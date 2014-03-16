package edu.uci.ics.BoardGameClient.Distribution;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable {

	private final static int PORT = 54321;
	private volatile boolean stopRunning = false;
	private Distribution distribution;
	private Socket socket = null;
	private PrintWriter printWriter;
	private BufferedReader bufferedReader;
	

	public void setDistribution(Distribution distribution) {
		this.distribution = distribution;
	}

	public void stop() {
		stopRunning = true;
	}

	@Override
	public void run() {
		connectToServer();
		if (socket == null) {
			return;
		}

		try {
			printWriter = new PrintWriter(socket.getOutputStream(), true);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		try {
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		while (!stopRunning) {
			String message = null;

			try {
				message = bufferedReader.readLine();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (message != null) {
				distribution.messageFromServer(message);
			}
		}

		disconnectFromServer();
	}

	private void connectToServer() {
		try {
			socket = new Socket("localhost", PORT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void disconnectFromServer() {
		try {
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void messageToServer(String message) {
		printWriter.println(message);
		printWriter.flush();
	}

}
