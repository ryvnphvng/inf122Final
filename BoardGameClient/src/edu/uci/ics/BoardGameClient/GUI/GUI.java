package edu.uci.ics.BoardGameClient.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import edu.uci.ics.BoardGameClient.Action.Action;
import edu.uci.ics.BoardGameClient.Action.GameObjectDisplayer;
import edu.uci.ics.BoardGameClient.Action.MoveSender;
import edu.uci.ics.BoardGameClient.Board.Board;
import edu.uci.ics.BoardGameClient.Board.Tile;
import edu.uci.ics.BoardGameClient.Common.Definitions;

public class GUI {

	private Action action;
	private Board board;
	private int gameType;
	private JFrame frame = new JFrame("Board Game Client");

	public GUI(Action action, Board board, int gameType)
	{
		this.action = action;
		this.board = board;
		this.gameType = gameType;
		
		
		setupWindow();
	}

	private void setupWindow() {
		// // tabbedPane
		// JTabbedPane tabbedPane = new JTabbedPane();
		//
		// // tabHome
		// @SuppressWarnings("unused")
		// TabHome tabHome = new TabHome(action, frame, tabbedPane);

		// frame
		// frame.add(tabbedPane, 0);

		// Determines the Height and Width of the board
		int height = determineHeight();
		int width = determineWidth();

		frame.setMinimumSize(new Dimension(640, 600));
		frame.setMaximumSize(new Dimension(660, 600));
		frame.setLayout(new GridLayout(height, width));

		for (int i = 0; i < height; i++) {
			for(int j=0; j<width; j++)
			{
				frame.add(new BoardPanel(action, board.getTile(i,j), i, j, gameType));
			}
		}

		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				closeFrame();
			}
		});
		frame.pack();
		frame.setVisible(true);

	}

	private void closeFrame() {
		frame.setVisible(false);
		frame.dispose();
		action.shutdown();
	}

	private int determineHeight() {

		if (action.getGameType() == Definitions.GAMETYPETICTACTOE) {
			return 3;
		}

		return -1;
	}

	private int determineWidth() {

		if (action.getGameType() == Definitions.GAMETYPETICTACTOE) {
			return 3;
		}

		return -1;
	}

	// Not sure if this will repaint the new game board correctly. Will need to test.
	public void update()
	{
		int height = determineHeight();
		int width = determineWidth();
		
		frame.getContentPane().removeAll();
		
		frame.setLayout(new GridLayout(height, width));

		for (int i = 0; i < height; i++) {
			for(int j=0; j<width; j++)
			{
				frame.add(new BoardPanel(action, board.getTile(i,j), i, j, gameType));
			}
		}

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				closeFrame();
			}
		});
		
		frame.pack();
	}
	
	public void winMessage()
	{
		JOptionPane.showMessageDialog(frame,
			    "You have won the game.",
			    "Win",
			    JOptionPane.PLAIN_MESSAGE);
		this.closeFrame();
	}
	
	public void loseMessage()
	{
		JOptionPane.showMessageDialog(frame,
			    "You have lost the game.",
			    "Lose",
			    JOptionPane.PLAIN_MESSAGE);
		this.closeFrame();
	}
	
	public void tieMessage()
	{
		JOptionPane optionPane = new JOptionPane();
		JOptionPane.showMessageDialog(frame,
			    "Tie game.",
			    "Tie",
			    JOptionPane.PLAIN_MESSAGE);
		this.closeFrame();
	}
}

class BoardPanel extends JPanel {
	private int row;
	private int col;
	private int gameType;
	private Tile tile;
	private Action action;

	// Not sure if setting to final will break things.
	public BoardPanel(final Action action, Tile tile, final int row, final int col, final int gameType) {

		this.row = row;
		this.col = col;
		this.gameType = gameType;
		this.tile = tile;
		
		Color bgColor = new Color(255, 255, 255);

		setPreferredSize(new Dimension(100, 100));

		this.setBackground(bgColor);
		this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		
		if(tile.getGameObjects().size() > 0) // If true, we want to display what's in this tile
		{
			for(int i=0; i<tile.getGameObjects().size(); i++)
			{
				int objectType = tile.getGameObjects().get(i).getObjectType();
				this.add(GameObjectDisplayer.displayGameObject(objectType));
			}
			
		}
		
		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				MoveSender.sendMessage(action, gameType, row, col);
			}
		});
	}
	
	
}
