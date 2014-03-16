package edu.uci.ics.BoardGameServer.Action;

import edu.uci.ics.BoardGameServer.Board.Board;
import edu.uci.ics.BoardGameServer.Board.GameObject;

public class TicTacToeGameOver extends GameOver {

	Board board;
	
	TicTacToeGameOver(Board b)
	{
		this.board = b;
	}
	
	public boolean isWinCondidtionMet(String s) {
		
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
					return true;
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
					return true;
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
				return true;
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
				return true;
			}
		}
		
		return false;
	}

	public boolean isLoseCondidtionMet(String s) {
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
					return true;
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
					return true;
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
				return true;
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
				return true;
			}
		}
		
		return false;
	}

	public boolean isTieCondidtionMet(String s) {
		for(int i=0; i<3; i++)
		{
			for(int j=0; j<3; j++)
			{
				if(board.getTile(i, j).getGameObjects().size() == 0)
				{
					return false; // The board has not been filled up.
				}
			}
		}
		
		return !isWinCondidtionMet(s); // There was no three in a row. Tie game.
	}

}
