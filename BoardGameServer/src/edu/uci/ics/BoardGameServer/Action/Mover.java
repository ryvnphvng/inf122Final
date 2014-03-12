package edu.uci.ics.BoardGameServer.Action;

import edu.uci.ics.BoardGameServer.Board.Board;
import edu.uci.ics.BoardGameServer.Board.GameObject;

public abstract class Mover {
	private Board board;

	public Mover(Board board) {
		this.board = board;
	}

	public abstract void moveGameObject(GameObject g, Integer row, Integer col);

	public abstract void swapGameObjects(GameObject g1, GameObject g2);
}
