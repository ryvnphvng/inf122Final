package edu.uci.ics.BoardGameServer.Action;

import edu.uci.ics.BoardGameServer.Board.Board;
import edu.uci.ics.BoardGameServer.Board.GameObject;

public abstract class Instantiator {
	
	public abstract Board instantiateNewGame(int numOfPlayers, String gameType);
	
	public abstract GameObject instantiateGameObject(String gameType, int playerNum, int nextObjID);
	
}
