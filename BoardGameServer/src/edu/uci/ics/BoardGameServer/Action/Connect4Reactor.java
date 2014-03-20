package edu.uci.ics.BoardGameServer.Action;

import edu.uci.ics.BoardGameServer.Board.GameObjectDefinitions;

public class Connect4Reactor extends ActionReactor{

	public Connect4Reactor(GameObjectFactory gof, BoardManipulator bm) {
		super(gof, bm);
	}

	public void updateBoard() {
		for(int i = 0; i < bm.getBoard().getHeight(); i++)
		{
			for(int j = 0; j < bm.getBoard().getWidth(); j++)
			{
				if (bm.getBoard().getTile(i, j).getGameObjects().size() > 0)
				{
					if (bm.getBoard().getTile(i, j).getGameObjects().size() == 0)
					{
						//bm.moveGameObject(bm.getBoard().getTile(i, j).getGameObjects().get(0).getObjID(), bm.getBoard().getTile(i, j).getGameObjects().get(0).getOwner(), j-1, i, bm.getBoard().g, numberOfPlayers);
					}
				}
			}
		}
	}

}
