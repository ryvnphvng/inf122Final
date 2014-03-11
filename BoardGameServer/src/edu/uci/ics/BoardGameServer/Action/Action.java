package edu.uci.ics.BoardGameServer.Action;

import edu.uci.ics.BoardGameServer.Common.Message;
import edu.uci.ics.BoardGameServer.Engine.Game;

public abstract class Action {

	private Game game;

	public void setBoard(Game game) {
		this.game = game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public void messageFromClient(Message message) {
	}

	@SuppressWarnings("unused")
	private void messageToClient(Message message) {
		message.message = "blah";
		message.playerNumber = 0;
		game.messageToClient(message);
	}

}
