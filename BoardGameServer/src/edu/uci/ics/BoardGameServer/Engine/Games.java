package edu.uci.ics.BoardGameServer.Engine;

import java.util.Map;
import java.util.TreeMap;
import edu.uci.ics.BoardGameServer.Common.Message;

public class Games implements Runnable {

	private TalkDistribution talkDistribution;
	private Map<Integer, Game> games = new TreeMap<Integer, Game>();
	private int nextGameId = 0;
	private volatile boolean stopRunning = false;

	public void setTalkDistribution(TalkDistribution talkDistribution) {
		this.talkDistribution = talkDistribution;
	}

	public void stop() {
		stopRunning = true;
	}

	@Override
	public void run() {
		while (!stopRunning) {
			talkDistribution.waitForInputQueue();
			messageFromClient(talkDistribution.getMessageFromInputQueue());
		}
	}

	public int createGame(int gameType, int numberOfPlayers) {
		int gameNumber = getNextGameId();
		while (games.containsKey(gameNumber)) {
			gameNumber = getNextGameId();
		}
		Game game = new Game(gameNumber, gameType, numberOfPlayers);
		game.setGames(this);
		games.put(gameNumber, game);
		return gameNumber;
	}
	
	public void gameCreated(int gameNumber) {
		Game game = games.get(gameNumber);
		if (game == null) {
			System.err.println("Invalid gameNumber " + gameNumber);
			return;
		}
		
		game.gameCreated();
	}

	public void destroyGame(int gameNumber) {
		Game game = games.get(gameNumber);
		if (game == null) {
			return;
		}
		game.destroyGame();
		games.remove(gameNumber);
	}

	private void messageFromClient(Message message) {
		if (message == null) {
			return;
		}
		Game game = games.get(message.gameId);
		if (game == null) {
			return;
		}
		game.messageFromClient(message);
	}

	public void messageToClient(Message message) {
		talkDistribution.messageToClient(message);
	}

	private int getNextGameId() {
		nextGameId++;
		if (nextGameId > 1000000000) {
			nextGameId = 1;
		}
		return nextGameId;
	}
}
