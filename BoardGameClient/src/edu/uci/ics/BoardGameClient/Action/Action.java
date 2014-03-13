package edu.uci.ics.BoardGameClient.Action;

import edu.uci.ics.BoardGameClient.Board.Board;
import edu.uci.ics.BoardGameClient.Common.Message;
import edu.uci.ics.BoardGameClient.Engine.Game;
import edu.uci.ics.BoardGameClient.GUI.GUI;

public class Action {

	private GUI gui = new GUI();
	private Game game;
	private Board board;
	private int gameType;

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
		// implement message from server
	}

	private void messageToServer(Message message) {
		message.message = "blah";
		game.messageToServer(message);
	}

}
