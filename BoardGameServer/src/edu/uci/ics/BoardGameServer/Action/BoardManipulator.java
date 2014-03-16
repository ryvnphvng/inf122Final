package edu.uci.ics.BoardGameServer.Action;

import org.json.simple.JSONObject;

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
	
	public void createGameObject(GameObject g,int gameID, int objectID, int type, int playerNum, int row, int col)
	{	
		board.addToBoard(g, row, col);
		JSONObject gameMessage=new JSONObject();
		  gameMessage.put("MessageType", "Create");
		  gameMessage.put("GameID", gameID);
		  gameMessage.put("PlayerID", playerNum);
		  gameMessage.put("ObjectID", objectID);
		  gameMessage.put("Row", row);
		  gameMessage.put("Col", col);
		  gameMessage.put("Message", type);

		action.messageToClient(gameMessage); // Send creation message to client
	}
	
	public void deleteGameObject(int objectID, int gameID, int playerNum)
	{
		board.removeFromBoard(objectID);
		JSONObject gameMessage=new JSONObject();
		  gameMessage.put("MessageType", "Delete");
		  gameMessage.put("GameID", gameID);
		  gameMessage.put("PlayerID", playerNum);
		  gameMessage.put("ObjectID", objectID);

		action.messageToClient(gameMessage); // Send removal message to client
	}
	
	public void moveGameObject(int objectID, int playerNum, int row, int col, int gameID)
	{
		board.move(objectID, row, col);
		JSONObject gameMessage=new JSONObject();
		  gameMessage.put("MessageType", "Move");
		  gameMessage.put("GameID", gameID);
		  gameMessage.put("PlayerID", playerNum);
		  gameMessage.put("ObjectID", objectID);
		  gameMessage.put("Row", row);
		  gameMessage.put("Col", col);
	
		action.messageToClient(gameMessage); // Send move message to client
	}
	
	public void swapGameObjects(int objectID1, int objectID2, int playerNum, int gameID)
	{
		board.swap(objectID1, objectID2);
		JSONObject gameMessage=new JSONObject();
		  gameMessage.put("MessageType", "Swap");
		  gameMessage.put("GameID", gameID);
		  gameMessage.put("PlayerID", playerNum);
		  gameMessage.put("ObjectID1", objectID1);
		  gameMessage.put("ObjectID2", objectID2);
		
		action.messageToClient(gameMessage); // Send swap message to client
	}
	
	
	
	
	
	
	
	
	
}
