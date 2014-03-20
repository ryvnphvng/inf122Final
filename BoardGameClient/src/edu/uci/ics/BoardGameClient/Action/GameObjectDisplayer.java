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
			return new JLabel("<html><font color='red' size='20'>X</font></html>");
			//JLabel j = new JLabel();
			//j.setBackground(Color.RED);
			//return j;
		}
		else if(objectType == GameObjectDefinitions.TICTACTOE_O)
		{
			return new JLabel("<html><font color='black' size='20'>O</font></html>");
			//JLabel j = new JLabel();
			//j.setBackground(Color.BLUE);
			//return j;
		}
		
		if(objectType == GameObjectDefinitions.CONNECT4_RED)
		{
			return new JLabel("<html><font color='red' size='20'>O</font></html>");
			//JLabel j = new JLabel();
			//j.setBackground(Color.RED);
			//return j;
		}
		else if(objectType == GameObjectDefinitions.CONNECT4_YELLOW)
		{
			return new JLabel("<html><font color='yellow' size='20'>O</font></html>");
			//JLabel j = new JLabel();
			//j.setBackground(Color.BLUE);
			//return j;
		}
		
		return null;
	}
}
