package edu.uci.ics.BoardGameClient.Board;

public class GameObject {
	private int row;
	private int col;
	private int objID;
	private int objType; 
	private int playerID;
	
	public GameObject(int row, int col, int objID, int objType, int playerID){
		this.row = row;
		this.col = col;
		this.objID = objID;
		this.objType = objType;
		this.playerID = playerID;
	}
	
	public int getRow(){
		return this.row;
	}
	
	public void setRow(int newRow){
		this.row = newRow;
	}
	
	public int getCol(){
		return this.col;
	}
	
	public void setCol(int newCol){
		this.col = newCol;
	}
	
	public int getObjID(){
		return this.objID;
	}
	
	public int getObjectType(){
		return this.objType;
	}
	 
	public int getOwner(){
		return playerID;
	}
}
