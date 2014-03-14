package edu.uci.ics.BoardGameServer.Board;


public abstract class GameObject {
	private int row;
	private int col;
	private int objID;
	private String objType; 
	private int playerID;
	
	public GameObject(int row, int col, int objID, String objType, int playerID){
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
	
	public String getObjectType(){
		return this.objType;
	}
	 
	public int getOwner(){
		return playerID;
	}
}
