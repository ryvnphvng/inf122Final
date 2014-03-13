package edu.uci.ics.BoardGameServer.Action;

import edu.uci.ics.BoardGameServer.Board.Board;

public abstract class GameOver {
	private Board board;

	public GameOver(Board board) {
		this.board = board;
	}

	public abstract boolean isWinCondidtionMet(String s);
	
	public abstract boolean isLoseCondidtionMet(String s);
	
	public abstract boolean isTieCondidtionMet(String s);
}
