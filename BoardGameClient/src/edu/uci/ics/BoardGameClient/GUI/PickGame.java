package edu.uci.ics.BoardGameClient.GUI;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

import edu.uci.ics.BoardGameClient.Action.Action;
import edu.uci.ics.BoardGameClient.Common.Definitions;

public class PickGame {

	private Action action;
	private JTextArea textConsole;
	private JPanel panelLogin;
	private JRadioButton buttonTicTacToe;
	private JRadioButton buttonConnectFour;

	public PickGame(Action action, JTextArea textConsole) {
		this.action = action;
		this.textConsole = textConsole;

		setupPanel();
	}

	private void setupPanel() {

		panelLogin = new JPanel();
		panelLogin.setLayout(new BoxLayout(panelLogin, BoxLayout.PAGE_AXIS));

		Component boxLeft = Box.createRigidArea(new Dimension(10, 0));
		Component boxTop = Box.createRigidArea(new Dimension(0, 20));

		JLabel labelSelectGame = new JLabel("Select game");
		labelSelectGame.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		Component boxSelectGame = Box.createRigidArea(new Dimension(0, 20));

		buttonTicTacToe = new JRadioButton("TicTacToe");
		buttonTicTacToe.setAlignmentX(JRadioButton.LEFT_ALIGNMENT);

		buttonConnectFour = new JRadioButton("Connect Four");
		buttonConnectFour.setAlignmentX(JRadioButton.LEFT_ALIGNMENT);

		Component boxBeforeSelect = Box.createRigidArea(new Dimension(0, 20));
		JButton buttonSelect = new JButton("Select");

		ButtonGroup groupGames = new ButtonGroup();
		groupGames.add(buttonTicTacToe);
		groupGames.add(buttonConnectFour);

		panelLogin.add(boxLeft);
		panelLogin.add(boxTop);
		panelLogin.add(labelSelectGame);
		panelLogin.add(boxSelectGame);
		panelLogin.add(buttonTicTacToe);
		panelLogin.add(buttonConnectFour);
		panelLogin.add(boxBeforeSelect);
		panelLogin.add(buttonSelect);

		// buttonSelect
		buttonSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				selectionGame();
			}
		});

	}

	public JPanel getPanel() {
		return panelLogin;
	}

	private void selectionGame() {
		if (buttonTicTacToe.isSelected()) {
			textConsole.append("Great choice, you selected TicTacToe \n");
			action.createGame(Definitions.GAMETYPETICTACTOE);
			return;
		}
		if (buttonConnectFour.isSelected()) {
			textConsole.append("Great choice, you selected Connect Four \n");
			action.createGame(Definitions.GAMETYPECONNECTFOUR);
			return;
		}
		textConsole.append("Please select a game \n");
	}

}
