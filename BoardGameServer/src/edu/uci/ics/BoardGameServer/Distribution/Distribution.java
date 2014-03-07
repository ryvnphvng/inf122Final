package edu.uci.ics.BoardGameServer.Distribution;

import edu.uci.ics.BoardGameServer.Common.Message;
import edu.uci.ics.BoardGameServer.Engine.TalkDistribution;

import java.util.ArrayList;
import java.util.List;

public class Distribution {

	private TalkDistribution talkDistribution;
	private List<Integer> gameIds = new ArrayList<Integer>();

	public void setTalkDistribution(TalkDistribution talkDistribution) {
		this.talkDistribution = talkDistribution;
	}

	@SuppressWarnings("unused")
	private void createGame() {
		int gameId = talkDistribution.createGame("temp", 2);
		gameIds.add(gameId);
	}
	
	@SuppressWarnings("unused")
	private void sendMessageToGame(Message message) {
		message.gameId = 2;
		message.playerNumber = 0;
		message.message = "blah";
		talkDistribution.sendMessageToGame(message);
	}

}
