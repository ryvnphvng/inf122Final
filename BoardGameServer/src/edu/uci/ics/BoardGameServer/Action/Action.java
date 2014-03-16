package edu.uci.ics.BoardGameServer.Action;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import edu.uci.ics.BoardGameServer.Board.Board;
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
					manipulator.createGameObject(gof.createGameObject((int) gameMessage.get("GameType"), (int) gameMessage.get("PlayerID"), (int) gameMessage.get("Row"), (int) gameMessage.get("Col")), 
					(int) gameMessage.get("GameID"), (int) gameMessage.get("GameType"), (int) gameMessage.get("ObjectID"), (int) gameMessage.get("ObjectType"), (int) gameMessage.get("PlayerID"), 
					(int) gameMessage.get("Row"), (int) gameMessage.get("Col"));
				}
				
				else if(gameMessage.get("MessageType").equals("Delete")){
					manipulator.deleteGameObject((int) gameMessage.get("ObjectID"), (int) gameMessage.get("GameID"), (int) gameMessage.get("PlayerID"));
				}
				else if(gameMessage.get("MessageType").equals("Move")){
					manipulator.moveGameObject((int) gameMessage.get("ObjectID"), (int) gameMessage.get("PlayerID"), (int) gameMessage.get("Row"), (int) gameMessage.get("Col"), (int) gameMessage.get("GameID"));
					
				}
				else if(gameMessage.get("MessageType").equals("Swap")){
					manipulator.swapGameObjects((int) gameMessage.get("ObjectID1"), (int) gameMessage.get("ObjectID2"), (int) gameMessage.get("PlayerID"), (int) gameMessage.get("GameID"));
					
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
