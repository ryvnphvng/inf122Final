package edu.uci.ics.BoardGameServer.Action;

import edu.uci.ics.BoardGameServer.Board.Board;

public abstract class GameOver {
	private Board board;

	public GameOver(Board board) {
		this.board = board;
	}

	public abstract Boolean isWinCondidtionMet(String s);

	public abstract Boolean isLoseCondidtionMet(String s);
}
