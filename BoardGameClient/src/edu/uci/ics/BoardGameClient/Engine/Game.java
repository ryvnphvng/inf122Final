package edu.uci.ics.BoardGameClient.Engine;

import edu.uci.ics.BoardGameClient.Action.Action;
import edu.uci.ics.BoardGameClient.Common.Message;

public class Game implements Runnable {

	private Engine engine;
	private Action action = new Action();
	private TalkDistribution talkDistribution;
	private volatile boolean stopRunning = false;
	
	public Game () {
		action.setGame(this);
	}
	
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
		action.messageFromServer(message);
	}

	public void messageToServer(Message message) {
		talkDistribution.messageToServer(message);
	}

}
