package edu.uci.ics.BoardGameClient.Action;


import edu.uci.ics.BoardGameClient.Board.GameObject;

public class GameObjectFactory {
	
	public GameObject createGameObject(int playerNum, int objectType, int objectID, int row, int col)
	{
		return new GameObject(row, col, objectID, objectType, playerNum);
	}	

}
