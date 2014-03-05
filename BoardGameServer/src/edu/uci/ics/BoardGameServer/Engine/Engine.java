package edu.uci.ics.BoardGameServer.Engine;

import java.util.Map;
import java.util.TreeMap;

import edu.uci.ics.BoardGameServer.Action.Action;
import edu.uci.ics.BoardGameServer.Board.Board;
import edu.uci.ics.BoardGameServer.Distribution.Distribution;

public class Engine {

	private Action action;
	private Board board;
	private Distribution distribution;
	private Map<Integer, Game> games = new TreeMap<Integer, Game>();
	private int nextGameNumber = 0;

	public void run() {
		startup();
	}

	private void startup() {
		action = new Action();
		board = new Board();
		distribution = new Distribution();

		action.setBoard(board);
		board.setAction(action);
	}

	public int createGame(String type, int numberOfPlayers) {
		int gameNumber = nextGameNumber++; 
		games.put(gameNumber, new Game(type, numberOfPlayers));
		return gameNumber;
	}
	
	public void destroyGame(int gameNumber) {
		games.remove(gameNumber);
	}
}
