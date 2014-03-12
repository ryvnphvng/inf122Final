package edu.uci.ics.BoardGameServer.Engine;

import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;
import java.util.concurrent.ArrayBlockingQueue;

import edu.uci.ics.BoardGameServer.Common.Message;

public class Games implements Runnable {

	private TalkDistribution talkDistribution;
	private Queue<Message> inputQueue = new ArrayBlockingQueue<Message>(32);
	private Map<Integer, Game> games = new TreeMap<Integer, Game>();
	private int nextGameId = 0;
	private volatile boolean stopRunning = false;

	public void setTalkDistribution(TalkDistribution talkDistribution) {
		this.talkDistribution = talkDistribution;
		talkDistribution.setInputQueue(inputQueue);
	}

	public void stop() {
		stopRunning = true;
	}

	@Override
	public void run() {
		while (!stopRunning) {
			talkDistribution.waitForInputQueue();
			sendMessageToGame(inputQueue.poll());
		}
	}

	public int createGame(int gameType, int numberOfPlayers) {
		if (nextGameId > 999999999){
			nextGameId = 0;
		}
		int gameNumber = nextGameId++;
		while (games.containsKey(gameNumber)) {
			gameNumber = nextGameId++;
			if (nextGameId > 999999999){
				nextGameId = 0;
			}
		}
		games.put(gameNumber, new Game(gameNumber, gameType, numberOfPlayers));
		return gameNumber;
	}

	public void destroyGame(int gameNumber) {
		games.remove(gameNumber);
	}

	private void sendMessageToGame(Message message) {
		if (message == null) {
			return;
		}
		Game game = games.get(message.gameId);
		if (game == null) {
			return;
		}
		game.messageFromClient(message);
	}
	
	public void sendMessageToClient(Message message) {
		talkDistribution.sendMessageToClient(message);
	}

}
