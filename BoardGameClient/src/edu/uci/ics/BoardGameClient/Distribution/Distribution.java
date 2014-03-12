package edu.uci.ics.BoardGameClient.Distribution;

import edu.uci.ics.BoardGameClient.Common.Message;
import edu.uci.ics.BoardGameClient.Engine.TalkDistribution;

public class Distribution implements Runnable {

	private TalkDistribution talkDistribution;
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
			talkDistribution.waitForOutputQueue();
			messageToServer(talkDistribution.getOutputQueue());
		}
	}

	private void createGame() {
		// implement sending the message to server
	}

	private void destroyGame() {
		// implement sending the message to server
	}

	private void messageFromServer(Message message) {
		talkDistribution.messageFromServer(message);
	}

	private void messageToServer(Message message) {
		if (message == null) {
			return;
		}
		// implement sending the message to the server
	}

}
