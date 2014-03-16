package edu.uci.ics.BoardGameServer.Action;

import java.util.ArrayList;

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
	private Board board;
	private BoardManipulator manipulator;
	private GameOver gameOver;
	private ActionReactor reactor; // abstract
	private GameObjectFactory gof;
	private MoveValidator validator; // abstract

	public Action(Game game, int gameType, int numberOfPlayers)
	{
		this.game = game;
		this.gameType = gameType;
		gof = new GameObjectFactory();
		board = createBoard(gameType, numberOfPlayers);
		manipulator = new BoardManipulator(board, game, this);
		
		reactor = setActionReactor();
		validator = setMoveValidator();
		gameOver = setGameOver(board);
		
		populateBoard();
	}
	

	public void setGame(Game game) {
		this.game = game;
	}

	public void setGameType(int gameType) {
		this.gameType = gameType;
	}

	@SuppressWarnings("unchecked")
	public void messageFromClient(Message message) {
		
		JSONObject gameMessage;
		try {
			gameMessage = (JSONObject) new JSONParser().parse(message.message);
			
			if  (validator.isValidMove(gameMessage)) {
				
				if(gameMessage.get("MessageType").equals("Create")){ // Client wants to create a Game Object
					
					Integer gameType = (Integer) gameMessage.get("GameType");
					Integer playerID = message.playerNumber;
					Integer row = (Integer) gameMessage.get("Row");
					Integer col = (Integer) gameMessage.get("Col");
					Integer gameID = message.gameId;
					
					GameObject g = gof.createGameObject(gameType, playerID, row, col);
					
					manipulator.createGameObject(g, gameID, gameType, g.getObjID(), g.getObjectType(), playerID, row, col);
					
					reactor.updateBoard();
					
					ArrayList<Integer> winners = gameOver.isWinConditionMet(gameMessage);
					ArrayList<Integer> losers = gameOver.isLoseConditionMet(gameMessage);
					ArrayList<Integer> ties = gameOver.isTieConditionMet(gameMessage);
					
					if(winners.size() > 0) // There are one or more winners
					{
						for(int i=0; i<winners.size(); i++) // Send a game over message to each person that won.
						{
							JSONObject winMessage = new JSONObject();
							winMessage.put("MessageType", "Win");
							winMessage.put("GameID", message.gameId);
							winMessage.put("PlayerID", winners.get(i));
							game.messageToClient(decodeMessage(winMessage));
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
							game.messageToClient(decodeMessage(loseMessage));
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
							game.messageToClient(decodeMessage(tieMessage));
						}
					}
					
				}
				
				else if(gameMessage.get("MessageType").equals("Delete")){ // Client wants to delete a Game Object
					
					Integer playerID = (Integer) gameMessage.get("PlayerID");
					Integer gameID = (Integer) gameMessage.get("GameID");
					Integer objectID = (Integer) gameMessage.get("ObjectID");
					
					manipulator.deleteGameObject(objectID, gameID, playerID);
					
					reactor.updateBoard();
					
					ArrayList<Integer> winners = gameOver.isWinConditionMet(gameMessage);
					ArrayList<Integer> losers = gameOver.isLoseConditionMet(gameMessage);
					ArrayList<Integer> ties = gameOver.isTieConditionMet(gameMessage);
					
					if(winners.size() > 0) // There are one or more winners
					{
						for(int i=0; i<winners.size(); i++) // Send a game over message to each person that won.
						{
							JSONObject winMessage = new JSONObject();
							winMessage.put("MessageType", "Win");
							winMessage.put("GameID", message.gameId);
							winMessage.put("PlayerID", winners.get(i));
							game.messageToClient(decodeMessage(winMessage));
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
							game.messageToClient(decodeMessage(loseMessage));
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
							game.messageToClient(decodeMessage(tieMessage));
						}
					}
				}
				else if(gameMessage.get("MessageType").equals("Move")){ // Client wants to move a Game Object
					
					Integer playerID = (Integer) gameMessage.get("PlayerID");
					Integer objectID = (Integer) gameMessage.get("ObjectID");
					Integer gameID = (Integer) gameMessage.get("GameID");
					Integer row = (Integer) gameMessage.get("Row");
					Integer col = (Integer) gameMessage.get("Col");
					
					manipulator.moveGameObject(objectID, playerID, row, col, gameID);
					
					reactor.updateBoard();
					
					ArrayList<Integer> winners = gameOver.isWinConditionMet(gameMessage);
					ArrayList<Integer> losers = gameOver.isLoseConditionMet(gameMessage);
					ArrayList<Integer> ties = gameOver.isTieConditionMet(gameMessage);
					
					if(winners.size() > 0) // There are one or more winners
					{
						for(int i=0; i<winners.size(); i++) // Send a game over message to each person that won.
						{
							JSONObject winMessage = new JSONObject();
							winMessage.put("MessageType", "Win");
							winMessage.put("GameID", message.gameId);
							winMessage.put("PlayerID", winners.get(i));
							game.messageToClient(decodeMessage(winMessage));
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
							game.messageToClient(decodeMessage(loseMessage));
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
							game.messageToClient(decodeMessage(tieMessage));
						}
					}
				}
				else if(gameMessage.get("MessageType").equals("Swap")){ // Client wants to swap two Game Objects
					
					Integer objectID1 = (Integer) gameMessage.get("ObjectID1");
					Integer objectID2 = (Integer) gameMessage.get("ObjectID2");
					Integer gameID = (Integer) gameMessage.get("GameID");
					Integer playerID = (Integer) gameMessage.get("PlayerID");
					
					manipulator.swapGameObjects(objectID1, objectID2, playerID, gameID);
					
					reactor.updateBoard();
					
					ArrayList<Integer> winners = gameOver.isWinConditionMet(gameMessage);
					ArrayList<Integer> losers = gameOver.isLoseConditionMet(gameMessage);
					ArrayList<Integer> ties = gameOver.isTieConditionMet(gameMessage);
					
					if(winners.size() > 0) // There are one or more winners
					{
						for(int i=0; i<winners.size(); i++) // Send a game over message to each person that won.
						{
							JSONObject winMessage = new JSONObject();
							winMessage.put("MessageType", "Win");
							winMessage.put("GameID", message.gameId);
							winMessage.put("PlayerID", winners.get(i));
							game.messageToClient(decodeMessage(winMessage));
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
							game.messageToClient(decodeMessage(loseMessage));
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
							game.messageToClient(decodeMessage(tieMessage));
						}
					}
				}
					
			}
			else{ // Client sent an invalid move
				
				JSONObject notValidMove = new JSONObject();
				notValidMove.put("MessageType", "InvalidMove");
				notValidMove.put("GameID", message.gameId);
				notValidMove.put("PlayerID", message.playerNumber);
				game.messageToClient(decodeMessage(notValidMove));
				
			}	
			
				
		} catch (ParseException e) {
			e.printStackTrace();
		}	
		
		
	}
	
	public Message decodeMessage(JSONObject gameMessage) {

		
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

	private GameOver setGameOver(Board b) // Perhaps encapsulate this in its own object? 
	{
		if(gameType == Definitions.GAMETYPETICTACTOE)
		{
			return new TicTacToeGameOver(b);
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
