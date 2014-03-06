package edu.uci.ics.BoardGameServer.Action;

import edu.uci.ics.BoardGameServer.Board.Board;
import edu.uci.ics.BoardGameServer.Common.Message;
import edu.uci.ics.BoardGameServer.Engine.Game;

public class Action {

	@SuppressWarnings("unused")
	private Board board;
	private Game game;

	public void setBoard(Board board) {
		this.board = board;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public void messageFromClient(Message message) {
	}

	@SuppressWarnings("unused")
	private void messageToClient(Message message) {
		message.message = "blah";
		game.messageToClient(message);
	}

}
