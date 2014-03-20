package edu.uci.ics.BoardGameServer.Action;

import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import edu.uci.ics.BoardGameServer.Board.Board;
import edu.uci.ics.BoardGameServer.Board.GameObject;
import edu.uci.ics.BoardGameServer.Board.GameObjectDefinitions;
import edu.uci.ics.BoardGameServer.Common.Definitions;
import edu.uci.ics.BoardGameServer.Common.Message;
import edu.uci.ics.BoardGameServer.Engine.Game;



public class Action {

	private Game game;
	private int gameType;
	private Board board;
	private BoardManipulator manipulator;
	private GameOver gameOver;
	private ActionReactor reactor; // abstract
	private GameObjectFactory gof;
	private MoveValidator validator; // abstract
	private int numberOfPlayers;

	public Action(Game game, int gameType, int numberOfPlayers)
	{
		this.game = game;
		this.gameType = gameType;
		this.numberOfPlayers = numberOfPlayers;
		gof = new GameObjectFactory();
		board = createBoard(gameType, numberOfPlayers);
		manipulator = new BoardManipulator(board, game, this);
		
		reactor = setActionReactor();
		validator = setMoveValidator();
		gameOver = setGameOver(board);
	}
	

	public void setGame(Game game) {
		this.game = game;
	}

	public void setGameType(int gameType) {
		this.gameType = gameType;
	}
	
	public Board getBoard() {
		return board;
	}
	
	@SuppressWarnings("unchecked")
	public void gameCreated() {
		for(int i=0; i<numberOfPlayers; i++)
		{
			JSONObject gameMessage=new JSONObject();
			gameMessage.put("MessageType", "BoardCreated");
			gameMessage.put("PlayerID", i);
			
			game.messageToClient(encodeMessage(gameMessage));
		}
		
		populateBoard();
	}
	
	public void destroyGame() {
		// TODO: do stuff with this
		System.out.println("destroyGame");
	}

	@SuppressWarnings("unchecked")
	public void messageFromClient(Message message) {
		
		JSONObject gameMessage;
		try {
			gameMessage = (JSONObject) new JSONParser().parse(message.message);
			
			if  (validator.isValidMove(gameMessage, message)) {
				
				if(gameMessage.get("MessageType").equals("Create")){ // Client wants to create a Game Object
					
					Integer gameType = new Integer(((Long) gameMessage.get("GameType")).intValue());
					Integer playerID = new Integer(message.playerNumber);
					Integer row = new Integer(((Long) gameMessage.get("Row")).intValue());
					Integer col = new Integer (((Long) gameMessage.get("Col")).intValue());
					Integer gameID = new Integer(message.gameId);
					
					GameObject g = gof.createGameObject(gameType, playerID, row, col);
					
					manipulator.createGameObject(g, gameID, gameType, g.getObjID(), g.getObjectType(), playerID, row, col, numberOfPlayers);
					
					reactor.updateBoard(gameMessage);
					
					ArrayList<Integer> winners = gameOver.isWinConditionMet(gameMessage);
					ArrayList<Integer> losers = gameOver.isLoseConditionMet(gameMessage);
					ArrayList<Integer> ties = gameOver.isTieConditionMet(gameMessage);
					
					sendGameOverMessages(message, winners, losers, ties);
					
					validator.nextPlayerTurn();
					
				}
				else if(gameMessage.get("MessageType").equals("Delete")){ // Client wants to delete a Game Object
					
					Integer playerID = new Integer(((Long) gameMessage.get("PlayerID")).intValue());
					Integer gameID = new Integer(((Long) gameMessage.get("GameID")).intValue());
					Integer objectID = new Integer(((Long) gameMessage.get("ObjectID")).intValue());
					
					manipulator.deleteGameObject(objectID, gameID, playerID, numberOfPlayers);
					
					reactor.updateBoard(gameMessage);
					
					ArrayList<Integer> winners = gameOver.isWinConditionMet(gameMessage);
					ArrayList<Integer> losers = gameOver.isLoseConditionMet(gameMessage);
					ArrayList<Integer> ties = gameOver.isTieConditionMet(gameMessage);
					
					sendGameOverMessages(message, winners, losers, ties);
					
					validator.nextPlayerTurn();
				}
				else if(gameMessage.get("MessageType").equals("Move")){ // Client wants to move a Game Object
					
					Integer playerID = new Integer(((Long) gameMessage.get("PlayerID")).intValue());
					Integer objectID = new Integer(((Long) gameMessage.get("ObjectID")).intValue());
					Integer gameID = new Integer(((Long) gameMessage.get("GameID")).intValue());
					Integer row = new Integer(((Long) gameMessage.get("Row")).intValue());
					Integer col = new Integer(((Long) gameMessage.get("Col")).intValue());
					
					manipulator.moveGameObject(objectID, playerID, row, col, gameID, numberOfPlayers);
					
					reactor.updateBoard(gameMessage);
					
					ArrayList<Integer> winners = gameOver.isWinConditionMet(gameMessage);
					ArrayList<Integer> losers = gameOver.isLoseConditionMet(gameMessage);
					ArrayList<Integer> ties = gameOver.isTieConditionMet(gameMessage);
					
					sendGameOverMessages(message, winners, losers, ties);
					
					validator.nextPlayerTurn();
				}
				else if(gameMessage.get("MessageType").equals("Swap")){ // Client wants to swap two Game Objects
					
					Integer objectID1 = new Integer(((Long) gameMessage.get("ObjectID1")).intValue());
					Integer objectID2 = new Integer(((Long) gameMessage.get("ObjectID2")).intValue());
					Integer gameID = new Integer(((Long) gameMessage.get("GameID")).intValue());
					Integer playerID = new Integer(((Long) gameMessage.get("PlayerID")).intValue());
					
					manipulator.swapGameObjects(objectID1, objectID2, playerID, gameID, numberOfPlayers);
					
					reactor.updateBoard(gameMessage);
					
					ArrayList<Integer> winners = gameOver.isWinConditionMet(gameMessage);
					ArrayList<Integer> losers = gameOver.isLoseConditionMet(gameMessage);
					ArrayList<Integer> ties = gameOver.isTieConditionMet(gameMessage);
					
					sendGameOverMessages(message, winners, losers, ties);
					
					validator.nextPlayerTurn();
				}
					
			}
			else{ // Client sent an invalid move
				
				JSONObject notValidMove = new JSONObject();
				notValidMove.put("MessageType", "InvalidMove");
				notValidMove.put("GameID", message.gameId);
				notValidMove.put("PlayerID", message.playerNumber);
				game.messageToClient(encodeMessage(notValidMove));
			}	
			
				
		} catch (ParseException e) {
			e.printStackTrace();
		}	
		
		
	}
	
