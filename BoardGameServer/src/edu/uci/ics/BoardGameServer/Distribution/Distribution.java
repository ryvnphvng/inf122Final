package edu.uci.ics.BoardGameServer.Distribution;

import edu.uci.ics.BoardGameServer.Common.Definitions;
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

	private void createGame() {
		int gameId = talkDistribution.createGame(Definitions.GAMETYPETICTACTOE, 2);
		gameIds.add(gameId);
	}
	
	private void destroyGame() {
		talkDistribution.destroyGame(2);
	}
	
	private void sendMessageToGame(Message message) {
		message.gameId = 2;
		message.playerNumber = 0;
		message.message = "blah";
		talkDistribution.sendMessageToGame(message);
	}

}
