package edu.uci.ics.BoardGameServer.Engine;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import edu.uci.ics.BoardGameServer.Common.Message;

public class TalkDistribution {

	private Games games;
	private Object synchronizeInputQueue = new Object();
	private Queue<Message> inputQueue;
	private Object synchronizeOutputQueue = new Object();
	private Queue<Message> outputQueue = new ArrayBlockingQueue<Message>(32);

	public void setGames(Games games) {
		this.games = games;
	}

	public void setInputQueue(Queue<Message> inputQueue) {
		this.inputQueue = inputQueue;
	}

	public int createGame(String type, int numberOfPlayers) {
		return games.createGame(type, numberOfPlayers);
	}

	public void waitForInputQueue() {
		synchronized (synchronizeInputQueue) {
			if (inputQueue.isEmpty()) {
				try {
					synchronizeInputQueue.wait(60000);
				} catch (Exception e) {
					// pass
				}
			}
		}
	}

	public void sendMessageToGame(Message message) {
		synchronized (synchronizeInputQueue) {
			inputQueue.add(message);
		}
		synchronizeInputQueue.notifyAll();
	}

	public void waitForOutputQueue() {
		synchronized (synchronizeOutputQueue) {
			if (outputQueue.isEmpty()) {
				try {
					synchronizeOutputQueue.wait(60000);
				} catch (Exception e) {
					// pass
				}
			}
		}
	}

	public void sendMessageToClient(Message message) {
		synchronized (synchronizeOutputQueue) {
			outputQueue.add(message);
		}
		synchronizeOutputQueue.notifyAll();
	}

}
