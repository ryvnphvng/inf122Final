package edu.uci.ics.BoardGameClient.Action;

import org.json.simple.JSONObject;

import edu.uci.ics.BoardGameClient.Board.Board;

public class TicTacToeValidator extends MoveValidator {

	Board board;
	
	public TicTacToeValidator(Board board) {
		this.board = board;
	}

	public Boolean isValidMove(JSONObject gameMessage) {
		
		return false;
	}

}

