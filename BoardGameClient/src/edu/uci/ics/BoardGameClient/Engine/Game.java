package edu.uci.ics.BoardGameClient.Engine;

import edu.uci.ics.BoardGameClient.Action.Action;
import edu.uci.ics.BoardGameClient.Common.Message;

public class Game implements Runnable {

	private Engine engine;
	private Action action;
	private TalkDistribution talkDistribution;
	private volatile boolean stopRunning = false;
	
	public void setEngine(Engine engine) {
		this.engine = engine;
	}

	public void setTalkDistribution(TalkDistribution talkDistribution) {
		this.talkDistribution = talkDistribution;
	}

	public void stop() {
		stopRunning = true;
	}

	@Override
	public void run() {
		action = new Action(this);
		
		while (!stopRunning) {
			talkDistribution.waitForInputQueue();
			messageFromServer(talkDistribution.getInputQueue());
		}
	}

	public void createGame(int gameType) {
		talkDistribution.createGame(gameType);
	}

	public void shutdown() {
		engine.shutdown();
	}

	public void messageFromServer(Message message) {
		if (message == null) {
			return;
		}
		action.messageFromServer(message);
	}

	public void messageToServer(Message message) {
		talkDistribution.messageToServer(message);
	}

}
