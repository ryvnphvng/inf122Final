package edu.uci.ics.BoardGameServer.Distribution;

import edu.uci.ics.BoardGameServer.Common.Definitions;
import edu.uci.ics.BoardGameServer.Common.Message;
import edu.uci.ics.BoardGameServer.Engine.TalkDistribution;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Distribution implements Runnable {

	private TalkDistribution talkDistribution;
	private List<Integer> gameIds = new ArrayList<Integer>();
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
			messageToClient(talkDistribution.getOutputQueue());
		}
	}

	private void createGame() {
		int gameId = talkDistribution.createGame(Definitions.GAMETYPETICTACTOE,
				2);
		gameIds.add(gameId);
	}

	private void destroyGame() {
		talkDistribution.destroyGame(2);
	}

	private void messageFromClient(Message message) {
		message.gameId = 2;
		message.playerNumber = 0;
		message.message = "blah";
		talkDistribution.messageFromClient(message);
	}

	private void messageToClient(Message message) {
		if (message == null) {
			return;
		}
		// implement sending the message to the correct client
	}

}
