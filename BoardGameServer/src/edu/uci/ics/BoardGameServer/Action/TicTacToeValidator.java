package edu.uci.ics.BoardGameServer.Action;

import org.json.simple.JSONObject;

import edu.uci.ics.BoardGameServer.Board.Board;
import edu.uci.ics.BoardGameServer.Common.Message;

public class TicTacToeValidator extends MoveValidator {

	Board board;
	
	public TicTacToeValidator(Board board, int numberOfPlayers) {
		super(board, numberOfPlayers);
	}

	public Boolean isValidMove(JSONObject gameMessage, Message message) {
		if(gameMessage.get("MessageType").equals("Create") && 
		   message.playerNumber == this.getCurrentPlayerTurn())
		{
			int row = Integer.parseInt(gameMessage.get("Row").toString());
			int col = Integer.parseInt(gameMessage.get("Col").toString());
			
			if(getBoard().getTile(row, col).getGameObjects().size() > 0)
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
