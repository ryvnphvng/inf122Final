package edu.uci.ics.BoardGameClient.Action;

import org.json.simple.JSONObject;

import edu.uci.ics.BoardGameClient.Board.Board;
import edu.uci.ics.BoardGameClient.Common.Definitions;

public class MoveSender {
	
	private MoveSender() {}
	
	@SuppressWarnings("unchecked")
	public static void sendMessage(Action action, Integer gameType, Integer row, Integer col)
	{
		if(gameType == Definitions.GAMETYPETICTACTOE)
		{
			JSONObject gameMessage=new JSONObject();
			  gameMessage.put("MessageType", "Create");
			  gameMessage.put("GameType", gameType);
			  gameMessage.put("Row", row);
			  gameMessage.put("Col", col);
			  
			action.getGame().messageToServer(action.encodeMessage(gameMessage));
		}
		if(gameType == Definitions.GAMETYPECONNECTFOUR)
		{
			JSONObject gameMessage=new JSONObject();
			  gameMessage.put("MessageType", "Create");
			  gameMessage.put("GameType", gameType);
			  gameMessage.put("Row", row); //is this needed?
			  gameMessage.put("Col", col);
			  
			action.getGame().messageToServer(action.encodeMessage(gameMessage));
		}
		if(gameType == Definitions.GAMETYPECHECKERS)
		{
			Board b = action.getBoard();
			int height = b.getHeight();
			int width = b.getWidth();
			
			for(int i=0; i<height; i++)
			{
				for(int j=0; j<width; j++)
				{
					if(b.getTile(i, j).isSelected())
					{
						if(b.getTile(i, j).getGameObjects().size() > 0) // There is a piece that can be moved.
						{
							JSONObject gameMessage=new JSONObject();
							  gameMessage.put("MessageType", "Move");
							  gameMessage.put("GameType", gameType);
							  gameMessage.put("ObjectID", b.getTile(i, j).getGameObjects().get(0).getObjID());
							  gameMessage.put("PlayerID", b.getTile(i, j).getGameObjects().get(0).getOwner());
							  gameMessage.put("GameID", 0);
							  gameMessage.put("Row", row);
							  gameMessage.put("Col", col);
							  
							action.getGame().messageToServer(action.encodeMessage(gameMessage));
						}
						
						b.getTile(i, j).deselectPanel();
						
						return;
					}
				}
			}
			
			// Nothing was already selected, so the user wants to select a game piece. 
			b.getTile(row, col).selectPanel();
		}
	}
}
