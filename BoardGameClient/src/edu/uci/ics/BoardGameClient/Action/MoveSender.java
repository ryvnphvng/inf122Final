package edu.uci.ics.BoardGameClient.Action;

import org.json.simple.JSONObject;

import edu.uci.ics.BoardGameClient.Common.Definitions;
import edu.uci.ics.BoardGameClient.Engine.Game;

public class MoveSender {

	Game game;
	Action action;
	int gameType;
	
	public static void sendMessage(Action action, int gameType, int row, int col)
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
	}
}
