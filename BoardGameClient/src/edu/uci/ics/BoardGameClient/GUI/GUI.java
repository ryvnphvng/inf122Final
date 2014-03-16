package edu.uci.ics.BoardGameClient.GUI;

import edu.uci.ics.BoardGameClient.Action.Action;
import edu.uci.ics.BoardGameClient.Common.Definitions;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;



import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class GUI {

	private Action action;
	private JFrame frame = new JFrame("Board Game Client");

	public void setAction(Action action) {
		this.action = action;
		setupWindow();
	}

	
	private void setupWindow() {
//		// tabbedPane
//		JTabbedPane tabbedPane = new JTabbedPane();
//
//		// tabHome
//		@SuppressWarnings("unused")
//		TabHome tabHome = new TabHome(action, frame, tabbedPane);

		// frame
//		frame.add(tabbedPane, 0);
		
		//Determines the Height and Width of the board
		int height = determineHeight();
		int width = determineWidth(); 
		
		frame.setMinimumSize(new Dimension(640, 600));
		frame.setMaximumSize(new Dimension(660, 600));
		frame.setLayout(new GridLayout(height, width));
		
		for(int i=0;i<width * height;i++)
		{
			frame.add(new BoardPanel());
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
		action.disconnect();
		action.shutdown();
	}
	
	private int determineHeight(){
		
		if(action.getGameType() == Definitions.GAMETYPETICTACTOE){
			return 3;
		}
		
		return -1;
	}
	
	private int determineWidth(){
		
		if(action.getGameType() == Definitions.GAMETYPETICTACTOE){
			return 3;
		}
		
		return -1;
	}
	
	public static void main(String [] args)
	{		
		GUI gui = new GUI();
		gui.setupWindow();
	}
	

}


class BoardPanel extends JPanel{
	Color bgColor;
	public BoardPanel()
	{	
		
		bgColor = new Color(255,255,255);

		setPreferredSize(new Dimension(100, 100));
		
		this.setBackground(bgColor);
		this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		
		this.addMouseListener(new MouseAdapter() 
		{
            public void mousePressed(MouseEvent e) 
            {
                Random r  = new Random();
                setBackground(new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)));
                
                repaint();
            }
		});
	}
}