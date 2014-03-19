package edu.uci.ics.BoardGameServer.Action;

import org.json.simple.JSONObject;

import edu.uci.ics.BoardGameServer.Board.Board;
import edu.uci.ics.BoardGameServer.Common.Message;

public class Connect4Validator extends MoveValidator {

	Board board;
	
	public Connect4Validator(Board board, int numberOfPlayers) {
		super(board, numberOfPlayers);
	}

	public Boolean isValidMove(JSONObject gameMessage, Message message) {
		if(gameMessage.get("MessageType").equals("Create") && 
		   message.playerNumber == this.getCurrentPlayerTurn())
		{
			int col = Integer.parseInt(gameMessage.get("Col").toString());
			
			if(getBoard().getTile(0, col).getGameObjects().size() > 0)
			{
				return false; // Column is filled - move is invalid
			}
			else
			{
				return true; // Still room for more pieces. Move is valid.
			}
		}
		
		return false;
	}

}
