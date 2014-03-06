package edu.uci.ics.BoardGameServer.Engine;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class TalkDistribution {

	private Games games;
	private Queue<String> inputQueue = new ArrayBlockingQueue<String>(32);

	public void setGames(Games games) {
		this.games = games;
	}

	public int createGame(String type, int numberOfPlayers) {
		return games.createGame(type, numberOfPlayers);
	}

}
