package edu.uci.ics.BoardGameServer.Action;

import edu.uci.ics.BoardGameClient.Common.Definitions;
import edu.uci.ics.BoardGameServer.Board.Board;

public class GameBoardFactory {

	GameObjectFactory gof;
	
	public GameBoardFactory()
	{
		gof = new GameObjectFactory();
	}
	
	public Board createBoard(int gameType, int playerNum, int nextObjID)
	{
		if(gameType == Definitions.GAMETYPETICTACTOE)
		{
			int TicTacToe_BoardHeight = 3;
			int TicTacToe_BoardWidth = 3;
			
			return new Board(TicTacToe_BoardHeight, TicTacToe_BoardWidth, Definitions.GAMETYPETICTACTOE);
		}
		
		return null;
	}
}
