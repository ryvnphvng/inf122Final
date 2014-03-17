package edu.uci.ics.BoardGameServer.Distribution;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientConnection {
	public boolean alive = true;
	public long lastMessagetime = System.currentTimeMillis();
	public int connectionId;
	public Socket socket;
	public PrintWriter printWriter;
	public BufferedReader bufferedReader;
}
