package edu.uci.ics.BoardGameServer.Board;

public class GameObject {

	private int x;
	private int y;
	private String type;
	private Player player;

	public int getX() {
		return this.x;
	}

	public void setX(int newX) {
		this.x = newX;
	}

	public int getY() {
		return this.y;
	}

	public void setY(int newY) {
		this.y = newY;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String t) {
		this.type = t;
	}

	public Player getOwner() {
		return this.player;
	}

	public void setOwner(Player p) {
		this.player = p;
	}

}
