package edu.uci.ics.BoardGameClient.GUI;

import edu.uci.ics.BoardGameClient.Action.Action;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Login {

	private Action action;
	private JTextArea textConsole;
	private JPanel panelLogin;
	private JFormattedTextField fieldUsername;
	private JFormattedTextField fieldPassword;

	public Login(Action action, JTextArea textConsole) {
		this.action = action;
		this.textConsole = textConsole;

		setupPanel();
	}

	private void setupPanel() {
		panelLogin = new JPanel();
		panelLogin.setLayout(new BoxLayout(panelLogin, BoxLayout.PAGE_AXIS));

		
		Component boxLeft = Box.createRigidArea(new Dimension(5, 0));
		Component boxTop = Box.createRigidArea(new Dimension(0, 20));
		
		JLabel labelLogin = new JLabel("Login");
		labelLogin.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		Component boxLogin = Box.createRigidArea(new Dimension(0, 20));

		JLabel labelUsername = new JLabel("Username");
		labelUsername.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		fieldUsername = new JFormattedTextField();
		fieldUsername.setAlignmentX(JFormattedTextField.LEFT_ALIGNMENT);
		fieldUsername.setMaximumSize(new Dimension(300, 50));
		Component boxUsername = Box.createRigidArea(new Dimension(0, 20));
		
		JLabel labelPassword = new JLabel("Password");
		labelPassword.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		fieldPassword = new JFormattedTextField();
		fieldPassword.setAlignmentX(JFormattedTextField.LEFT_ALIGNMENT);
		fieldPassword.setMaximumSize(new Dimension(300, 50));
		Component boxPassword = Box.createRigidArea(new Dimension(0, 20));
		
		JButton buttonLogin = new JButton("Login");

		panelLogin.add(boxLeft);
		panelLogin.add(boxTop);
		panelLogin.add(labelLogin);
		panelLogin.add(boxLogin);
		panelLogin.add(labelUsername);
		panelLogin.add(fieldUsername);
		panelLogin.add(boxUsername);
		panelLogin.add(labelPassword);
		panelLogin.add(fieldPassword);
		panelLogin.add(boxPassword);
		panelLogin.add(buttonLogin);
		
		
		// buttonLogin
		buttonLogin.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					loginUser();
				}
			}
		);
	}

	public JPanel getPanel() {
		return panelLogin;
	}
	
	private void loginUser() {
		if (fieldUsername.getText() == null || fieldUsername.getText().equals("")){
			textConsole.append("Username must not be blank \n");
			return;
		}
		if (fieldPassword.getText() == null || fieldPassword.getText().equals("")){
			textConsole.append("Password must not be blank \n");
			return;
		}
		if (!action.loginUser(fieldUsername.getText(), fieldPassword.getText())) {
			textConsole.append("Invaild login credentials \n");
			return;
		}
	}

}
