package edu.uci.ics.BoardGameServer;

import edu.uci.ics.BoardGameServer.Engine.Engine;

public class BoardGameServer {

	public static void main(String[] args) {
		BoardGameServer boardGameServer = new BoardGameServer();
		boardGameServer.run();
	}

	private void run() {
		Engine engine = new Engine();
		engine.run();
	}

}
