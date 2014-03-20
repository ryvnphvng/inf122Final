package edu.uci.ics.BoardGameServer.Action;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import edu.uci.ics.BoardGameServer.Board.Board;

public class CheckersGameOver extends GameOver {

	private Board board;
	
	public CheckersGameOver(Board b)
	{
		this.board = b;
	}
	
	@Override
	public ArrayList<Integer> isWinConditionMet(JSONObject o) {
		// TODO Auto-generated method stub
		return new ArrayList<Integer>();
	}

	@Override
	public ArrayList<Integer> isLoseConditionMet(JSONObject o) {
		// TODO Auto-generated method stub
		return new ArrayList<Integer>();
	}

	@Override
	public ArrayList<Integer> isTieConditionMet(JSONObject o) {
		// TODO Auto-generated method stub
		return new ArrayList<Integer>();
	}

}
