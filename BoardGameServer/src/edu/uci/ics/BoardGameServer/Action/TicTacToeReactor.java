package edu.uci.ics.BoardGameServer.Action;

public class TicTacToeReactor extends ActionReactor{

	public TicTacToeReactor(GameObjectFactory gof, BoardManipulator bm) {
		super(gof, bm);
	}

	public void updateBoard() {
		return; // There are no reactions that take place in TicTacToe
	}

}
