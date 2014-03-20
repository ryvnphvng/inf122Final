package edu.uci.ics.BoardGameServer.Action;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import edu.uci.ics.BoardGameServer.Board.Board;
import edu.uci.ics.BoardGameServer.Board.GameObjectDefinitions;

public class CheckersGameOver extends GameOver {

	private Board board;
	
	public CheckersGameOver(Board b)
	{
		this.board = b;
	}
	
	@Override
	public ArrayList<Integer> isWinConditionMet(JSONObject o) {
		ArrayList<Integer> winners = new ArrayList<Integer>();
		
		boolean foundRedPiece = false;
		boolean foundBlackPiece = false;
		
		
		for(int i=0; i<board.getHeight(); i++) {
			for(int j=0; j<board.getWidth(); j++) {
				if(board.getTile(i, j).getGameObjects().size() > 0) {
					if(board.getTile(i, j).getGameObjects().get(0).getObjectType() == GameObjectDefinitions.CHECKERS_RED) {
						foundRedPiece = true;
					}
					else if(board.getTile(i, j).getGameObjects().get(0).getObjectType() == GameObjectDefinitions.CHECKERS_BLACK) {
						foundBlackPiece = true;
					}
				}
				
				if(foundRedPiece && foundBlackPiece) { // Both red and black sill have remaining pieces.
					return winners;
				}
			}
		}
		
		if(!foundRedPiece) { // There were no remaining red pieces. Black wins.
			winners.add(1);
		}
		else if(!foundBlackPiece) { //// There were no more black pieces. Red wins.
			winners.add(0);
		}
		
		return winners;
	}

	@Override
	public ArrayList<Integer> isLoseConditionMet(JSONObject o) {
		ArrayList<Integer> losers = new ArrayList<Integer>();
		ArrayList<Integer> winners = this.isWinConditionMet(o);		
		
		if(winners.size() > 0)
		{
			if(winners.get(0) == GameObjectDefinitions.TICTACTOE_X) // Player 1 is a winner.
			{
				losers.add(1); //Player 2 lost.
			}
			else // Player 2 is a winner.
			{
				losers.add(0); // Player 1 lost.
			}
		}
		
		return losers;
	}

	@Override
	public ArrayList<Integer> isTieConditionMet(JSONObject o) {
		ArrayList<Integer> ties = new ArrayList<Integer>();
		
		boolean blackHasOpenMove = false;
		boolean redHasOpenMove = false;
		
		for(int i=0; i<board.getHeight()-1; i++) {
			for(int j=0; j<board.getWidth(); j++) {
				if(board.getTile(i, j).getGameObjects().size() > 0) {
					if(board.getTile(i, j).getGameObjects().get(0).getObjectType() == GameObjectDefinitions.CHECKERS_RED) {
						if( i > 0) {
							if(j == 0) {
								if(board.getTile(i-1, j+1).getGameObjects().size() == 0) {
									redHasOpenMove = true;
								}
							}
							else if(j == board.getWidth() -1) {
								if(board.getTile(i-1, j-1).getGameObjects().size() == 0) {
									redHasOpenMove = true;
								}
							}
							else {
								if(board.getTile(i-1, j-1).getGameObjects().size() == 0 ||
								   board.getTile(i-1, j+1).getGameObjects().size() == 0) {
									redHasOpenMove = true;
								}
							}
						}
					}
					else { //Game Piece is black
						if( i < board.getHeight()) {
							if(j == 0) {
								if(board.getTile(i+1, j+1).getGameObjects().size() == 0) {
									blackHasOpenMove = true;
								}
							}
							else if(j == board.getWidth() - 1) {
								if(board.getTile(i+1, j-1).getGameObjects().size() == 0) {
									blackHasOpenMove = true;
								}
							}
							else {
								if(board.getTile(i+1, j-1).getGameObjects().size() == 0 ||
								   board.getTile(i+1, j+1).getGameObjects().size() == 0) {
									blackHasOpenMove = true;
								}
							}
						}
					}
				}
			}
		}
		
		if(redHasOpenMove && blackHasOpenMove) {
			return ties;
		}
		else {
			ties.add(0);
			ties.add(1);
			return ties;	
		}
	}

}
