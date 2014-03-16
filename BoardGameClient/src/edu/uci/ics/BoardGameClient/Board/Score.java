package edu.uci.ics.BoardGameClient.Board;

public class Score {
	private int score = 0;
	
	public int getScore(){
		return this.score;
	}
	
	public void modifyScore(int amount){
		this.score += amount;
	}
}
