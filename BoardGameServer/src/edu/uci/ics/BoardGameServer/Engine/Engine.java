package edu.uci.ics.BoardGameServer.Engine;

import edu.uci.ics.BoardGameServer.Action.Action;
import edu.uci.ics.BoardGameServer.Board.Board;
import edu.uci.ics.BoardGameServer.Distribution.Distribution;

public class Engine {

	private Action action;
	private Board board;
	private Distribution distribution;
	private TalkDistribution linkDistribution;
	private Games games;

	public void run() {
		startup();
	}

	private void startup() {
		action = new Action();
		board = new Board();
		distribution = new Distribution();
		linkDistribution = new TalkDistribution();
		games = new Games();

		action.setBoard(board);
		board.setAction(action);
		linkDistribution.setGames(games);
		distribution.setLinkDistribution(linkDistribution);
	}

}
