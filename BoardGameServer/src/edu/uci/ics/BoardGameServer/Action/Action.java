package edu.uci.ics.BoardGameServer.Action;

import edu.uci.ics.BoardGameServer.Board.Board;
import edu.uci.ics.BoardGameServer.Common.Message;
import edu.uci.ics.BoardGameServer.Engine.Game;

public class Action {

	private Game game;
	private Board board;
	private int gameType;
	private int numberOfPlayers;

	public void setGame(Game game) {
		this.game = game;
	}

	public void setGameType(int gameType) {
		this.gameType = gameType;
	}

	public void setNumberOfPlayers(int numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
	}

	public void messageFromClient(Message message) {
	}

	private void messageToClient(Message message) {
		message.message = "blah";
		message.playerNumber = 0;
		game.messageToClient(message);
	}

}
