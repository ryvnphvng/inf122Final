package edu.uci.ics.BoardGameServer.Distribution;

import edu.uci.ics.BoardGameServer.Common.Message;
import edu.uci.ics.BoardGameServer.Engine.TalkDistribution;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Distribution implements Runnable {

	private TalkDistribution talkDistribution;
	private Login login = new Login();
	private volatile boolean stopRunning = false;
	private Server server;
	private Thread threadServer;
	private List<ClientWait> clientWaits = new ArrayList<ClientWait>();
	private List<ClientGame> clientGames = new ArrayList<ClientGame>();
	private Map<Integer, ClientGame> connectionIdToClientGame = new TreeMap<Integer, ClientGame>();
	private Map<Integer, ClientGame> gameIdToClientGame = new TreeMap<Integer, ClientGame>();

	public void setTalkDistribution(TalkDistribution talkDistribution) {
		this.talkDistribution = talkDistribution;
	}

	public void stop() {
		stopRunning = true;
	}

	@Override
	public void run() {

		server = new Server();
		server.setDistribution(this);

		threadServer = new Thread(server);
		threadServer.start();

		while (!stopRunning) {
			talkDistribution.waitForOutputQueue();
			messageToClient(talkDistribution.getOutputQueue());
		}

		server.stop();
		threadServer.interrupt();
	}

	public void createGame(int connectionId, String message) {
		message = message.replace("createGame ", "");
		int gameType = Integer.parseInt(message);

		// TODO: fix number of player depending on game type and etc
		ClientWait clientFound = null;
		Iterator<ClientWait> iteratorClientWaits = clientWaits.iterator();
		while (iteratorClientWaits.hasNext()) {
			ClientWait clientWaiting = iteratorClientWaits.next();
			if (clientWaiting.gameType != gameType) {
				continue;
			}
			if (!server.isConnectionAlive(clientWaiting.connectionId)) {
				iteratorClientWaits.remove();
				continue;
			}
			clientFound = clientWaiting;
		}
		
		if (clientFound == null) {
			clientFound = new ClientWait();
			clientFound.connectionId = connectionId;
			clientFound.gameType = gameType;
			clientWaits.add(clientFound);
			return;
		}

		clientWaits.remove(clientFound);

		ClientGame clientGame = new ClientGame();
		clientGame.connectionIds.add(connectionId);
		clientGame.connectionIds.add(clientFound.connectionId);
		clientGames.add(clientGame);

		clientGame.gameId = talkDistribution.createGame(gameType, 2);

		gameIdToClientGame.put(clientGame.gameId, clientGame);
		connectionIdToClientGame.put(connectionId, clientGame);
		connectionIdToClientGame.put(clientFound.connectionId, clientGame);

		talkDistribution.gameCreated(clientGame.gameId);
	}

	public void destroyGame(int connectionId) {
		ClientGame clientGame = connectionIdToClientGame.get(connectionId);
		if (clientGame == null) {
			return;
		}

		talkDistribution.destroyGame(clientGame.gameId);

		// TODO: delay remove from clientGames and gameIdToClientGame
	}

	public void removeConnectionId(int connectionId) {
		connectionIdToClientGame.remove(connectionId);
	}

	public void messageFromClient(int connectionId, String data) {
		ClientGame clientGame = connectionIdToClientGame.get(connectionId);
		
		if (clientGame == null) {
			try {
				JSONObject loginMessage = (JSONObject) new JSONParser().parse(data);
				if(loginMessage.get("MessageType").equals("Login"))
				{
					if(login.validate((String) loginMessage.get("Username"), (String) loginMessage.get("Password")))
					{
						Message messageToClient = new Message();
						String email = login.getEmail((String) loginMessage.get("Username"), (String) loginMessage.get("Password"));
						loginMessage.put("Email", email);
						loginMessage.put("LoginSuccessful", true);
						messageToClient.message = loginMessage.toJSONString();
						server.messageToClient(connectionId, messageToClient.message);
						return;
					}
					else //not a valid login
					{
						Message messageToClient = new Message();
						loginMessage.put("LoginSuccessful", false);
						messageToClient.message = loginMessage.toJSONString();
						server.messageToClient(connectionId, messageToClient.message);
						return;
					}
				}
				else
				{
					System.err.println("Message sent without matching game " + data);
					return;
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		Message message = new Message();
		message.gameId = clientGame.gameId;
		for (int i = 0; i < clientGame.connectionIds.size(); i++) {
			if (clientGame.connectionIds.get(i) == connectionId) {
				message.playerNumber = i;
			}
		}
		message.message = data;

		talkDistribution.messageFromClient(message);
	}

	private void messageToClient(Message message) {
		if (message == null) {
			return;
		}

		ClientGame clientGame = gameIdToClientGame.get(message.gameId);
		if (clientGame == null) {
			return;
		}

		int connectionId = clientGame.connectionIds.get(message.playerNumber);
		server.messageToClient(connectionId, message.message);
	}
}
