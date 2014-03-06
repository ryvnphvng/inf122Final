package edu.uci.ics.BoardGameServer.Engine;

import edu.uci.ics.BoardGameServer.Action.Action;
import edu.uci.ics.BoardGameServer.Board.Board;
import edu.uci.ics.BoardGameServer.Distribution.Distribution;

public class Engine {

	private Action action;
	private Board board;
	private Distribution distribution;
	private TalkDistribution talkDistribution;
	private Games games;
	private Thread threadGames;

	public void run() {
		startup();
		try {
			Thread.sleep(5000);
		} catch (Exception e) {
			// pass
		}
		shutdown();
	}

	private void startup() {
		action = new Action();
		board = new Board();
		distribution = new Distribution();
		talkDistribution = new TalkDistribution();
		games = new Games();

		action.setBoard(board);
		board.setAction(action);
		talkDistribution.setGames(games);
		distribution.setTalkDistribution(talkDistribution);
		games.setTalkDistribution(talkDistribution);

		threadGames = new Thread(games);
		threadGames.start();
	}

	public void shutdown() {
		games.stop();
		threadGames.interrupt();
	}
}
