package edu.uci.ics.BoardGameServer.Action;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import edu.uci.ics.BoardGameServer.Board.Board;
import edu.uci.ics.BoardGameServer.Board.GameObject;
import edu.uci.ics.BoardGameServer.Board.GameObjectDefinitions;

public class TicTacToeGameOver extends GameOver {

	Board board;
	
	TicTacToeGameOver(Board b)
	{
		this.board = b;
	}
	
	public ArrayList<Integer> isWinConditionMet(JSONObject o) {
		
		ArrayList<Integer> winners = new ArrayList<Integer>();
		
		// Check if there is a horizontal three in a row
		for(int i=0; i<3; i++)
		{
			if(board.getTile(i, 0).getGameObjects().size() > 0 &&
			   board.getTile(i, 1).getGameObjects().size() > 0 &&
			   board.getTile(i, 2).getGameObjects().size() > 0)
			{
				GameObject go1 = board.getTile(i, 0).getGameObjects().get(0);
				GameObject go2 = board.getTile(i, 1).getGameObjects().get(0);
				GameObject go3 = board.getTile(i, 2).getGameObjects().get(0);
				
				if(go1.getObjectType() == go2.getObjectType() &&
				   go1.getObjectType() == go3.getObjectType())
				{
					if(go1.getObjectType() == GameObjectDefinitions.TICTACTOE_X)
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
		
		// Check if there is a vertical three in a row
		for(int i=0; i<3; i++)
		{
			if(board.getTile(0, i).getGameObjects().size() > 0 &&
			   board.getTile(1, i).getGameObjects().size() > 0 &&
			   board.getTile(2, i).getGameObjects().size() > 0)
			{
				GameObject go1 = board.getTile(0, i).getGameObjects().get(0);
				GameObject go2 = board.getTile(1, i).getGameObjects().get(0);
				GameObject go3 = board.getTile(2, i).getGameObjects().get(0);
				
				if(go1.getObjectType() == go2.getObjectType() &&
				   go1.getObjectType() == go3.getObjectType())
				{
					if(go1.getObjectType() == GameObjectDefinitions.TICTACTOE_X)
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
		
		// Check if there is a diagonal three in a row
		if(board.getTile(0, 0).getGameObjects().size() > 0 &&
		   board.getTile(1, 1).getGameObjects().size() > 0 &&
		   board.getTile(2, 2).getGameObjects().size() > 0)
		{
			GameObject go1 = board.getTile(0, 0).getGameObjects().get(0);
			GameObject go2 = board.getTile(1, 1).getGameObjects().get(0);
			GameObject go3 = board.getTile(2, 2).getGameObjects().get(0);
			
			if(go1.getObjectType() == go2.getObjectType() &&
			   go1.getObjectType() == go3.getObjectType())
			{
				if(go1.getObjectType() == GameObjectDefinitions.TICTACTOE_X)
				{
					winners.add(0); // Player 1 is a winner
				}
				else
				{
					winners.add(1); // Player 2 is a winner
				}
			}
		}
		
		if(board.getTile(0, 2).getGameObjects().size() > 0 &&
		   board.getTile(1, 1).getGameObjects().size() > 0 &&
		   board.getTile(2, 0).getGameObjects().size() > 0)
		{
			GameObject go1 = board.getTile(0, 2).getGameObjects().get(0);
			GameObject go2 = board.getTile(1, 1).getGameObjects().get(0);
			GameObject go3 = board.getTile(2, 0).getGameObjects().get(0);
			
			if(go1.getObjectType() == go2.getObjectType() &&
			   go1.getObjectType() == go3.getObjectType())
			{
				if(go1.getObjectType() == GameObjectDefinitions.TICTACTOE_X)
				{
					winners.add(0); // Player 1 is a winner
				}
				else
				{
					winners.add(1); // Player 2 is a winner
				}
			}
		}
		
		return winners;
	}

	public ArrayList<Integer> isLoseConditionMet(JSONObject o) {
		ArrayList<Integer> losers = new ArrayList<Integer>();
		
		// Check if there is a horizontal three in a row
		for(int i=0; i<3; i++)
		{
			if(board.getTile(i, 0).getGameObjects().size() > 0 &&
			   board.getTile(i, 1).getGameObjects().size() > 0 &&
			   board.getTile(i, 2).getGameObjects().size() > 0)
			{
				GameObject go1 = board.getTile(i, 0).getGameObjects().get(0);
				GameObject go2 = board.getTile(i, 1).getGameObjects().get(0);
				GameObject go3 = board.getTile(i, 2).getGameObjects().get(0);
				
				if(go1.getObjectType() == go2.getObjectType() &&
				   go1.getObjectType() == go3.getObjectType())
				{
					if(go1.getObjectType() == GameObjectDefinitions.TICTACTOE_X)
					{
						losers.add(1); // Player 2 is a loser
					}
					else
					{
						losers.add(0); // Player 1 is a loser
					}
				}
			}
			
		}
		
		// Check if there is a vertical three in a row
		for(int i=0; i<3; i++)
		{
			if(board.getTile(0, i).getGameObjects().size() > 0 &&
			   board.getTile(1, i).getGameObjects().size() > 0 &&
			   board.getTile(2, i).getGameObjects().size() > 0)
			{
				GameObject go1 = board.getTile(0, i).getGameObjects().get(0);
				GameObject go2 = board.getTile(1, i).getGameObjects().get(0);
				GameObject go3 = board.getTile(2, i).getGameObjects().get(0);
				
				if(go1.getObjectType() == go2.getObjectType() &&
				   go1.getObjectType() == go3.getObjectType())
				{
					if(go1.getObjectType() == GameObjectDefinitions.TICTACTOE_X)
					{
						losers.add(1); // Player 2 is a loser
					}
					else
					{
						losers.add(0); // Player 1 is a loser
					}
				}
			}
			
		}
		
		// Check if there is a diagonal three in a row
		if(board.getTile(0, 0).getGameObjects().size() > 0 &&
		   board.getTile(1, 1).getGameObjects().size() > 0 &&
		   board.getTile(2, 2).getGameObjects().size() > 0)
		{
			GameObject go1 = board.getTile(0, 0).getGameObjects().get(0);
			GameObject go2 = board.getTile(1, 1).getGameObjects().get(0);
			GameObject go3 = board.getTile(2, 2).getGameObjects().get(0);
			
			if(go1.getObjectType() == go2.getObjectType() &&
			   go1.getObjectType() == go3.getObjectType())
			{
				if(go1.getObjectType() == GameObjectDefinitions.TICTACTOE_X)
				{
					losers.add(1); // Player 2 is a loser
				}
				else
				{
					losers.add(0); // Player 1 is a loser
				}
			}
		}
		
		if(board.getTile(0, 2).getGameObjects().size() > 0 &&
		   board.getTile(1, 1).getGameObjects().size() > 0 &&
		   board.getTile(2, 0).getGameObjects().size() > 0)
		{
			GameObject go1 = board.getTile(0, 2).getGameObjects().get(0);
			GameObject go2 = board.getTile(1, 1).getGameObjects().get(0);
			GameObject go3 = board.getTile(2, 0).getGameObjects().get(0);
			
			if(go1.getObjectType() == go2.getObjectType() &&
			   go1.getObjectType() == go3.getObjectType())
			{
				if(go1.getObjectType() == GameObjectDefinitions.TICTACTOE_X)
				{
					losers.add(1); // Player 2 is a loser
				}
				else
				{
					losers.add(0); // Player 1 is a loser
				}
			}
		}
		
		return losers;
	}

	public ArrayList<Integer> isTieConditionMet(JSONObject o) {
		ArrayList<Integer> ties = new ArrayList<Integer>();
		
		for(int i=0; i<3; i++)
		{
			for(int j=0; j<3; j++)
			{
				if(board.getTile(i, j).getGameObjects().size() == 0)
				{
					return ties; // The board has not been filled up.
				}
			}
		}
		
		if(isWinConditionMet(o).size() == 0) // There was no three in a row. Tie game.
		{
			ties.add(0);
			ties.add(1);
		}
		
		return ties;
	}

}
