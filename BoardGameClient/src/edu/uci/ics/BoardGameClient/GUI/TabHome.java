package edu.uci.ics.BoardGameClient.GUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.uci.ics.BoardGameClient.Action.Action;
import edu.uci.ics.BoardGameClient.Common.Definitions;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class TabHome {

	private Action action;
	private JFrame frame;
	private JTabbedPane tabbedPane;

	public TabHome(Action action, JFrame frame, JTabbedPane tabbedPane) {
		this.action = action;
		this.frame = frame;
		this.tabbedPane = tabbedPane;

		setupTabHome();
	}

	private void setupTabHome() {

		// panelHome
		JPanel panelHome = new JPanel(new BorderLayout());

		// tabbedPane
		tabbedPane.removeAll();
		tabbedPane.addTab("Home", null, panelHome, "Home");

		// buttonLoginStudent
		JButton buttonCreateGame = new JButton("Create Game");
		buttonCreateGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				createGame();
			}
		});
		
		panelHome.add(buttonCreateGame);

		frame.repaint();
	}

	private void createGame() {
		action.createGame(Definitions.GAMETYPETICTACTOE);
	}

}
