package edu.uci.ics.BoardGameServer.Action;

import org.json.simple.JSONObject;

import edu.uci.ics.BoardGameServer.Board.Board;

public class TicTacToeValidator extends MoveValidator {

	Board board;
	
	public TicTacToeValidator(Board board) {
		this.board = board;
	}

	public Boolean isValidMove(JSONObject gameMessage) {
		if(gameMessage.get("MessageType").equals("Create"))
		{
			int row = Integer.parseInt(gameMessage.get("Row").toString());
			int col = Integer.parseInt(gameMessage.get("Col").toString());
			
			if(board.getTile(row, col).getGameObjects().size() > 0)
			{
				return false; // A player has already put an 'X' or 'O' in this tile. Move is invalid.
			}
			else
			{
				return true; // A play has not put an 'X' or 'O' in this tile. Move is valid.
			}
		}
		
		return false;
	}

}
