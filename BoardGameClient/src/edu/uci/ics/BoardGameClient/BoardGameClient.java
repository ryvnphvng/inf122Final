package edu.uci.ics.BoardGameClient;

import edu.uci.ics.BoardGameClient.Engine.Engine;

public class BoardGameClient {

	public static void main(String[] args) {
		BoardGameClient boardGameClient = new BoardGameClient();
		boardGameClient.run();
	}

	private void run() {
		Engine engine = new Engine();
		engine.run();
	}

} 
