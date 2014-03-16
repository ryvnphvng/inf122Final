package edu.uci.ics.BoardGameServer.Distribution;

import edu.uci.ics.BoardGameServer.Common.Message;
import edu.uci.ics.BoardGameServer.Engine.TalkDistribution;

import java.util.ArrayList;
import java.util.List;

public class Distribution implements Runnable {

	private TalkDistribution talkDistribution;
	private volatile boolean stopRunning = false;
	private Server server;
	private Thread threadServer;
	private List<ClientWait> clientWaits = new ArrayList<ClientWait>();
	private List<ClientGame> clientGames = new ArrayList<ClientGame>();

	public void setTalkDistribution(TalkDistribution talkDistribution) {
		this.talkDistribution = talkDistribution;
	}

	public void stop() {
		stopRunning = true;
	}

	@Override
	public void run() {

		// Temporary
		server = new Server();
		server.setDistribution(this);

		threadServer = new Thread(server);
		threadServer.start();

		while (!stopRunning) {
			talkDistribution.waitForOutputQueue();
			messageToClient(talkDistribution.getOutputQueue());
		}

		// Temporary
		server.stop();
		threadServer.interrupt();

	}

	public void createGame(int connectionId, String message) {
		message = message.replace("createGame ", "");
		int gameType = Integer.parseInt(message);
		
		// TODO: fix number of player depending on game type and etc
		ClientWait clientFound = null;
		for (ClientWait clientWaiting : clientWaits) {
			if (clientWaiting.gameType == gameType) {
				clientFound = clientWaiting;
			}
		}
		if (clientFound == null) {
			clientFound = new ClientWait();
			clientFound.connectionId = connectionId;
			clientFound.gameType = gameType;
			clientWaits.add(clientFound);
			return;
		}

		ClientGame clientGame = new ClientGame();
		clientGame.gameId = talkDistribution.createGame(gameType, 2);
		clientGame.connectionIds.add(connectionId);
		clientGame.connectionIds.add(clientFound.connectionId);
		
		clientGames.add(clientGame);
	}

	// TODO: need to call destroyGame
	public void destroyGame() {
		// TODO: fix to destroy game correctly
		talkDistribution.destroyGame(2);
	}

	public void messageFromClient(String data) {
		Message message = new Message();
		message.gameId = 2;
		message.playerNumber = 0;
		message.message = data;
		talkDistribution.messageFromClient(message);
	}

	private void messageToClient(Message message) {
		if (message == null) {
			return;
		}
		// TODO: implement sending the message to the correct client
		System.out.println(message.message);
	}

}