	public Message encodeMessage(JSONObject gameMessage) {

		
		int playerID = Integer.parseInt(gameMessage.get("PlayerID").toString());
		
		Message messageToClient = new Message();
		messageToClient.playerNumber = playerID;
		messageToClient.message = gameMessage.toJSONString();
		
		return messageToClient;
		
	}
	
	@SuppressWarnings("unchecked")
	public void sendGameOverMessages(Message message, ArrayList<Integer> winners, ArrayList<Integer> losers, ArrayList<Integer> ties)
	{
		if(winners.size() > 0) // There are one or more winners
		{
			for(int i=0; i<winners.size(); i++) // Send a game over message to each person that won.
			{
				JSONObject winMessage = new JSONObject();
				winMessage.put("MessageType", "Win");
				winMessage.put("GameID", message.gameId);
				winMessage.put("PlayerID", winners.get(i));
				game.messageToClient(encodeMessage(winMessage));
			}
		}
		
		if(losers.size() > 0) // There are one or more losers
		{
			for(int i=0; i<losers.size(); i++) // Send a game over message to each person that lost.
			{
				JSONObject loseMessage = new JSONObject();
				loseMessage.put("MessageType", "Lose");
				loseMessage.put("GameID", message.gameId);
				loseMessage.put("PlayerID", losers.get(i));
				game.messageToClient(encodeMessage(loseMessage));
			}
		}
		
		if(ties.size() > 0) // There is a tie
		{
			for(int i=0; i<ties.size(); i++) // Send a game over message to each person that tied.
			{
				JSONObject tieMessage = new JSONObject();
				tieMessage.put("MessageType", "Tie");
				tieMessage.put("GameID", message.gameId);
				tieMessage.put("PlayerID", ties.get(i));
				game.messageToClient(encodeMessage(tieMessage));
			}
		}
	}
	
