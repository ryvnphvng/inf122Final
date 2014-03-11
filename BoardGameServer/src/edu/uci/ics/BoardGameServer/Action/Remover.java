package edu.uci.ics.BoardGameServer.Action;

import edu.uci.ics.BoardGameServer.Board.Board;



public abstract class Remover {
	Board board;
	
	public Remover(Board board)
	{
		this.board = board;
	}
	
	public abstract void deleteGameObject(GameObject g, Integer row,Integer col);
}
