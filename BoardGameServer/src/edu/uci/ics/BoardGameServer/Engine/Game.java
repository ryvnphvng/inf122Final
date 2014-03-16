package edu.uci.ics.BoardGameServer.Engine;

import edu.uci.ics.BoardGameServer.Action.Action;
import edu.uci.ics.BoardGameServer.Common.Message;

public class Game {

	private Action action;
	private Games games;
	private int gameId;

	Game(int gameId, int gameType, int numberOfPlayers) {
		action = new Action(this, gameType, numberOfPlayers);
		this.gameId = gameId;
	}

	public void setGames(Games games) {
		this.games = games;
	}

	public void messageFromClient(Message message) {
		action.messageFromClient(message);
	}

	public void messageToClient(Message message) {
		message.gameId = gameId;
		games.messageToClient(message);
	}

}
