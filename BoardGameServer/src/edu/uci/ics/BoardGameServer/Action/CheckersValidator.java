package edu.uci.ics.BoardGameServer.Action;

import org.json.simple.JSONObject;

import edu.uci.ics.BoardGameServer.Board.Board;
import edu.uci.ics.BoardGameServer.Common.Message;

public class CheckersValidator extends MoveValidator {

	public CheckersValidator(Board board, int numberOfPlayers) {
		super(board, numberOfPlayers);
	}

	@Override
	public Boolean isValidMove(JSONObject gameMessage, Message message) {
		// TODO Auto-generated method stub
		return true;
	}

}
