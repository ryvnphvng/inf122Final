package edu.uci.ics.BoardGameClient.GUI;

import java.util.Scanner;
import java.util.regex.Pattern;

import edu.uci.ics.BoardGameClient.Action.Action;

public class GUI {

	private Action action;
	private Scanner scanner = new Scanner(System.in);
	private String inputString;

	public void setAction(Action action) {
		this.action = action;
	}

	public void run() {
		// pause and getInputString is temporary code
		pause();
	}

	// temporary method
	private void pause() {
		getInputString("Please type anything and enter to shutdown: ",
				Pattern.compile(".*"));
	}

	// temporary method
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
