package edu.uci.ics.BoardGameServer.Engine;

import java.util.Scanner;
import java.util.regex.Pattern;

import edu.uci.ics.BoardGameServer.Action.Action;
import edu.uci.ics.BoardGameServer.Board.Board;
import edu.uci.ics.BoardGameServer.Distribution.Distribution;

public class Engine {

	private Scanner scanner = new Scanner(System.in);
	private String inputString;
	private Action action;
	private Board board;
	private Distribution distribution;
	private TalkDistribution talkDistribution;
	private Games games;
	private Thread threadGames;

	public void run() {
		startup();
		pause();
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

	public void pause() {
		getInputString("Please type anything and enter to shutdown: ",
				Pattern.compile(".*"));
	}

	private void getInputString(String message, Pattern pattern) {
		while (true) {
			System.out.print(message);
			while (!scanner.hasNext()) {
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					// pass
				}
			}
			try {
				inputString = scanner.next(pattern);
			} catch (Exception e) {
				scanner.next();
				continue;
			}
			if (!inputString.isEmpty()) {
				System.out.println();
				break;
			}
		}
	}
}
