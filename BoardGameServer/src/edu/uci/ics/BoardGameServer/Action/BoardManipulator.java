package edu.uci.ics.BoardGameServer.Action;

import edu.uci.ics.BoardGameServer.Board.Board;
import edu.uci.ics.BoardGameServer.Board.GameObject;

public class BoardManipulator {

	private Board board;
	private Action action;
	
	
	
	public BoardManipulator(Board board, Action action)
	{
		this.board = board;
		this.action = action; //need action for message passing
	}
	
	public void createGameObject(GameObject g, int objectID, String type, int playerNum, int row, int col)
	{
		board.addToBoard(g, row, col);
		action.messageToClient(); // Send creation message to client
	}
	
	public void deleteGameObject(int objectID)
	{
		board.removeFromBoard(objectID);
		action.messageToClient(); // Send removal message to client
	}
	
	public void moveGameObject(int objectID, int playerNum, int row, int col)
	{
		board.move(objectID, row, col);
		action.messageToClient(); // Send move message to client
	}
	
	public void swapGameObjects(int objectID1, int objectID2)
	{
		board.swap(objectID1, objectID2);
		action.messageToClient(); // Send swap message to client
	}
}
