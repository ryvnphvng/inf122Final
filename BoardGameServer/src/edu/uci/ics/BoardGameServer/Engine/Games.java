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
		if (nextGameId > 999999999) {
			nextGameId = 0;
		}
		int gameNumber = nextGameId++;
		while (games.containsKey(gameNumber)) {
			gameNumber = nextGameId++;
			if (nextGameId > 999999999) {
				nextGameId = 0;
			}
		}
		games.put(gameNumber, new Game(gameNumber, gameType, numberOfPlayers));
		return gameNumber;
	}

	public void destroyGame(int gameNumber) {
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

}
