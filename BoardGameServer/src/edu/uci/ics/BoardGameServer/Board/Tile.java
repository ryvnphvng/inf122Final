package edu.uci.ics.BoardGameServer.Board;

import java.util.ArrayList;

public class Tile {
	private ArrayList<GameObject> gameObjects;
	private boolean isSelected;
	
	public Tile()
	{
		isSelected = false;
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
