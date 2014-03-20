package edu.uci.ics.BoardGameServer.Action;

import org.json.simple.JSONObject;

public class TicTacToeReactor extends ActionReactor{

	public TicTacToeReactor(GameObjectFactory gof, BoardManipulator bm) {
		super(gof, bm);
	}

	public void updateBoard(JSONObject o) {
		return; // There are no reactions that take place in TicTacToe
	}

}
