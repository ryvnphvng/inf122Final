package edu.uci.ics.BoardGameServer.Engine;

import java.util.Map;
import java.util.TreeMap;

public class Games {

	private Map<Integer, Game> games = new TreeMap<Integer, Game>();
	private int nextGameNumber = 0;
	
	public int createGame(String type, int numberOfPlayers) {
		int gameNumber = nextGameNumber++; 
		games.put(gameNumber, new Game(type, numberOfPlayers));
		return gameNumber;
	}
	
	public void destroyGame(int gameNumber) {
		games.remove(gameNumber);
	}

}
