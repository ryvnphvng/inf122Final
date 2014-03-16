package edu.uci.ics.BoardGameClient.Board;

import java.util.ArrayList;

import edu.uci.ics.BoardGameClient.Board.GameObject;

public class Tile {
	private ArrayList<GameObject> gameObjects;
	
	public Tile()
	{
		gameObjects = new ArrayList<GameObject>();
	}

	
	public void addToTile(GameObject go){
		gameObjects.add(go);
	}
	
	public void removeFromTile(GameObject go){
		gameObjects.remove(go);
	}
	
	public ArrayList<GameObject> getGameObjects(){
		return gameObjects;
	}
}
