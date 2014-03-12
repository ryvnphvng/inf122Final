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
	private int numberOfPlayers;

	public Action() {
		gui.setAction(this);
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public void setGameType(int gameType) {
		this.gameType = gameType;
	}

	public void setNumberOfPlayers(int numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
	}

	public void messageFromServer(Message message) {
	}

	private void messageToServer(Message message) {
		message.message = "blah";
		game.messageToServer(message);
	}

}
