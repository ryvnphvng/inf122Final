package edu.uci.ics.BoardGameServer.Board;

import edu.uci.ics.BoardGameServer.Action.Action;
import edu.uci.ics.BoardGameServer.Common.Message;
import edu.uci.ics.BoardGameServer.Engine.Game;

public class Board {

	@SuppressWarnings("unused")
	private Action action;
	private Game game;

	public void setAction(Action action) {
		this.action = action;
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
