package edu.uci.ics.BoardGameServer.Action;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import edu.uci.ics.BoardGameServer.Board.Board;
import edu.uci.ics.BoardGameServer.Board.GameObject;
import edu.uci.ics.BoardGameServer.Board.GameObjectDefinitions;

public class Connect4GameOver extends GameOver {

	Board board;
	
	Connect4GameOver(Board b)
	{
		this.board = b;
	}
	
	public ArrayList<Integer> isWinConditionMet(JSONObject o) {
		ArrayList<Integer> winners = new ArrayList<Integer>();
		
		//check board horizontal
		for(int i=0; i< board.getWidth() - 3; i++)
		{
			for(int j=0; j< board.getHeight(); j++)
			{
				if(board.getTile(j, i).getGameObjects().get(0).getObjectType() 
						== board.getTile(j, i+1).getGameObjects().get(0).getObjectType() &&
						board.getTile(j, i).getGameObjects().get(0).getObjectType() 
						== board.getTile(j, i+2).getGameObjects().get(0).getObjectType() &&
						board.getTile(j, i).getGameObjects().get(0).getObjectType() 
						== board.getTile(j, i+3).getGameObjects().get(0).getObjectType())
				{
					if(board.getTile(j, i).getGameObjects().get(0).getObjectType() == GameObjectDefinitions.CONNECT4_RED)
					{
						winners.add(0); // Player 1 is a winner
					}
					else
					{
						winners.add(1); // Player 2 is a winner
					}
				}
			}
		}
		//check board vertical
		for(int i=0; i<board.getWidth(); i++)
		{
			for(int j=0; j<board.getHeight() - 3; j++)
			{
				if(board.getTile(j, i).getGameObjects().get(0).getObjectType() 
						== board.getTile(j + 1, i).getGameObjects().get(0).getObjectType() &&
						board.getTile(j, i).getGameObjects().get(0).getObjectType() 
						== board.getTile(j + 2, i).getGameObjects().get(0).getObjectType() &&
						board.getTile(j, i).getGameObjects().get(0).getObjectType() 
						== board.getTile(j + 3, i).getGameObjects().get(0).getObjectType())
				{
					if(board.getTile(j, i).getGameObjects().get(0).getObjectType() == GameObjectDefinitions.CONNECT4_RED)
					{
						winners.add(0); // Player 1 is a winner
					}
					else
					{
						winners.add(1); // Player 2 is a winner
					}
				}
			}
		}
		//check board forward diagonal
		for(int i=0; i<board.getWidth() - 3; i++)
		{
			for(int j=0; j<board.getHeight() - 3; j++)
			{
				if(board.getTile(j, i).getGameObjects().get(0).getObjectType() 
						== board.getTile(j + 1, i + 1).getGameObjects().get(0).getObjectType() &&
						board.getTile(j, i).getGameObjects().get(0).getObjectType() 
						== board.getTile(j + 2, i + 2).getGameObjects().get(0).getObjectType() &&
						board.getTile(j, i).getGameObjects().get(0).getObjectType() 
						== board.getTile(j + 3, i + 3).getGameObjects().get(0).getObjectType())
				{
					if(board.getTile(j, i).getGameObjects().get(0).getObjectType() == GameObjectDefinitions.CONNECT4_RED)
					{
						winners.add(0); // Player 1 is a winner
					}
					else
					{
						winners.add(1); // Player 2 is a winner
					}
				}
			}
		}
		//check board forward diagonal
				for(int i=board.getWidth(); i > 3; i--)
				{
					for(int j=board.getHeight(); j > 3; j--)
					{
						if(board.getTile(j, i).getGameObjects().get(0).getObjectType() 
								== board.getTile(j - 1, i - 1).getGameObjects().get(0).getObjectType() &&
								board.getTile(j, i).getGameObjects().get(0).getObjectType() 
								== board.getTile(j - 2, i - 2).getGameObjects().get(0).getObjectType() &&
								board.getTile(j, i).getGameObjects().get(0).getObjectType() 
								== board.getTile(j - 3, i - 3).getGameObjects().get(0).getObjectType())
						{
							if(board.getTile(j, i).getGameObjects().get(0).getObjectType() == GameObjectDefinitions.CONNECT4_RED)
							{
								winners.add(0); // Player 1 is a winner
							}
							else
							{
								winners.add(1); // Player 2 is a winner
							}
						}
					}
				}
		
		return winners;
	}

