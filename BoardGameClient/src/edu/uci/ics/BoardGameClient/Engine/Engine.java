package edu.uci.ics.BoardGameClient.Engine;

import edu.uci.ics.BoardGameClient.Distribution.Distribution;

public class Engine {

	private Distribution distribution;
	private Thread threadDistribution;
	private TalkDistribution talkDistribution;
	private Game game;
	private Thread threadGame;

	public void run() {
		startup();
	}

	private void startup() {
		distribution = new Distribution();
		talkDistribution = new TalkDistribution();
		game = new Game();

		distribution.setTalkDistribution(talkDistribution);
		talkDistribution.setDistribution(distribution);
		game.setEngine(this);
		game.setTalkDistribution(talkDistribution);

		threadDistribution = new Thread(distribution);
		threadDistribution.start();
		
		threadGame = new Thread(game);
		threadGame.start();
	}
	
	public void shutdown() {
		distribution.stop();
		game.stop();
		threadDistribution.interrupt();
		threadGame.interrupt();
	}


}
