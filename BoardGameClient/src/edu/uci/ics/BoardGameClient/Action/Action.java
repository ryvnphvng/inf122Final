package edu.uci.ics.BoardGameClient.Action;

import javax.swing.JOptionPane;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import edu.uci.ics.BoardGameClient.Board.Board;
import edu.uci.ics.BoardGameClient.Common.Definitions;
import edu.uci.ics.BoardGameClient.Common.Message;
import edu.uci.ics.BoardGameClient.Engine.Game;
import edu.uci.ics.BoardGameClient.GUI.GUI;

public class Action {

	private Game game;
	private int gameType;
	private Board board;
	private BoardManipulator manipulator;
	private ActionReactor reactor; // abstract
	private GameObjectFactory gof;
	private MoveValidator validator; // abstract
	private int numberOfPlayers;
	private GUI gui = new GUI();

	public Action(Game game) {
		this.game = game;
		int gameType = -1; 
		int numberOfPlayers = -1;
		
		Object[] options = { "TicTacToe" };
		int n = JOptionPane.showOptionDialog(null, "Please select a game",
				"Status", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		if (n == 0) {  //TicTacToe was selected
			gameType = Definitions.GAMETYPETICTACTOE;
			numberOfPlayers = 2;
		} 
		
		
		setUp(gameType, numberOfPlayers);
		
	}

	
	private void setUp(int gameType, int numberOfPlayers){
		this.gameType = gameType;
		gof = new GameObjectFactory();
		
		board = createBoard(gameType, numberOfPlayers);
		manipulator = new BoardManipulator(board, game, this);
		
	}
		
	public void setGame(Game game) {
		this.game = game;
	}

	public int getGameType() {
		return gameType;
	}

	public void setNumberOfPlayers(int numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
	}

	public void createGame(int gameType) {
		this.gameType = gameType;
		game.createGame(gameType);
		board = createBoard(gameType, numberOfPlayers);
		manipulator = new BoardManipulator(board, game, this);
	}

	public void shutdown() {
		game.shutdown();
	}

	public void messageFromServer(Message message) {
		JSONObject gameMessage;
		// The client receives messages from the server and implements them
		try {
			gameMessage = (JSONObject) new JSONParser().parse(message.message);

			if (gameMessage.get("MessageType").equals("Create")) { // Client Side create game object

				Integer gameType = (Integer) gameMessage.get("GameType");
				Integer playerID = (Integer) gameMessage.get("PlayerID");
				Integer row = (Integer) gameMessage.get("Row");
				Integer col = (Integer) gameMessage.get("Col");
				Integer gameID = (Integer) gameMessage.get("GameID");
				Integer objectID = (Integer) gameMessage.get("ObjectID");
				Integer objectType = (Integer) gameMessage.get("ObjectType");

				manipulator.createGameObject(gof.createGameObject(playerID, objectType, objectID, row, col), gameID,
						gameType, objectID, objectType, playerID, row, col);

				reactor.updateBoard();

			} else if (gameMessage.get("MessageType").equals("Delete")) { // Client Side delete game object
				Integer playerID = (Integer) gameMessage.get("PlayerID");
				Integer gameID = (Integer) gameMessage.get("GameID");
				Integer objectID = (Integer) gameMessage.get("ObjectID");

				manipulator.deleteGameObject(objectID, gameID, playerID);

				reactor.updateBoard();

			} else if (gameMessage.get("MessageType").equals("Move")) { // Client Side move game object
				Integer playerID = (Integer) gameMessage.get("PlayerID");
				Integer objectID = (Integer) gameMessage.get("ObjectID");
				Integer gameID = (Integer) gameMessage.get("GameID");
				Integer row = (Integer) gameMessage.get("Row");
				Integer col = (Integer) gameMessage.get("Col");

				manipulator.moveGameObject(objectID, playerID, row, col, gameID);

				reactor.updateBoard();

			} else if (gameMessage.get("MessageType").equals("Swap")) { // Client Side swap game object
				Integer objectID1 = (Integer) gameMessage.get("ObjectID1");
				Integer objectID2 = (Integer) gameMessage.get("ObjectID2");
				Integer gameID = (Integer) gameMessage.get("GameID");
				Integer playerID = (Integer) gameMessage.get("PlayerID");

				manipulator.swapGameObjects(objectID1, objectID2, playerID, gameID);

				reactor.updateBoard();

			} else if (gameMessage.get("MessageType").equals("Win")) { // Client Side recognize winners
				// GUI should display game won message to each player that won

			} else if (gameMessage.get("MessageType").equals("Lose")) { // Client Side recognize losers
				// GUI should display game lost message to each player that lost
			} else if (gameMessage.get("MessageType").equals("Tie")) { // Client Side recognizes a tie
				// GUI should display game tie message to each player that tie
			} else if (gameMessage.get("MessageType").equals("InvalidMove")) { // Client Side invalid move handling
				// GUI should display that chosen move is invalid
			}

		} catch (ParseException e) {
			// Catch a parse exception for JSON object
			e.printStackTrace();
		}

	}

	public Message encodeMessage(JSONObject gameMessage) {
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
		if (gameType == Definitions.GAMETYPETICTACTOE) {
			int TicTacToe_BoardHeight = 3;
			int TicTacToe_BoardWidth = 3;

			return new Board(TicTacToe_BoardHeight, TicTacToe_BoardWidth, Definitions.GAMETYPETICTACTOE);
		}

		return null;
	}

	private MoveValidator setMoveValidator() // Perhaps encapsulate this in its own object?
	{
		if (gameType == Definitions.GAMETYPETICTACTOE) {
			return new TicTacToeValidator(board);
		}
		return null;
	}

	private ActionReactor setActionReactor() // Perhaps encapsulate this in its own object?
	{
		if (gameType == Definitions.GAMETYPETICTACTOE) {
			return new TicTacToeReactor(gof, manipulator);
		} else {
			return null;
		}
	}
}
