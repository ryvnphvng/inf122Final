package edu.uci.ics.BoardGameServer.Engine;

import java.util.Scanner;
import java.util.regex.Pattern;

import edu.uci.ics.BoardGameServer.Distribution.Distribution;

public class Engine {

	private Scanner scanner = new Scanner(System.in);
	private String inputString;
	private Distribution distribution;
	private Thread threadDistribution;
	private TalkDistribution talkDistribution;
	private Games games;
	private Thread threadGames;

	public void run() {
		startup();
		pause();
		shutdown();
	}

	private void startup() {
		distribution = new Distribution();
		talkDistribution = new TalkDistribution();
		games = new Games();

		talkDistribution.setGames(games);
		distribution.setTalkDistribution(talkDistribution);
		games.setTalkDistribution(talkDistribution);

		threadGames = new Thread(games);
		threadGames.start();

		threadDistribution = new Thread(distribution);
		threadDistribution.start();
	}

	private void shutdown() {
		games.stop();
		distribution.stop();
		threadGames.interrupt();
		threadDistribution.interrupt();
	}

	private void pause() {
		getInputString("Please type anything and enter to shutdown: ", Pattern.compile(".*"));
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
