package edu.uci.ics.BoardGameServer.Board;


public class Player {
	private Integer ID;
	private Score score;
	
	public Player(int ID)
	{
		this.ID = ID;
		score = new Score();
	}
	
	public Integer getID(){
		return this.ID;
	}
	
	public void modifyScore(int amount){
		score.modifyScore(amount);
	}
	
	public int getScore(){
		return score.getScore();
	}
	
	
}
