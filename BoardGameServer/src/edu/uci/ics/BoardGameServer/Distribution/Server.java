package edu.uci.ics.BoardGameServer.Distribution;

import java.net.ServerSocket;
import java.util.Map;
import java.util.TreeMap;


public class Server implements Runnable {

	private final static int PORT = 54321;
	private volatile boolean stopRunning = false;
	private Distribution distribution;
	private ServerSocket serverSocket = null;
	private Map<Integer, ClientConnection> clientConnections = new TreeMap<Integer, ClientConnection>();
	private ServerAccept serverAccept;
	private Thread threadServerAccept;

	public void setDistribution(Distribution distribution) {
		this.distribution = distribution;
	}

	public void stop() {
		stopRunning = true;
	}

	@Override
	public void run() {

		createServerSocket();
		if (serverSocket == null) {
			return;
		}

		serverAccept = new ServerAccept(serverSocket, clientConnections);
		threadServerAccept = new Thread(serverAccept);
		threadServerAccept.start();

		while (!stopRunning) {
			String message = null;

			for (ClientConnection clientConnection : clientConnections.values()) {
				try {
					if (!clientConnection.bufferedReader.ready()) {
						continue;
					}
					message = clientConnection.bufferedReader.readLine();
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (message.equals("disconnect")) {
					clientConnection.alive = false;
					continue;
				}
				if (message.startsWith("createGame ")) {
					distribution.createGame(clientConnection.connectionId, message);
					continue;
				}
				distribution.messageFromClient(message);
			}

			checkConnectionsIfAlive();

			if (message == null) {
				// TODO: something better then sleep
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// pass
				}
			}
		}

		serverAccept.stop();
		threadServerAccept.interrupt();

		destroyServerSocket();
	}

	private void createServerSocket() {
		try {
			serverSocket = new ServerSocket(PORT);
			serverSocket.setSoTimeout(20000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void destroyServerSocket() {
		try {
			serverSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void checkConnectionsIfAlive() {
		for (int key : clientConnections.keySet()) {
			if (!clientConnections.get(key).alive) {
				try {
					clientConnections.get(key).socket.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				clientConnections.remove(key);
			}
		}
	}

}
