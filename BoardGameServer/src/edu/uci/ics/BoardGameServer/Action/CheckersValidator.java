package edu.uci.ics.BoardGameServer.Action;

import org.json.simple.JSONObject;

import edu.uci.ics.BoardGameServer.Board.GameObjectDefinitions;
import edu.uci.ics.BoardGameServer.Board.Board;
import edu.uci.ics.BoardGameServer.Board.GameObject;
import edu.uci.ics.BoardGameServer.Common.Message;

public class CheckersValidator extends MoveValidator {

	public CheckersValidator(Board board, int numberOfPlayers) {
		super(board, numberOfPlayers);
	}

	@Override
	public Boolean isValidMove(JSONObject gameMessage, Message message) {
		if(gameMessage.get("MessageType").equals("Move") && 
		   message.playerNumber == this.getCurrentPlayerTurn())
		{
			int playerID = message.playerNumber;
			int objectID = Integer.parseInt(gameMessage.get("ObjectID").toString());
			
			int toRow = Integer.parseInt(gameMessage.get("Row").toString());
			int toCol = Integer.parseInt(gameMessage.get("Col").toString());
			
			int fromRow = getBoard().getGameObject(objectID).getRow();
			int fromCol = getBoard().getGameObject(objectID).getCol();
			
			GameObject pieceMoved = getBoard().getGameObject(objectID);
			
			// Check if the space where piece is being moved is already taken. And check if the piece being moved 
			// belongs to the player making the move.
			if(getBoard().getTile(toRow, toCol).getGameObjects().size() > 0 ||
				pieceMoved.getOwner() != playerID) {
				return false; // The space is already occupied by a checker piece. Invalid move.
			}
			
			if(pieceMoved.getObjectType() == GameObjectDefinitions.CHECKERS_BLACK) {
				if(toRow > fromRow) { // Piece is moving in the correct direction.
					if((toRow == fromRow + 1 && toCol == fromCol - 1) ||
					   (toRow == fromRow + 1 && toCol == fromCol + 1)) { // Piece moved 1 space to left or right.
						return true;
					}
					else if(toRow == fromRow + 2 && toCol == fromCol - 2) { // Check if the piece jumped another.
						if(getBoard().getTile(fromRow + 1, fromCol - 1).getGameObjects().size() > 0 &&
						  (getBoard().getTile(fromRow + 1, fromCol - 1).getGameObjects().get(0).getOwner() != 
						   pieceMoved.getOwner())) {
							return true;
						}
					}
					else if(toRow == fromRow + 2 && toCol == fromCol + 2) { // Check if the piece jumped another.
						if(getBoard().getTile(fromRow + 1, fromCol + 1).getGameObjects().size() > 0 &&
						  (getBoard().getTile(fromRow + 1, fromCol + 1).getGameObjects().get(0).getOwner() != 
						   pieceMoved.getOwner())) {
							return true;
						}
					}
				}
					
			}
			else if(pieceMoved.getObjectType() == GameObjectDefinitions.CHECKERS_RED) {
				if(toRow < fromRow) { // Piece is moving in the correct direction.
					if((toRow == fromRow - 1 && toCol == fromCol - 1) ||
					   (toRow == fromRow - 1 && toCol == fromCol + 1)) { // Piece moved 1 space to left or right.
						return true;
					}
					else if(toRow == fromRow - 2 && toCol == fromCol - 2) { // Check if the piece jumped another.
						if(getBoard().getTile(fromRow - 1, fromCol - 1).getGameObjects().size() > 0 &&
						  (getBoard().getTile(fromRow - 1, fromCol - 1).getGameObjects().get(0).getOwner() != 
						   pieceMoved.getOwner())) {
							return true;
						}
					}
					else if(toRow == fromRow - 2 && toCol == fromCol + 2) { // Check if the piece jumped another.
						if(getBoard().getTile(fromRow - 1, fromCol + 1).getGameObjects().size() > 0 &&
						  (getBoard().getTile(fromRow - 1, fromCol + 1).getGameObjects().get(0).getOwner() != 
						   pieceMoved.getOwner())) {
							return true;
						}
					}
				}
			}
		}
		
		return false;
	}

}
