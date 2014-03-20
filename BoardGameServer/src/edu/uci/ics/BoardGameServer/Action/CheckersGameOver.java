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
		
		for(int j=0; j<board.getWidth(); j++) {
			if(board.getTile(0, j).getGameObjects().size() > 0) {
				if(board.getTile(0, j).getGameObjects().get(0).getObjectType() == GameObjectDefinitions.CHECKERS_RED) {
					winners.add(0);
					return winners;
				}
			}
		}
		
		for(int j=0; j<board.getWidth(); j++) {
			if(board.getTile(board.getHeight()-1, j).getGameObjects().size() > 0) {
				if(board.getTile(board.getHeight()-1, j).getGameObjects().get(0).getObjectType() == GameObjectDefinitions.CHECKERS_BLACK) {
					winners.add(1);
					return winners;
				}
			}
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
		
		for(int i=0; i<board.getHeight(); i++) {
			for(int j=0; j<board.getWidth(); j++) {
				if(board.getTile(i, j).getGameObjects().size() > 0) {
					if(board.getTile(i, j).getGameObjects().get(0).getObjectType() == GameObjectDefinitions.CHECKERS_RED) {
						if( i > 0) {
							if(j == 0) {
								if(board.getTile(i-1, j+1).getGameObjects().size() == 0) {
									redHasOpenMove = true;
								}
								if(i > 2) {
									if(board.getTile(i-2, j+2).getGameObjects().size() == 0 && 
									   board.getTile(i-1, j+1).getGameObjects().size() > 0 &&
									   board.getTile(i-1, j+1).getGameObjects().get(0).getObjectType() == 
									   GameObjectDefinitions.CHECKERS_BLACK) {
										blackHasOpenMove = true;
									}
								}
							}
							else if(j == board.getWidth() -1) {
								if(board.getTile(i-1, j-1).getGameObjects().size() == 0) {
									redHasOpenMove = true;
								}
								if(i > 2) {
									if(board.getTile(i-2, j-2).getGameObjects().size() == 0 && 
									   board.getTile(i-1, j-1).getGameObjects().size() > 0 &&
									   board.getTile(i-1, j-1).getGameObjects().get(0).getObjectType() == 
									   GameObjectDefinitions.CHECKERS_BLACK) {
										blackHasOpenMove = true;
									}
								}
							}
							else {
								if(board.getTile(i-1, j-1).getGameObjects().size() == 0 ||
								   board.getTile(i-1, j+1).getGameObjects().size() == 0) {
									redHasOpenMove = true;
								}
								if(i > 2 && j<board.getWidth()-2 && j>2) {
									if(board.getTile(i-2, j+2).getGameObjects().size() == 0 && 
									   board.getTile(i-1, j+1).getGameObjects().size() > 0 &&
									   board.getTile(i-1, j+1).getGameObjects().get(0).getObjectType() == 
									   GameObjectDefinitions.CHECKERS_BLACK) {
										blackHasOpenMove = true;
									}
									if(board.getTile(i-2, j-2).getGameObjects().size() == 0 && 
									   board.getTile(i-1, j-1).getGameObjects().size() > 0 &&
									   board.getTile(i-1, j-1).getGameObjects().get(0).getObjectType() == 
									   GameObjectDefinitions.CHECKERS_BLACK) {
										blackHasOpenMove = true;
									}
								}
							}
						}
					}
					else { //Game Piece is black
						if( i < board.getHeight()-1) {
							if(j == 0) {
								if(board.getTile(i+1, j+1).getGameObjects().size() == 0) {
									blackHasOpenMove = true;
								}
								if(i<board.getHeight()-2) {
									if(board.getTile(i+2, j+2).getGameObjects().size() == 0 && 
									   board.getTile(i+1, j+1).getGameObjects().size() > 0 &&
									   board.getTile(i+1, j+1).getGameObjects().get(0).getObjectType() == 
									   GameObjectDefinitions.CHECKERS_RED) {
										blackHasOpenMove = true;
									}
								}
							}
							else if(j == board.getWidth() - 1) {
								if(board.getTile(i+1, j-1).getGameObjects().size() == 0) {
									blackHasOpenMove = true;
								}
								if(i<board.getHeight()-2) {
									if(board.getTile(i+2, j-2).getGameObjects().size() == 0 && 
									   board.getTile(i+1, j-1).getGameObjects().size() > 0 &&
									   board.getTile(i+1, j-1).getGameObjects().get(0).getObjectType() == 
									   GameObjectDefinitions.CHECKERS_RED) {
										blackHasOpenMove = true;
									}
								}
							}
							else {
								if(board.getTile(i+1, j-1).getGameObjects().size() == 0 ||
								   board.getTile(i+1, j+1).getGameObjects().size() == 0) {
									blackHasOpenMove = true;
								}
								if(i<board.getHeight()-2 && j<board.getWidth()-2 && j>2)
								{
									if(i<board.getHeight()-2) {
										if(board.getTile(i+2, j+2).getGameObjects().size() == 0 && 
										   board.getTile(i+1, j+1).getGameObjects().size() > 0 &&
										   board.getTile(i+1, j+1).getGameObjects().get(0).getObjectType() == 
										   GameObjectDefinitions.CHECKERS_RED) {
											blackHasOpenMove = true;
										}
									}
									if(i<board.getHeight()-2) {
										if(board.getTile(i+2, j-2).getGameObjects().size() == 0 && 
										   board.getTile(i+1, j-1).getGameObjects().size() > 0 &&
										   board.getTile(i+1, j-1).getGameObjects().get(0).getObjectType() == 
										   GameObjectDefinitions.CHECKERS_RED) {
											blackHasOpenMove = true;
										}
									}
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
