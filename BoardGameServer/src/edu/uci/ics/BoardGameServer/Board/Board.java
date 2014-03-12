package edu.uci.ics.BoardGameServer.Board;

import edu.uci.ics.BoardGameServer.Action.Action;

public class Board {

	private Action action;
	private Tile[][] board;
	private int width;
	private int height;

	public void setAction(Action action) {
		this.action = action;
	}

	public int getWidth() {
		return this.width;
	}

	public void setWidth(int w) {
		this.width = w;
	}

	public int getHeight() {
		return this.height;
	}

	public void setHeight(int h) {
		this.height = h;
	}

	public Tile getTile(int row, int column) {
		if (row < this.height && column < this.width)
			return board[row][column];
		return null;
	}

	public void setTile(Tile t, int row, int column) {
		if (row < this.height && column < this.width)
			board[row][column] = t;
	}

}
