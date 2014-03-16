package edu.uci.ics.BoardGameClient.Action;

import edu.uci.ics.BoardGameClient.Board.Board;
import edu.uci.ics.BoardGameClient.Common.Message;
import edu.uci.ics.BoardGameClient.Engine.Game;
import edu.uci.ics.BoardGameClient.GUI.GUI;
import edu.uci.ics.BoardGameClient.Action.BoardManipulator;
import edu.uci.ics.BoardGameClient.Action.ActionReactor;
import edu.uci.ics.BoardGameClient.Action.GameObjectFactory;
import edu.uci.ics.BoardGameClient.Action.MoveValidator;
import edu.uci.ics.BoardGameClient.Action.TicTacToeReactor;
import edu.uci.ics.BoardGameClient.Action.TicTacToeValidator;
import edu.uci.ics.BoardGameClient.Common.Definitions;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Action {

	private Game game;
	private int gameType;
	private Board board;
	private BoardManipulator manipulator;
	private ActionReactor reactor; // abstract
	private GameObjectFactory gof;
	private MoveValidator validator; // abstract
	private GUI gui = new GUI();

	public Action(Game game, int gameType, int numberOfPlayers) {
		this.game = game;
		this.gameType = gameType;
		gof = new GameObjectFactory();
		board = createBoard(gameType, numberOfPlayers);
		manipulator = new BoardManipulator(board, game, this);
		
		reactor = setActionReactor();
		validator = setMoveValidator();
		
		gui.setAction(this);
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public void createGame(int gameType) {
		this.gameType = gameType;
		game.createGame(gameType);
	}

	public void disconnect() {
		game.disconnect();
	}

	public void shutdown() {
		game.shutdown();
	}

	public void messageFromServer(Message message) {
		JSONObject gameMessage;
		// implement message from server
		try {
			gameMessage = (JSONObject) new JSONParser().parse(message.message);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	private Message encodeMessage(JSONObject gameMessage) {
		int playerID = Integer.parseInt(gameMessage.get("PlayerID").toString());
		int gameID = Integer.parseInt(gameMessage.get("GameID").toString());
		
		Message messageToServer = new Message();
		messageToServer.gameId = gameID;
		messageToServer.playerNumber = playerID;
		messageToServer.message = gameMessage.toJSONString();
		
		return messageToServer;
		
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
}
