import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class ClientProgram {
	Socket socket;
	BufferedReader in;
	PrintWriter out;
	
	public void listenSocket(){
		//Create socket connection
		   try{
		    socket = new Socket("169.234.30.248", 65529);
		    out = new PrintWriter(socket.getOutputStream(), 
		                 true);
		     in = new BufferedReader(new InputStreamReader(
		                socket.getInputStream()));
		     
		    out.println("Hi, I'm Steven");
		    System.out.println(in.readLine());
		  
		     
		   } catch (UnknownHostException e) {
		     System.out.println("Unknown host");
		     System.exit(1);
		   } catch  (IOException e) {
		     System.out.println(e.getMessage());
		     System.exit(1);
		   }
		}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClientProgram c = new ClientProgram();
		
		c.listenSocket();
}
}



