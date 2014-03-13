package edu.uci.ics.BoardGameServer.Action;

import edu.uci.ics.BoardGameServer.Board.Board;
import edu.uci.ics.BoardGameServer.Common.Message;
import edu.uci.ics.BoardGameServer.Engine.Game;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import javax.swing.text.html.HTMLDocument.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



public class Action {

	private Game game;
	private Board board;
	private int gameType;
	private int numberOfPlayers;

	public void setGame(Game game) {
		this.game = game;
	}

	public void setGameType(int gameType) {
		this.gameType = gameType;
	}

	public void setNumberOfPlayers(int numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
	}

	public static void messageFromClient(String message) {
		Object obj=JSONValue.parse(message);
		JSONArray array=(JSONArray)obj;		
		JSONObject gameMessage=(JSONObject)array.get(0);
		  //testing; listing out the gameMessages 
		  System.out.println(obj.toString());
		  System.out.println("======Game Messages==========");
		  System.out.println("MessageType :" + gameMessage.get("MessageType")); 
		  System.out.println("GameID :" + gameMessage.get("GameID"));
		  System.out.println("PlayerID :" + gameMessage.get("PlayerID"));
		  System.out.println("ObjectID :" + gameMessage.get("ObjectID"));
		  System.out.println("Row :" + gameMessage.get("Row"));
		  System.out.println("Col :" + gameMessage.get("Col"));
		  System.out.println("Message :" + gameMessage.get("Message"));
		  
	}
	
	public static String messageToClient() {
		
		String message = "[{\"MessageType\":\"startGame\" , \"GameID\":\"25\", \"PlayerID\":\"25\", \"ObjectID\":\"25\",\"Row\":\"0\",\"Col\":\"1\",\"Message\":\"Place\" }]";
		return message;
	}

	//for testing purposes only!
	public static void main(String [ ] args)
	{
		String Message="[{\"MessageType\":\"startGame\" , \"GameID\":\"25\", \"PlayerID\":\"25\", \"ObjectID\":\"25\",\"Row\":\"0\",\"Col\":\"1\",\"Message\":\"Place\" }]";
		messageFromClient(Message);
		
	}

	
	

}
