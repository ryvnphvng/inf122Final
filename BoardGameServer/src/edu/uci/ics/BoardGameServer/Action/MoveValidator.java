package edu.uci.ics.BoardGameServer.Action;

import edu.uci.ics.BoardGameServer.Board.Board;

public abstract class MoveValidator {
	private Board board;

	public MoveValidator(Board b) {
		this.board = b;
	}

	public abstract Boolean isValidMove(String instruction);
}
