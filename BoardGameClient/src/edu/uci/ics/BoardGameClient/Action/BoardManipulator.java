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
	public void createGameObject(GameObject g, Integer gameID, Integer gameType, Integer objectID, Integer objectType, Integer playerNum, Integer row, Integer col)
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

		game.messageToServer(action.encodeMessage(gameMessage)); // Send creation message to client
	}
	
	@SuppressWarnings("unchecked")
	public void deleteGameObject(Integer objectID, Integer gameID, Integer playerNum)
	{
		board.removeFromBoard(objectID);
		
		JSONObject gameMessage=new JSONObject();
		  gameMessage.put("MessageType", "Delete");
		  gameMessage.put("GameID", gameID);
		  gameMessage.put("PlayerID", playerNum);
		  gameMessage.put("ObjectID", objectID);

		  game.messageToServer(action.encodeMessage(gameMessage)); // Send removal message to client
	}
	
	@SuppressWarnings("unchecked")
	public void moveGameObject(Integer objectID, Integer playerNum, Integer row, Integer col, Integer gameID)
	{
		board.move(objectID, row, col);
		
		JSONObject gameMessage=new JSONObject();
		  gameMessage.put("MessageType", "Move");
		  gameMessage.put("GameID", gameID);
		  gameMessage.put("PlayerID", playerNum);
		  gameMessage.put("ObjectID", objectID);
		  gameMessage.put("Row", row);
		  gameMessage.put("Col", col);
	
		  game.messageToServer(action.encodeMessage(gameMessage)); // Send move message to client
	}
	
	@SuppressWarnings("unchecked")
	public void swapGameObjects(Integer objectID1, Integer objectID2, Integer playerNum, Integer gameID)
	{
		board.swap(objectID1, objectID2);
		
		JSONObject gameMessage=new JSONObject();
		  gameMessage.put("MessageType", "Swap");
		  gameMessage.put("GameID", gameID);
		  gameMessage.put("PlayerID", playerNum);
		  gameMessage.put("ObjectID1", objectID1);
		  gameMessage.put("ObjectID2", objectID2);
		
		  game.messageToServer(action.encodeMessage(gameMessage)); // Send swap message to client
	}
	
	
}
