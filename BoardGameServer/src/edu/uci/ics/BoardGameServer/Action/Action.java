package edu.uci.ics.BoardGameServer.Action;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import edu.uci.ics.BoardGameClient.Common.Definitions;
import edu.uci.ics.BoardGameServer.Board.Board;
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

	public static void messageFromClient(Message message) {
		Object obj=JSONValue.parse(message.message);
		JSONArray array=(JSONArray)obj;		
		JSONObject gameMessage=(JSONObject)array.get(0);
		
		/*
		  //testing; listing out the gameMessages 
		  System.out.println(obj.toString());
		  System.out.println("======Game Messages==========");
		  System.out.println("MessageType :" + gameMessage.get("MessageType")); 
		  System.out.println("GameID :" + gameMessage.get("GameID"));
		  System.out.println("PlayerID :" + gameMessage.get("PlayerID"));
		  System.out.println("ObjectID :" + gameMessage.get("ObjectID"));
		  System.out.println("Row :" + gameMessage.get("Row"));
		  System.out.println("Col :" + gameMessage.get("Col"));
		  System.out.println("Message :" + gameMessage.get("Message"));
		*/
	}
	
	public static Message messageToClient() {
		/*
		Message message = new Message();
		message.message = "[{\"MessageType\":\"startGame\" , \"GameID\":\"25\", \"PlayerID\":\"25\", \"ObjectID\":\"25\",\"Row\":\"0\",\"Col\":\"1\",\"Message\":\"Place\" }]";
		return message;
		*/
		
		return null;
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
