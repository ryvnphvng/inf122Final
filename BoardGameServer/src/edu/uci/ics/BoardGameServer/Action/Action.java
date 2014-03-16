package edu.uci.ics.BoardGameServer.Action;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import edu.uci.ics.BoardGameServer.Board.Board;
import edu.uci.ics.BoardGameServer.Board.GameObject;
import edu.uci.ics.BoardGameServer.Common.Definitions;
import edu.uci.ics.BoardGameServer.Common.Message;
import edu.uci.ics.BoardGameServer.Engine.Game;



public class Action {

	private Game game;
	private int gameType;
	private int numberOfPlayers;
	private Board board;
	private BoardManipulator manipulator;
	private ActionReactor reactor; // abstract
	private GameObjectFactory gof;
	private MoveValidator validator; // abstract

	public Action(Game game, int gameType, int numberOfPlayers)
	{
		this.game = game;
		this.gameType = gameType;
		gof = new GameObjectFactory();
		board = createBoard(gameType, numberOfPlayers);
		manipulator = new BoardManipulator(board, this);
		
		reactor = setActionReactor();
		validator = setMoveValidator();

		populateBoard();
	}
	

	public void setGame(Game game) {
		this.game = game;
	}

	public void setGameType(int gameType) {
		this.gameType = gameType;
	}

	public void setNumberOfPlayers(int numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
	}

	public void messageFromClient(Message message) {
//		Object obj=JSONValue.parse(message.message);
//		JSONArray array=(JSONArray)obj;		
//		JSONObject gameMessage=(JSONObject)array.get(0);
		
		JSONObject gameMessage;
		try {
			gameMessage = (JSONObject) new JSONParser().parse(message.message);
			
			if  (validator.isValidMove(gameMessage)) {
				
				if(gameMessage.get("MessageType").equals("Create")){
					
					Integer gameType = (Integer) gameMessage.get("GameType");
					Integer playerID = (Integer) gameMessage.get("PlayerID");
					Integer row = (Integer) gameMessage.get("Row");
					Integer col = (Integer) gameMessage.get("Col");
					Integer gameID = (Integer) gameMessage.get("GameID");
					Integer objectID = (Integer) gameMessage.get("ObjectID");
					Integer objectType = (Integer) gameMessage.get("ObjectType");
					
					manipulator.createGameObject(gof.createGameObject(gameType, playerID, row, col), 
					gameID, gameType, objectID, objectType, playerID, row, col);
				}
				
				else if(gameMessage.get("MessageType").equals("Delete")){
					
					Integer playerID = (Integer) gameMessage.get("PlayerID");
					Integer gameID = (Integer) gameMessage.get("GameID");
					Integer objectID = (Integer) gameMessage.get("ObjectID");
					
					manipulator.deleteGameObject(objectID, gameID, playerID);
				}
				else if(gameMessage.get("MessageType").equals("Move")){
					
					Integer playerID = (Integer) gameMessage.get("PlayerID");
					Integer objectID = (Integer) gameMessage.get("ObjectID");
					Integer gameID = (Integer) gameMessage.get("GameID");
					Integer row = (Integer) gameMessage.get("Row");
					Integer col = (Integer) gameMessage.get("Col");
					
					manipulator.moveGameObject(objectID, playerID, row, col, gameID);
					
				}
				else if(gameMessage.get("MessageType").equals("Swap")){
					
					Integer objectID1 = (Integer) gameMessage.get("ObjectID1");
					Integer objectID2 = (Integer) gameMessage.get("ObjectID2");
					Integer gameID = (Integer) gameMessage.get("GameID");
					Integer playerID = (Integer) gameMessage.get("PlayerID");
					
					manipulator.swapGameObjects(objectID1, objectID2, playerID, gameID);
					
				}
					
			}
			else{
				
				JSONObject notValidMove = new JSONObject();
				notValidMove.put("MessageType", "InvalidMove");
				notValidMove.put("GameID", message.gameId);
				notValidMove.put("PlayerID", message.playerNumber);
				messageToClient(notValidMove);
				
			}	
			
				
		} catch (ParseException e) {
			e.printStackTrace();
		}	
		
		
	}
	
	public Message messageToClient(JSONObject gameMessage) {

		
		int playerID = Integer.parseInt(gameMessage.get("PlayerID").toString());
		int gameID = Integer.parseInt(gameMessage.get("GameID").toString());
		
		Message messageToClient = new Message();
		messageToClient.gameId = gameID;
		messageToClient.playerNumber = playerID;
		messageToClient.message = gameMessage.toJSONString();
		
		return messageToClient;
		
	}
	
	
	private Board createBoard(int gameType, int playerNum) // Perhaps encapsulate this in its own object?
	{
		if(gameType == Definitions.GAMETYPETICTACTOE)
		{
			int TicTacToe_BoardHeight = 3;
			int TicTacToe_BoardWidth = 3;
			
			return new Board(TicTacToe_BoardHeight, TicTacToe_BoardWidth, Definitions.GAMETYPETICTACTOE);
		}
		
		return null;
	}

	private MoveValidator setMoveValidator() // Perhaps encapsulate this in its own object? 
	{
		if(gameType == Definitions.GAMETYPETICTACTOE)
		{
			return new TicTacToeValidator(board);
		}
		return null;
	}

	private ActionReactor setActionReactor() // Perhaps encapsulate this in its own object? 
	{
		if(gameType == Definitions.GAMETYPETICTACTOE)
		{
			return new TicTacToeReactor(gof, manipulator);
		}
		else
		{
			return null;
		}
	}
	
	private void populateBoard() // Perhaps encapsulate this in its own object?
	{
		if(gameType == Definitions.GAMETYPETICTACTOE)
		{
			return; // No set up required for TicTacToe.
		}
	}
}
