package edu.uci.ics.BoardGameServer.Action;


public abstract class ActionReactor {
	Instantiator instantiator;
	BoardManipulator bm;
	
	public ActionReactor(Instantiator instantiator, BoardManipulator bm) {
		this.instantiator = instantiator;
		this.bm = bm;
	}

	public abstract void updateBoard();
}
