package edu.uci.ics.BoardGameServer.Engine;

import edu.uci.ics.BoardGameServer.Action.Action;
import edu.uci.ics.BoardGameServer.Board.Board;
import edu.uci.ics.BoardGameServer.Common.Message;

public class Game {

	private Action action = new Action();
	private Board board = new Board();
	private Games games;
	private int gameId;
	@SuppressWarnings("unused")
	private String type;
	@SuppressWarnings("unused")
	private int numberOfPlayers;

	Game(int gameId, String type, int numberOfPlayers) {
		action.setBoard(board);
		action.setGame(this);
		board.setAction(action);
		board.setGame(this);
		this.gameId = gameId;
		this.type = type;
		this.numberOfPlayers = numberOfPlayers;
	}
	
	public void setGames(Games games) {
		this.games = games;
	}
	
	public void messageFromClient(Message message) {
		// send to action and/or board
	}
	
	public void messageToClient(Message message) {
		message.gameId = gameId;
		games.sendMessageToClient(message);
	}

}
