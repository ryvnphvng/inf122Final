package edu.uci.ics.BoardGameClient.Board;

import java.util.ArrayList;

import edu.uci.ics.BoardGameClient.Board.GameObject;

public class Tile {
	private ArrayList<GameObject> gameObjects;
	private boolean isSelected;
	
	public Tile()
	{
		gameObjects = new ArrayList<GameObject>();
		isSelected = false;
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
	
	public boolean isSelected()
	{
		return this.isSelected;
	}
	
	public void selectPanel()
	{
		isSelected = true;
	}
	
	public void deselectPanel()
	{
		isSelected = false;
	}
}
