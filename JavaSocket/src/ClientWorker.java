import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JTextArea;


public class ClientWorker implements Runnable {
	private Socket client;

	//Constructor
	ClientWorker(Socket client) {
		this.client = client;
	}

	public void run(){
		String line;
		BufferedReader in = null;
		PrintWriter out = null;
		try{
			in = new BufferedReader(new 
					InputStreamReader(client.getInputStream()));
			out = new 
					PrintWriter(client.getOutputStream(), true);
		} catch (IOException e) {
			System.out.println("in or out failed");
			System.exit(-1);
		}

		while(true){
			try{
				if(!client.isClosed()){
					line = in.readLine();
					
					if(!(line == null))
					{
						System.out.println(line);
						
						out.println(line);
					}
				}
				//Send data back to client
				//Append data to text area
			}catch (IOException e) {
				System.out.println(e.getMessage());
				System.exit(-1);
			}

		}
	}
}