	public ArrayList<Integer> isLoseConditionMet(JSONObject o) {
		ArrayList<Integer> losers = new ArrayList<Integer>();
		
		//check board horizontal
		for(int i=0; i<board.getWidth() - 3; i++)
		{
			for(int j=0; j<board.getHeight(); j++)
			{
				if(board.getTile(j, i).getGameObjects().get(0).getObjectType() 
						== board.getTile(j, i+1).getGameObjects().get(0).getObjectType() &&
						board.getTile(j, i).getGameObjects().get(0).getObjectType() 
						== board.getTile(j, i+2).getGameObjects().get(0).getObjectType() &&
						board.getTile(j, i).getGameObjects().get(0).getObjectType() 
						== board.getTile(j, i+3).getGameObjects().get(0).getObjectType())
				{
					if(board.getTile(j, i).getGameObjects().get(0).getObjectType() == GameObjectDefinitions.CONNECT4_RED)
					{
						losers.add(1); // Player 1 is a winner
					}
					else
					{
						losers.add(0); // Player 2 is a winner
					}
				}
			}
		}
		//check board vertical
		for(int i=0; i<board.getWidth(); i++)
		{
			for(int j=0; j<board.getHeight() - 3; j++)
			{
				if(board.getTile(j, i).getGameObjects().get(0).getObjectType() 
						== board.getTile(j + 1, i).getGameObjects().get(0).getObjectType() &&
						board.getTile(j, i).getGameObjects().get(0).getObjectType() 
						== board.getTile(j + 2, i).getGameObjects().get(0).getObjectType() &&
						board.getTile(j, i).getGameObjects().get(0).getObjectType() 
						== board.getTile(j + 3, i).getGameObjects().get(0).getObjectType())
				{
					if(board.getTile(j, i).getGameObjects().get(0).getObjectType() == GameObjectDefinitions.CONNECT4_RED)
					{
						losers.add(1); // Player 1 is a winner
					}
					else
					{
						losers.add(0); // Player 2 is a winner
					}
				}
			}
		}
		//check board forward diagonal
		for(int i=0; i<board.getWidth() - 3; i++)
		{
			for(int j=0; j<board.getHeight() - 3; j++)
			{
				if(board.getTile(j, i).getGameObjects().get(0).getObjectType() 
						== board.getTile(j + 1, i + 1).getGameObjects().get(0).getObjectType() &&
						board.getTile(j, i).getGameObjects().get(0).getObjectType() 
						== board.getTile(j + 2, i + 2).getGameObjects().get(0).getObjectType() &&
						board.getTile(j, i).getGameObjects().get(0).getObjectType() 
						== board.getTile(j + 3, i + 3).getGameObjects().get(0).getObjectType())
				{
					if(board.getTile(j, i).getGameObjects().get(0).getObjectType() == GameObjectDefinitions.CONNECT4_RED)
					{
						losers.add(1); // Player 1 is a winner
					}
					else
					{
						losers.add(0); // Player 2 is a winner
					}
				}
			}
		}
		//check board forward diagonal
				for(int i=board.getWidth(); i > 3; i--)
				{
					for(int j=board.getHeight(); j > 3; j--)
					{
						if(board.getTile(j, i).getGameObjects().get(0).getObjectType() 
								== board.getTile(j - 1, i - 1).getGameObjects().get(0).getObjectType() &&
								board.getTile(j, i).getGameObjects().get(0).getObjectType() 
								== board.getTile(j - 2, i - 2).getGameObjects().get(0).getObjectType() &&
								board.getTile(j, i).getGameObjects().get(0).getObjectType() 
								== board.getTile(j - 3, i - 3).getGameObjects().get(0).getObjectType())
						{
							if(board.getTile(j, i).getGameObjects().get(0).getObjectType() == GameObjectDefinitions.CONNECT4_RED)
							{
								losers.add(1); // Player 1 is a winner
							}
							else
							{
								losers.add(0); // Player 2 is a winner
							}
						}
					}
				}
		
		return losers;
	}

	public ArrayList<Integer> isTieConditionMet(JSONObject o) {
		ArrayList<Integer> ties = new ArrayList<Integer>();
		
		for(int i=0; i<board.getWidth(); i++)
		{
			for(int j=0; j<board.getWidth(); j++)
			{
				if(board.getTile(j, i).getGameObjects().size() == 0)
				{
					return ties; // The board has not been filled up.
				}
			}
		}
		
		if(isWinConditionMet(o).size() == 0) // There was no four in a row. Tie game.
		{
			ties.add(0);
			ties.add(1);
		}
		
		return ties;
	}

}
