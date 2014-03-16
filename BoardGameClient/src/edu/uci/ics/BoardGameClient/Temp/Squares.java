// This is a program that was created to practice using the java swing
// library. It is a series of squares that change color when clicked.
// 
// Coded by: Daniel Hirsch 11/23/2013

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Squares extends JFrame{
	public Squares()
	{
		super("Squares");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		int size = 5;
		
		Container c = this.getContentPane();
		c.setLayout(new GridLayout(size, size));
		
		for(int i=0;i<Math.pow(size, 2);i++)
		{
			c.add(new SquarePanel());
		}
		
		this.pack();
		this.setVisible(true);
	}
	
	public static void main(String[] args)
	{
		Squares squares = new Squares();
		System.out.println(squares.getClass().getSimpleName());
		Integer i =5;
		System.out.println(i.getClass().getSimpleName());
		
		
	}
}

class SquarePanel extends JPanel{
	Color bgColor;
	public SquarePanel()
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