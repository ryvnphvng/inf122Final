package edu.uci.ics.BoardGameServer.Action;

import org.json.simple.JSONObject;

import edu.uci.ics.BoardGameServer.Board.Board;
import edu.uci.ics.BoardGameServer.Common.Message;

public abstract class MoveValidator {
	private Board board;
	private int currentPlayerTurn;
	private int numberOfPlayers;
	
	public MoveValidator(Board board, int numberOfPlayers)
	{
		this.board = board;
		this.currentPlayerTurn = 0; // Start with Player 1.
		this.numberOfPlayers = numberOfPlayers;
	}
	
	public MoveValidator(Board board, int numberOfPlayers, int initialPlayerTurn)
	{
		this.board = board;
		this.currentPlayerTurn = initialPlayerTurn;
		this.numberOfPlayers = numberOfPlayers;
	}
	
	public abstract Boolean isValidMove(JSONObject gameMessage, Message message);
	
	public void nextPlayerTurn()
	{
		// Done in a round robin style.
		if(currentPlayerTurn == (numberOfPlayers - 1))
		{
			currentPlayerTurn = 0;
		}
		else
		{
			currentPlayerTurn++;
		}
	}
	
	public int getCurrentPlayerTurn()
	{
		return currentPlayerTurn;
	}
	
	public Board getBoard()
	{
		return board;
	}
	
	
}
