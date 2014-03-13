package edu.uci.ics.BoardGameClient.GUI;

import edu.uci.ics.BoardGameClient.Action.Action;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class GUI {

	private Action action;
	private JFrame frame = new JFrame("Board Game Client");

	public void setAction(Action action) {
		this.action = action;
		setupWindow();
	}

	private void setupWindow() {
		// tabbedPane
		JTabbedPane tabbedPane = new JTabbedPane();

		// tabHome
		@SuppressWarnings("unused")
		TabHome tabHome = new TabHome(action, frame, tabbedPane);

		// frame
		frame.add(tabbedPane, 0);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				closeFrame();
			}
		});
		frame.setPreferredSize(new Dimension(800, 600));
		frame.setSize(800, 600);
		frame.pack();
		frame.setVisible(true);

	}

	private void closeFrame() {
		frame.setVisible(false);
		frame.dispose();
		action.disconnect();
		action.shutdown();
	}

}
