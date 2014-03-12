package edu.uci.ics.BoardGameClient.Engine;

import edu.uci.ics.BoardGameClient.Distribution.Distribution;

public class Engine {

	private Distribution distribution;
	private TalkDistribution talkDistribution;
	private Game game;
	private TalkGame talkGame;

	public void run() {
		startup();
	}

	private void startup() {
		distribution = new Distribution();
		talkDistribution = new TalkDistribution();
		game = new Game();
		talkGame = new TalkGame();

		distribution.setTalkDistribution(talkDistribution);
	}

}
