package edu.uci.ics.BoardGameServer.Distribution;

import edu.uci.ics.BoardGameServer.Engine.TalkDistribution;

import java.util.ArrayList;
import java.util.List;

public class Distribution {

	private TalkDistribution linkDistribution;
	private List<Integer> gameIds = new ArrayList<Integer>();

	public void setLinkDistribution(TalkDistribution linkDistribution) {
		this.linkDistribution = linkDistribution;
	}

	private void createGame() {
		int gameId = linkDistribution.createGame("temp", 2);
		gameIds.add(gameId);
	}

}
