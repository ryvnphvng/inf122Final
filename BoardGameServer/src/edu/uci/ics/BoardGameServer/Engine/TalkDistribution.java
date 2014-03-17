package edu.uci.ics.BoardGameServer.Engine;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import edu.uci.ics.BoardGameServer.Common.Message;

public class TalkDistribution {

	private Games games;
	private Object synchronizeInputQueue = new Object();
	private Queue<Message> inputQueue = new ArrayBlockingQueue<Message>(32);
	private Object synchronizeOutputQueue = new Object();
	private Queue<Message> outputQueue = new ArrayBlockingQueue<Message>(32);

	public void setGames(Games games) {
		this.games = games;
	}

	public void setInputQueue(Queue<Message> inputQueue) {
		this.inputQueue = inputQueue;
	}

	public int createGame(int gameType, int numberOfPlayers) {
		return games.createGame(gameType, numberOfPlayers);
	}

	public void destroyGame(int gameNumber) {
		games.destroyGame(gameNumber);
	}

	public void addMessageToInputQueue(Message message) {
		synchronized (synchronizeInputQueue) {
			inputQueue.add(message);
			synchronizeInputQueue.notifyAll();
		}
	}

	public Message getMessageFromInputQueue() {
		return inputQueue.poll();
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

	public void messageFromClient(Message message) {
		synchronized (synchronizeInputQueue) {
			inputQueue.add(message);
			synchronizeInputQueue.notifyAll();
		}
	}

	public Message getInputQueue() {
		return inputQueue.poll();
	}

	public void messageToClient(Message message) {
		synchronized (synchronizeOutputQueue) {
			outputQueue.add(message);
			synchronizeOutputQueue.notifyAll();
		}
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

	public Message getOutputQueue() {
		return outputQueue.poll();
	}

}
