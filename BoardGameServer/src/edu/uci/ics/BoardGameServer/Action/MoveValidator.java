package edu.uci.ics.BoardGameServer.Action;

import org.json.simple.JSONObject;

import edu.uci.ics.BoardGameServer.Board.Board;

public abstract class MoveValidator {
	private Board board;

	public abstract Boolean isValidMove(JSONObject gameMessage);
}
