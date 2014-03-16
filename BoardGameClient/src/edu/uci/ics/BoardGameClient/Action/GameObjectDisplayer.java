package edu.uci.ics.BoardGameClient.Action;

import java.awt.Component;

import javax.swing.JLabel;

import edu.uci.ics.BoardGameServer.Board.GameObjectDefinitions;

public class GameObjectDisplayer {
	
	
	public static Component displayGameObject(int gameType)
	{
		if(gameType == GameObjectDefinitions.TICTACTOE_X)
		{
			return new JLabel("X");
		}
		else if(gameType == GameObjectDefinitions.TICTACTOE_O)
		{
			return new JLabel("O");
		}
		
		return null;
	}
}
