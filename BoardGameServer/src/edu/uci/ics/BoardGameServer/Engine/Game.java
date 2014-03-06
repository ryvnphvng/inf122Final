package edu.uci.ics.BoardGameServer.Engine;

import edu.uci.ics.BoardGameServer.Action.Action;
import edu.uci.ics.BoardGameServer.Board.Board;

public class Game {

	private Action action = new Action();
	private Board board = new Board();
	private String type;
	private int numberOfPlayers;

	Game(String type, int numberOfPlayers) {
		action.setBoard(board);
		board.setAction(action);
		this.type = type;
		this.numberOfPlayers = numberOfPlayers;
	}

}
