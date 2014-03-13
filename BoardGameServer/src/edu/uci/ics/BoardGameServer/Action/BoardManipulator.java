package edu.uci.ics.BoardGameServer.Action;

import edu.uci.ics.BoardGameServer.Board.Board;
import edu.uci.ics.BoardGameServer.Board.GameObject;

public class BoardManipulator {

	private Board board;
	
	public void createGameObject(GameObject g, int objectID, String type, int playerNum, int row, int col)
	{
		// board.addToBoard(g, objectID, type, row, col);
	}
	
	public void deleteGameObject(int objectID)
	{
		// board.removeFromBoard(objectID);
	}
	
	public void moveGameObject(int objectID, int playerNum, int row, int col)
	{
		// board.move(objectID, row, col);
	}
	
	public void swapGameObjects(int objectID1, int objectID2)
	{
		//board.swap(objectID1, objectID2);
	}
}
