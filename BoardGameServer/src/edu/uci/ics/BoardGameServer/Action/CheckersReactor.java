package edu.uci.ics.BoardGameServer.Action;

import org.json.simple.JSONObject;

public class CheckersReactor extends ActionReactor{

	public CheckersReactor(GameObjectFactory gof, BoardManipulator bm) {
		super(gof, bm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void updateBoard(JSONObject o) {
		
		int playerNum = ((Long) o.get("PlayerID")).intValue();
		int gameID = ((Long) o.get("GameID")).intValue();
		int fromRow = Integer.MIN_VALUE;
		int fromCol = Integer.MIN_VALUE;
		if(o.containsKey("InitialRow") && o.containsKey("InitialCol")) {
			fromRow = new Integer(((Long) o.get("InitialRow")).intValue());
			fromCol = new Integer(((Long) o.get("InitialCol")).intValue());
		}
		int toRow = ((Long) o.get("Row")).intValue();
		int toCol = ((Long) o.get("Col")).intValue();
		int numberOfPlayers = 2;
		
		if(Math.abs(fromRow - toRow) == 2 && Math.abs(fromCol - toCol) == 2) // A piece was jumped
		{
			// Find which direction the jump occurred and remove the piece that was jumped.
			if(toRow > fromRow) {
				if(toCol > fromCol) { // Jumped the piece in the bottom right direction.
					bm.deleteGameObject(bm.getBoard().getTile(toRow - 1, toCol - 1).getGameObjects().get(0).getObjID(), 
							gameID, playerNum, numberOfPlayers);
				}
				else { // Jumped the piece in the bottom left direction.
					bm.deleteGameObject(bm.getBoard().getTile(toRow - 1, toCol + 1).getGameObjects().get(0).getObjID(), 
							gameID, playerNum, numberOfPlayers);
				}
			}
			else { 
				if(toCol > fromCol) { // Jumped the piece in the upper right direction.
					bm.deleteGameObject(bm.getBoard().getTile(toRow + 1, toCol - 1).getGameObjects().get(0).getObjID(), 
							gameID, playerNum, numberOfPlayers);
				}
				else { // Jumped the piece in the upper left direction.
					bm.deleteGameObject(bm.getBoard().getTile(toRow + 1, toCol + 1).getGameObjects().get(0).getObjID(), 
							gameID, playerNum, numberOfPlayers);
				}
			}
			
		}
	}

}
