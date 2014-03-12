import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	ServerSocket server;
	Socket client;
	Socket client2;
	BufferedReader in;
	BufferedReader in2;
	PrintWriter out;
	PrintWriter out2;
	String line;

public void listenSocket(){

  try{
    server = new ServerSocket(65529);
  } catch (IOException e) {
    System.out.println("Could not listen on port 4444");
    System.exit(-1);
  }
  
  while(true){
    ClientWorker w;
    try{
    	
    	client = server.accept();
    	out = new PrintWriter(client.getOutputStream(), true);
    	in = new BufferedReader(new InputStreamReader(client.getInputStream()));
    	System.out.println("Player One Connected at " + client.isConnected());
    	client2 = server.accept();
    	out2 = new PrintWriter(client2.getOutputStream(), true);
    	in2 = new BufferedReader(new InputStreamReader(client2.getInputStream()));
    	System.out.println("Player Two Connected at " + client2.isConnected());
    	while(true){
    		String line = in.readLine();
    		//do something with the input
    		//receive output from TalkDistribution
    		out.println(line);
    		out2.println(line);
    		
    		line = in2.readLine();
    		//do something with the input
    		//receive output from TalkDistribution
    		out.println(line);
    		out2.println(line);
    		
    	}
    	
//server.accept returns a client connection
      //w = new ClientWorker(server.accept());
     // Thread t = new Thread(w);
    // t.start();
    } catch (IOException e) {
      System.out.println("Accept failed: 4444");
      System.exit(-1);
    }
  }
}

	public static void main(String[] args) {
		
		Server server = new Server();
		server.listenSocket();
		
	}
}