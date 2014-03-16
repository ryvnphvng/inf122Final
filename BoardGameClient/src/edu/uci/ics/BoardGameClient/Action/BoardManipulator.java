package edu.uci.ics.BoardGameClient.Action;


import org.json.simple.JSONObject;

import edu.uci.ics.BoardGameClient.Board.Board;
import edu.uci.ics.BoardGameClient.Board.GameObject;
import edu.uci.ics.BoardGameClient.Engine.Game;
import edu.uci.ics.BoardGameClient.Action.Action;

public class BoardManipulator {
	
	private Action action;
	private Board board;
	private Game game;
	
	public BoardManipulator(Board board, Game game, Action action)
	{
		this.board = board;
		this.action = action; // need action for message passing
		this.game = game; // need game for message passing
	}
	
	@SuppressWarnings("unchecked")
	public void createGameObject(GameObject g,int gameID, int gameType, int objectID, int objectType, int playerNum, int row, int col)
	{	
		board.addToBoard(g, row, col);
		
		JSONObject gameMessage=new JSONObject();
		  gameMessage.put("MessageType", "Create");
		  gameMessage.put("GameID", gameID);
		  gameMessage.put("GameType", gameType);
		  gameMessage.put("PlayerID", playerNum);
		  gameMessage.put("ObjectID", objectID);
		  gameMessage.put("Row", row);
		  gameMessage.put("Col", col);
		  gameMessage.put("ObjectType", objectType);

		game.messageToServer(action.decodeMessage(gameMessage)); // Send creation message to client
	}
	
	@SuppressWarnings("unchecked")
	public void deleteGameObject(int objectID, int gameID, int playerNum)
	{
		board.removeFromBoard(objectID);
		
		JSONObject gameMessage=new JSONObject();
		  gameMessage.put("MessageType", "Delete");
		  gameMessage.put("GameID", gameID);
		  gameMessage.put("PlayerID", playerNum);
		  gameMessage.put("ObjectID", objectID);

		  game.messageToServer(action.decodeMessage(gameMessage)); // Send removal message to client
	}
	
	@SuppressWarnings("unchecked")
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
	
		  game.messageToServer(action.decodeMessage(gameMessage)); // Send move message to client
	}
	
	@SuppressWarnings("unchecked")
	public void swapGameObjects(int objectID1, int objectID2, int playerNum, int gameID)
	{
		board.swap(objectID1, objectID2);
		
		JSONObject gameMessage=new JSONObject();
		  gameMessage.put("MessageType", "Swap");
		  gameMessage.put("GameID", gameID);
		  gameMessage.put("PlayerID", playerNum);
		  gameMessage.put("ObjectID1", objectID1);
		  gameMessage.put("ObjectID2", objectID2);
		
		  game.messageToServer(action.decodeMessage(gameMessage)); // Send swap message to client
	}
	
	
}
