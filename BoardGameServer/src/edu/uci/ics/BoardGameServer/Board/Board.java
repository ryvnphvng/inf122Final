package edu.uci.ics.BoardGameServer.Board;

import java.util.Map;
import java.util.TreeMap;


public class Board {

	private Tile[][] board; 
	private int width;
	private int height;
	private String gameType;
	private Map<Integer, GameObject> gameObjectMap;
	private Map<Integer, Player> playerMap;
	
	public Board(int height, int width, String gameType)
	{
		for(int i=0;i<height;i++)
		{
			for(int j=0; j<width; j++)
			{
				board[i][j] = new Tile();
			}
		}
		
		gameObjectMap = new TreeMap<Integer, GameObject>();
		playerMap = new TreeMap<Integer, Player>();
	}
	
	public int getWidth(){
		return this.width;
	}
	
	public int getHeight(){
		return this.height;
	}
	
	public String getGameType(){
		return this.gameType;
	}
	
	public Tile getTile(int row, int column){
		return board[row][column]; 
	}
	
	public int getPlayerScore(int playerID){
		return playerMap.get(playerID).getScore();
	}
	
	public void modifyScore(int playerID, int amount){
		playerMap.get(playerID).modifyScore(amount);
	}
	
	public void addToBoard(GameObject g, int row, int col){
		board[row][col].addToTile(g);
		
		gameObjectMap.put(g.getObjID(), g);
	}
	
	public void removeFromBoard(int objID){
		GameObject toRemove = gameObjectMap.get(objID);
		
		board[toRemove.getRow()][toRemove.getCol()].removeFromTile(toRemove);
		
		gameObjectMap.remove(toRemove.getObjID());
	}
	
	public void move(int objID, int newRow, int newCol){
		GameObject toMove = gameObjectMap.get(objID);

		board[toMove.getRow()][toMove.getCol()].removeFromTile(toMove);
		
		board[newRow][newCol].addToTile(toMove);
		
		toMove.setRow(newRow);
		toMove.setCol(newCol);
	}
	
	public void swap(int objID1, int objID2){
		GameObject obj1 = gameObjectMap.get(objID1);
		GameObject obj2 = gameObjectMap.get(objID2);
		
		int obj1Row = obj1.getRow();
		int obj1Col = obj1.getCol();
		
		int obj2Row = obj2.getRow();
		int obj2Col = obj2.getCol();
		
		board[obj1.getRow()][obj1.getCol()].removeFromTile(obj1);
		board[obj2.getRow()][obj2.getCol()].removeFromTile(obj2);
		
		board[obj2.getRow()][obj2.getCol()].addToTile(obj1);
		board[obj1.getRow()][obj1.getCol()].addToTile(obj2);
		
		obj1.setRow(obj2Row);
		obj1.setCol(obj2Col);
		
		obj2.setRow(obj1Row);
		obj2.setCol(obj1Col);
		
	}

}
