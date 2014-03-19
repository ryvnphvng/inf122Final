package edu.uci.ics.BoardGameServer.Action;

import edu.uci.ics.BoardGameServer.Board.GameObject;
import edu.uci.ics.BoardGameServer.Board.GameObjectDefinitions;
import edu.uci.ics.BoardGameServer.Common.Definitions;

public class GameObjectFactory {
	
	private static GameObjectIDGenerator idGen;
	
	public GameObjectFactory()
	{
		idGen = new GameObjectIDGenerator();
	}
	
	public GameObject createGameObject(int gameType, int playerNum, int row, int col)
	{
		if(gameType == Definitions.GAMETYPETICTACTOE)
		{
			if(playerNum == 0) // Creating a game object for the first player
			{
				 // First player is always X
				return new GameObject(row, col, idGen.getNextID(), GameObjectDefinitions.TICTACTOE_X, playerNum);
			}
			else if(playerNum == 1) // Creating a game object for the second player
			{
				// Second player is always O
				return new GameObject(row, col, idGen.getNextID(), GameObjectDefinitions.TICTACTOE_O, playerNum);
			}
			else
			{
				return null;
			}
		}
		else if(gameType == Definitions.GAMETYPECONNECTFOUR)
		{
			if(playerNum == 0) // Creating a game object for the first player
			{
				 // First player is always X
				return new GameObject(row, col, idGen.getNextID(), GameObjectDefinitions.CONNECT4_RED, playerNum);
			}
			else if(playerNum == 1) // Creating a game object for the second player
			{
				// Second player is always O
				return new GameObject(row, col, idGen.getNextID(), GameObjectDefinitions.CONNECT4_YELLOW, playerNum);
			}
			else
			{
				return null;
			}
		}
		return null;
	}	
}
