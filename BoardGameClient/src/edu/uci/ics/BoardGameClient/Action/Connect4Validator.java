package edu.uci.ics.BoardGameClient.Action;

import org.json.simple.JSONObject;

import edu.uci.ics.BoardGameClient.Board.Board;

public class Connect4Validator extends MoveValidator {

	Board board;
	
	public Connect4Validator(Board board) {
		this.board = board;
	}

	public Boolean isValidMove(JSONObject gameMessage) {
		
		return false;
	}

}

