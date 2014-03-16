package edu.uci.ics.BoardGameServer.Action;


public abstract class ActionReactor {
	GameObjectFactory gof;
	BoardManipulator bm;
	
	public ActionReactor(GameObjectFactory gof, BoardManipulator bm) {
		this.gof = gof;
		this.bm = bm;
	}

	public abstract void updateBoard();
}
