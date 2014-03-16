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

	public void shutdown() {
		game.shutdown();
	}

	public void messageFromServer(Message message) {
		// implement message from server
	}

	private void messageToServer(Message message) {
		message.message = "blah";
		game.messageToServer(message);
	}

}
