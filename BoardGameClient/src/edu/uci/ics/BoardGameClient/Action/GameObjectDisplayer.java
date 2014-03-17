package edu.uci.ics.BoardGameClient.Action;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;

import edu.uci.ics.BoardGameClient.Board.GameObjectDefinitions;

public class GameObjectDisplayer {
	
	
	public static Component displayGameObject(int objectType)
	{
		if(objectType == GameObjectDefinitions.TICTACTOE_X)
		{
			return new JLabel("<html><font color='red'>X</font></html>");
			//JLabel j = new JLabel();
			//j.setBackground(Color.RED);
			//return j;
		}
		else if(objectType == GameObjectDefinitions.TICTACTOE_O)
		{
			return new JLabel("O");
			//JLabel j = new JLabel();
			//j.setBackground(Color.BLUE);
			//return j;
		}
		
		return null;
	}
}
