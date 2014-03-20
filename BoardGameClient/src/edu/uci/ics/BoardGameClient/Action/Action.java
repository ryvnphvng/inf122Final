package edu.uci.ics.BoardGameClient.Action;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import edu.uci.ics.BoardGameClient.Board.*;
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
	private int numberOfPlayers;
	private GUI gui;
	private boolean allowFurtherMoves;

	public Action(Game game) {
		this.game = game;
		allowFurtherMoves = true;

		gui = new GUI(this);

		// TODO: comment out the below line when testing login
		//gui.setToPickGame();
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Game getGame() {
		return game;
	}

	public void setNumberOfPlayers(int numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
	}

	public int getGameType() {
		return gameType;
	}

	public void shutdown() {
		game.shutdown();
	}

	public void createGame(int gameType) {
		numberOfPlayers = 2;

		gof = new GameObjectFactory();
		board = createBoard(gameType, numberOfPlayers);
		manipulator = new BoardManipulator(board, game, this);
		reactor = setActionReactor();

		game.createGame(gameType);

		gui.setBoard(board);
		gui.setToBoard();
		gui.appendText("Looking for game");
	}

	private Board createBoard(int gameType, int playerNum) {
		if (gameType == Definitions.GAMETYPETICTACTOE) {
			return new Board(3, 3, Definitions.GAMETYPETICTACTOE);
		}
		if (gameType == Definitions.GAMETYPECONNECTFOUR) {
			return new Board(6, 7, Definitions.GAMETYPECONNECTFOUR);
		}
		else if (gameType == Definitions.GAMETYPECHECKERS) {
			return new Board(8, 8, Definitions.GAMETYPECHECKERS);
		}
		return null;
	}

	public void messageFromServer(Message message) {
		// The client receives messages from the server and implements them
		JSONObject gameMessage;

		try {
			gameMessage = (JSONObject) new JSONParser().parse(message.message);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		if (gameMessage.get("MessageType").equals("Login"))
		{
			String username = new String ((String) gameMessage.get("Username"));
			String password = new String ((String) gameMessage.get("Password"));
			boolean loginSuccessful = ((Boolean) gameMessage.get("LoginSuccessful"));
			
			if(loginSuccessful)
			{
				String email = new String ((String) gameMessage.get("Email"));
				gui.appendText("User Profile");
				gui.appendText("--------------");
				gui.appendText("Username: " + username);
				gui.appendText("Email: " + email);
				gui.setToPickGame();
			}
			else 
				gui.appendText("Login Invalid");
		}

		if (gameMessage.get("MessageType").equals("BoardCreated")) {
			// Game has been created
			gui.updateBoard();
			gui.appendText("Joined game");
			return;
		}

		if (gameMessage.get("MessageType").equals("Create")) {
			// Client Side create game object
			Integer gameType = new Integer(((Long) gameMessage.get("GameType")).intValue());
			Integer playerID = new Integer(((Long) gameMessage.get("PlayerID")).intValue());
			Integer row = new Integer(((Long) gameMessage.get("Row")).intValue());
			Integer col = new Integer(((Long) gameMessage.get("Col")).intValue());
			Integer gameID = new Integer(((Long) gameMessage.get("GameID")).intValue());
			Integer objectID = new Integer(((Long) gameMessage.get("ObjectID")).intValue());
			Integer objectType = new Integer(((Long) gameMessage.get("ObjectType")).intValue());

			manipulator.createGameObject(gof.createGameObject(playerID, objectType, objectID, row, col), gameID,
					gameType, objectID, objectType, playerID, row, col);

			reactor.updateBoard();

			gui.updateBoard();
			return;
		}

		if (gameMessage.get("MessageType").equals("Delete")) {
			// Client Side delete game object
			Integer playerID = new Integer(((Long) gameMessage.get("PlayerID")).intValue());
			Integer gameID = new Integer(((Long) gameMessage.get("GameID")).intValue());
			Integer objectID = new Integer(((Long) gameMessage.get("ObjectID")).intValue());

			manipulator.deleteGameObject(objectID, gameID, playerID);

			reactor.updateBoard();

			gui.updateBoard();
			return;
		}

		if (gameMessage.get("MessageType").equals("Move")) {
			// Client Side move game object
			Integer playerID = new Integer(((Long) gameMessage.get("PlayerID")).intValue());
			Integer objectID = new Integer(((Long) gameMessage.get("ObjectID")).intValue());
			Integer gameID = new Integer(((Long) gameMessage.get("GameID")).intValue());
			Integer row = new Integer(((Long) gameMessage.get("Row")).intValue());
			Integer col = new Integer(((Long) gameMessage.get("Col")).intValue());

			manipulator.moveGameObject(objectID, playerID, row, col, gameID);

			reactor.updateBoard();

			gui.updateBoard();
			return;
		}

		if (gameMessage.get("MessageType").equals("Swap")) {
			// Client Side swap game object
			Integer objectID1 = new Integer(((Long) gameMessage.get("ObjectID1")).intValue());
			Integer objectID2 = new Integer(((Long) gameMessage.get("ObjectID2")).intValue());
			Integer gameID = new Integer(((Long) gameMessage.get("GameID")).intValue());
			Integer playerID = new Integer(((Long) gameMessage.get("PlayerID")).intValue());

			manipulator.swapGameObjects(objectID1, objectID2, playerID, gameID);

			reactor.updateBoard();

			gui.updateBoard();
			return;
		}

		if (gameMessage.get("MessageType").equals("Win")) {
			gui.appendText("You have won the game");
			disallowNewMovesToServer();
			return;
		}

		if (gameMessage.get("MessageType").equals("Lose")) {
			gui.appendText("You have lost the game");
			disallowNewMovesToServer();
			return;
		}

		if (gameMessage.get("MessageType").equals("Tie")) {
			gui.appendText("The game is a tie");
			disallowNewMovesToServer();
			return;
		}

		if (gameMessage.get("MessageType").equals("InvalidMove")) {
			gui.appendText("Invalid move");
			return;
		}

	}

	public Message encodeMessage(JSONObject gameMessage) {
		Message messageToServer = new Message();
		messageToServer.message = gameMessage.toJSONString();
		return messageToServer;
	}

	public boolean areFurtherMovesAllowed()
	{
		return this.allowFurtherMoves;
	}

	public void disallowNewMovesToServer()
	{
		this.allowFurtherMoves = false;
	}

	private ActionReactor setActionReactor() {
		if (gameType == Definitions.GAMETYPETICTACTOE) {
			return new TicTacToeReactor(gof, manipulator);
		}
		if (gameType == Definitions.GAMETYPECONNECTFOUR) {
			return new Connect4Reactor(gof, manipulator);
		}
		else if (gameType == Definitions.GAMETYPECHECKERS)
		{
			return new CheckersReactor(gof, manipulator);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public boolean loginUser(String username, String password) {

		JSONObject loginMessage = new JSONObject();
		loginMessage.put("MessageType", "Login");
		loginMessage.put("Username", username);
		loginMessage.put("Password", password);
		loginMessage.put("LoginSuccessful", false);

		Message message = encodeMessage(loginMessage);
		game.messageToServer(message);


		return true;
	}
}