	private Board createBoard(int gameType, int playerNum) // Perhaps encapsulate this in its own object?
	{
		if(gameType == Definitions.GAMETYPETICTACTOE)
		{
			int TicTacToe_BoardHeight = 3;
			int TicTacToe_BoardWidth = 3;
			
			return new Board(TicTacToe_BoardHeight, TicTacToe_BoardWidth, Definitions.GAMETYPETICTACTOE);
		}
		else if(gameType == Definitions.GAMETYPECONNECTFOUR)
		{
			int Connect4_BoardHeight = 6;
			int Connect4_BoardWidth = 7;
			
			return new Board(Connect4_BoardHeight, Connect4_BoardWidth, Definitions.GAMETYPECONNECTFOUR);
		}
		else if(gameType == Definitions.GAMETYPECHECKERS)
		{
			int Checkers_BoardHeight = 8;
			int Checkers_BoardWidth = 8;
			
			return new Board(Checkers_BoardHeight, Checkers_BoardWidth, Definitions.GAMETYPECHECKERS);
		}
		return null;
	}

	private MoveValidator setMoveValidator() // Perhaps encapsulate this in its own object? 
	{
		if(gameType == Definitions.GAMETYPETICTACTOE)
		{
			return new TicTacToeValidator(board, numberOfPlayers);
		}
		else if(gameType == Definitions.GAMETYPECONNECTFOUR)
		{
			return new Connect4Validator(board, numberOfPlayers);
		}
		else if(gameType == Definitions.GAMETYPECHECKERS)
		{
			return new CheckersValidator(board, numberOfPlayers);
		}
		return null;
	}

	private ActionReactor setActionReactor() // Perhaps encapsulate this in its own object? 
	{
		if(gameType == Definitions.GAMETYPETICTACTOE)
		{
			return new TicTacToeReactor(gof, manipulator);
		}
		else if(gameType == Definitions.GAMETYPECONNECTFOUR)
		{
			return new Connect4Reactor(gof, manipulator);
		}
		else if(gameType == Definitions.GAMETYPECHECKERS)
		{
			return new CheckersReactor(gof, manipulator);
		}
		else
		{
			return null;
		}
	}

	private GameOver setGameOver(Board b) // Perhaps encapsulate this in its own object? 
	{
		if(gameType == Definitions.GAMETYPETICTACTOE)
		{
			return new TicTacToeGameOver(b);
		}
		else if(gameType == Definitions.GAMETYPECONNECTFOUR)
		{
			return new Connect4GameOver(b);
		}
		else if(gameType == Definitions.GAMETYPECHECKERS)
		{
			return new CheckersGameOver(b);
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
		else if(gameType == Definitions.GAMETYPECONNECTFOUR)
		{
			return;
		}
		else if(gameType == Definitions.GAMETYPECHECKERS)
		{
			int player1 = 0;
			int player2 = 1;
			for(int i=0; i<board.getWidth(); i++)
			{
				if(i % 2 == 1)
				{
					int row = 0;
					int col = i;
					GameObject g = gof.createGameObject(gameType, player2, row, col);

					manipulator.createGameObject(g, -1, gameType, g.getObjID(), g.getObjectType(), g.getOwner(), g.getRow(), g.getCol(), numberOfPlayers);
					
					row = 2;
					col = i;
					g = gof.createGameObject(gameType, player2, row, col);
					manipulator.createGameObject(g, -1, gameType, g.getObjID(), g.getObjectType(), g.getOwner(), g.getRow(), g.getCol(), numberOfPlayers);
					
					row = 6;
					col = i;
					g = gof.createGameObject(gameType, player1, row, col);
					manipulator.createGameObject(g, -1, gameType, g.getObjID(), g.getObjectType(), g.getOwner(), g.getRow(), g.getCol(), numberOfPlayers);
				}
				else // i % 2 == 0
				{
					int row = 1;
					int col = i;
					GameObject g = gof.createGameObject(gameType, player2, row, col);
					manipulator.createGameObject(g, -1, gameType, g.getObjID(), g.getObjectType(), g.getOwner(), g.getRow(), g.getCol(), numberOfPlayers);
					
					row = 5;
					col = i;
					g = gof.createGameObject(gameType, player1, row, col);
					manipulator.createGameObject(g, -1, gameType, g.getObjID(), g.getObjectType(), g.getOwner(), g.getRow(), g.getCol(), numberOfPlayers);
					
					row = 7;
					col = i;
					g = gof.createGameObject(gameType, player1, row, col);
					manipulator.createGameObject(g, -1, gameType, g.getObjID(), g.getObjectType(), g.getOwner(), g.getRow(), g.getCol(), numberOfPlayers);
				}
			}
					
		}
	}
}
