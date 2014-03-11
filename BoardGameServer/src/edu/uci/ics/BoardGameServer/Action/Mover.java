package edu.uci.ics.BoardGameServer.Action;

import edu.uci.ics.BoardGameServer.Board.Board;

public abstract class Mover {
	private Board board;
	
	public Mover(Board board)
	{
		this.board = board;
	}
	
	public abstract void moveGameObject(GameObject g, Integer row, Integer col);
	public abstract void swapGameObjects(GameObject1 g1, GameObject2 g2);
}
