package edu.uci.ics.BoardGameServer.Action;

import edu.uci.ics.BoardGameServer.Board.Board;

public abstract class Creator {
	private Board board;
	
	public Creator(Board board)
	{
		this.board = board;
	}
	
	public abstract void createGameObject(GameObject g, Integer row, Integer col);
}