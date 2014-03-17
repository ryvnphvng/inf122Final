package edu.uci.ics.BoardGameClient.Engine;

import edu.uci.ics.BoardGameClient.Common.Message;
import edu.uci.ics.BoardGameClient.Distribution.Distribution;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class TalkDistribution {

	private Distribution distribution;
	private Object synchronizeInputQueue = new Object();
	private Queue<Message> inputQueue = new ArrayBlockingQueue<Message>(32);
	private Object synchronizeOutputQueue = new Object();
	private Queue<Message> outputQueue = new ArrayBlockingQueue<Message>(32);

	public void setDistribution(Distribution distribution) {
		this.distribution = distribution;
	}

	public void setInputQueue(Queue<Message> inputQueue) {
		this.inputQueue = inputQueue;
	}

	public void createGame(int gameType) {
		distribution.createGame(gameType);
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

	public void messageFromServer(Message message) {
		synchronized (synchronizeInputQueue) {
			inputQueue.add(message);
			synchronizeInputQueue.notifyAll();
		}
	}

	public Message getInputQueue() {
		return inputQueue.poll();
	}

	public void messageToServer(Message message) {
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
