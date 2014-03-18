package edu.uci.ics.BoardGameServer.Distribution;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
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
			Boolean recivedMessage = false;
			String message;

			for (ClientConnection clientConnection : clientConnections.values()) {
				message = null;
				try {
					if (!clientConnection.bufferedReader.ready()) {
						continue;
					}
					message = clientConnection.bufferedReader.readLine();
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (message == null) {
					continue;
				}
				recivedMessage = true;
				processMessage(clientConnection, message);
			}
			
			if (!recivedMessage) {
				checkConnectionsIfAlive();
				// note: should do something better than sleep, but code is much more complicated
				try {
					Thread.sleep(300);
				} catch (Exception e) {
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

	private void processMessage(ClientConnection clientConnection, String message) {
		clientConnection.lastMessagetime = System.currentTimeMillis();
		if (message.equals("ping")) {
			return;
		}
		if (message.equals("disconnect")) {
			clientConnection.alive = false;
			return;
		}
		if (message.startsWith("createGame ")) {
			distribution.createGame(clientConnection.connectionId, message);
			return;
		}
		distribution.messageFromClient(clientConnection.connectionId, message);
	}

	private void checkConnectionsIfAlive() {
		List<Integer> clientConnectionsToRemove = new ArrayList<Integer>();
		List<Integer> gamesToDestroy = new ArrayList<Integer>();

		for (int key : clientConnections.keySet()) {
			if (clientConnections.get(key).lastMessagetime + 15000 < System.currentTimeMillis()) {
				clientConnections.get(key).alive = false;
			}
			if (!clientConnections.get(key).alive) {
				try {
					clientConnections.get(key).socket.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				clientConnections.get(key).socket = null;
				clientConnections.get(key).printWriter = null;
				clientConnections.get(key).bufferedReader = null;
				gamesToDestroy.add(clientConnections.get(key).connectionId);
				clientConnectionsToRemove.add(key);
			}
		}

		for (int connectionId : gamesToDestroy) {
			distribution.destroyGame(connectionId);
		}

		for (int key : clientConnectionsToRemove) {
			distribution.removeConnectionId(clientConnections.get(key).connectionId);
			clientConnections.remove(key);
		}
	}

	public boolean isConnectionAlive(int connectionId) {
		ClientConnection clientConnection = clientConnections.get(connectionId);
		if (clientConnection == null) {
			return false;
		}
		return clientConnection.alive;
	}

	public void messageToClient(int connectionId, String message) {
		ClientConnection clientConnection = clientConnections.get(connectionId);
		if (clientConnection == null) {
			return;
		}
		if (clientConnection.printWriter == null) {
			return;
		}
		clientConnection.printWriter.println(message);
	}

}
