package edu.uci.ics.BoardGameServer.Action;

import edu.uci.ics.BoardGameServer.Board.Board;

public abstract class Reactor {
	Board board;
	Creator creator;
	Remover remover;
	Mover mover;
	GameOver gameOver;
	
	public Reactor(Board board, Creator creator, Remover remover, Mover mover, GameOver gameOver)
	{
		this.board = board;
		this.creator = creator;
		this.remover = remover;
		this.mover = mover;
		this.gameOver = gameOver;
	}
	
	public abstract void updateBoard();
}
