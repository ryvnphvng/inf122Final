package edu.uci.ics.BoardGameServer.Distribution;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class ServerAccept implements Runnable {

	private volatile boolean stopRunning = false;
	private ServerSocket serverSocket;
	private Map<Integer, ClientConnection> clientConnections;
	private int nextConnectionId = 0;

	ServerAccept(ServerSocket serverSocket, Map<Integer, ClientConnection> clientConnections) {
		this.serverSocket = serverSocket;
		this.clientConnections = clientConnections;
	}

	public void stop() {
		stopRunning = true;
	}

	@Override
	public void run() {
		while (!stopRunning) {
			socketAccept();
		}

	}

	private void socketAccept() {
		Socket socket = null;

		try {
			socket = null;
			socket = serverSocket.accept();
		} catch (Exception e) {
			if (stopRunning) {
				return;
			}
			if (e.getMessage().equals("Accept timed out")) {
				return;
			}
			e.printStackTrace();
		}

		if (socket != null) {
			ClientConnection clientConnection = new ClientConnection();
			clientConnection.socket = socket;
			try {
				clientConnection.printWriter = new PrintWriter(socket.getOutputStream(), true);
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				clientConnection.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			} catch (Exception e) {
				e.printStackTrace();
			}

			int connectionId = getNextConnectionId();
			while (clientConnections.containsKey(connectionId)) {
				connectionId = getNextConnectionId();
			}
			clientConnection.connectionId = connectionId;
			clientConnections.put(connectionId, clientConnection);
		}
	}

	private int getNextConnectionId() {
		nextConnectionId++;
		if (nextConnectionId > 1000000000) {
			nextConnectionId = 1;
		}
		return nextConnectionId;
	}

}
