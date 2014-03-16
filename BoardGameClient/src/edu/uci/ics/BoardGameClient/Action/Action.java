package edu.uci.ics.BoardGameClient.Action;

import edu.uci.ics.BoardGameClient.Board.Board;
import edu.uci.ics.BoardGameClient.Common.Message;
import edu.uci.ics.BoardGameClient.Engine.Game;
import edu.uci.ics.BoardGameClient.GUI.GUI;
import edu.uci.ics.BoardGameClient.Action.BoardManipulator;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Action {

	private Game game;
	private int gameType;
	private Board board;
	private BoardManipulator manipulator;
	private GUI gui = new GUI();

	public Action() {
		gui.setAction(this);
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public void createGame(int gameType) {
		this.gameType = gameType;
		game.createGame(gameType);
	}

	public void disconnect() {
		game.disconnect();
	}

	public void shutdown() {
		game.shutdown();
	}

	public void messageFromServer(Message message) {
		JSONObject gameMessage;
		// implement message from server
		try {
			gameMessage = (JSONObject) new JSONParser().parse(message.message);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	private Message encodeMessage(JSONObject gameMessage) {
		int playerID = Integer.parseInt(gameMessage.get("PlayerID").toString());
		int gameID = Integer.parseInt(gameMessage.get("GameID").toString());
		
		Message messageToServer = new Message();
		messageToServer.gameId = gameID;
		messageToServer.playerNumber = playerID;
		messageToServer.message = gameMessage.toJSONString();
		
		return messageToServer;
		
	}

}
