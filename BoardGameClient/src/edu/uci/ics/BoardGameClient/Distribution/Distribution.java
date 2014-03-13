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

	public void createGame(int gameType) {
		System.out.println("createGame");
		// implement sending to server
	}

	public void disconnect() {
		System.out.println("disconnect");
		// implement sending to server
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
