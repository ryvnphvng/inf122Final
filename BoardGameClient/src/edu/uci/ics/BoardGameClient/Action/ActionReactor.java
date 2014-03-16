package edu.uci.ics.BoardGameClient.Action;
import edu.uci.ics.BoardGameClient.Action.BoardManipulator;
import edu.uci.ics.BoardGameClient.Action.GameObjectFactory;


public abstract class ActionReactor {
	GameObjectFactory gof;
	BoardManipulator bm;
	
	public ActionReactor(GameObjectFactory gof, BoardManipulator bm) {
		this.gof = gof;
		this.bm = bm;
	}

	public abstract void updateBoard();
}

