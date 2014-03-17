package edu.uci.ics.BoardGameClient.Action;

import java.awt.Component;

import javax.swing.JLabel;

import edu.uci.ics.BoardGameClient.Board.GameObjectDefinitions;

public class GameObjectDisplayer {
	
	
	public static Component displayGameObject(int objectType)
	{
		if(objectType == GameObjectDefinitions.TICTACTOE_X)
		{
			return new JLabel("X");
		}
		else if(objectType == GameObjectDefinitions.TICTACTOE_O)
		{
			return new JLabel("O");
		}
		
		return null;
	}
}
