package edu.uci.ics.BoardGameClient.Distribution;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;

public class Client implements Runnable {

	private final static int PORT = 54321;
	private volatile boolean stopRunning = false;
	private Distribution distribution;
	private Socket socket = null;
	private PrintWriter printWriter;
	private BufferedReader bufferedReader;
	private long lastMessageTime = System.currentTimeMillis();

	public void setDistribution(Distribution distribution) {
		this.distribution = distribution;
	}

	public void stop() {
		stopRunning = true;
	}

	@Override
	public void run() {

		connectToServer();
		handleLogin();
		
		while (socket == null && !stopRunning) {
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				// pass
			}
			connectToServer();
		}

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
				if (bufferedReader.ready()) {
					message = bufferedReader.readLine();
					if (message != null) {
						distribution.messageFromServer(message);
					}
				} else {
					sleepAndPing();
					continue;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		disconnectFromServer();
	}

	private void connectToServer() {
		try {
			socket = new Socket("localhost", PORT);
		} catch (Exception e) {
			if (e.getMessage().equals("Connection refused: connect")) {
				return;
			}
			e.printStackTrace();
		}
	}
	
	private void handleLogin()
	{
		Boolean validUser = false;
		
		while(!validUser)
		{
			String username = JOptionPane.showInputDialog("Username");
			//if user cancels, it will quit the program for the client
			if(username == null){
				System.exit(1);
			}
			
			String password = JOptionPane.showInputDialog("Password");
			if(password == null){
				System.exit(1);
			}
			
			//validate user from file
			if(true){//username.equals("a")){
				validUser = true;
			}
			else{
				JOptionPane.showMessageDialog(null, "incorrect info");
			}
		}
	}

	private void disconnectFromServer() {
		try {
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void sleepAndPing() {
		// note: should do something better than sleep, but code is much more complicated
		try {
			Thread.sleep(300);
		} catch (Exception e) {
			// pass
		}
		if (lastMessageTime + 10000 < System.currentTimeMillis()) {
			messageToServer("ping");
		}
	}

	public void messageToServer(String message) {
		while (printWriter == null) {
			if (stopRunning) {
				return;
			}
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				// pass
			}
		}
		lastMessageTime = System.currentTimeMillis();
		printWriter.println(message);
		printWriter.flush();
	}

}
