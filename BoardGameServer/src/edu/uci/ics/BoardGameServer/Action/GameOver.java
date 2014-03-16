package edu.uci.ics.BoardGameServer.Action;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import edu.uci.ics.BoardGameServer.Board.Board;

public abstract class GameOver {
	private Board board;

	public abstract ArrayList<Integer> isWinConditionMet(JSONObject o);
	
	public abstract ArrayList<Integer> isLoseConditionMet(JSONObject o);
	
	public abstract ArrayList<Integer> isTieConditionMet(JSONObject o);
}
