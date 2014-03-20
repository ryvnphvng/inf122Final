package edu.uci.ics.BoardGameServer.Action;

import org.json.simple.JSONObject;


public abstract class ActionReactor {
	GameObjectFactory gof;
	BoardManipulator bm;
	
	public ActionReactor(GameObjectFactory gof, BoardManipulator bm) {
		this.gof = gof;
		this.bm = bm;
	}

	public abstract void updateBoard(JSONObject o);
}
