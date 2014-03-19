package edu.uci.ics.BoardGameServer.Action;

import edu.uci.ics.BoardGameServer.Board.GameObjectDefinitions;

public class Connect4Reactor extends ActionReactor{

	public Connect4Reactor(GameObjectFactory gof, BoardManipulator bm) {
		super(gof, bm);
	}

	public void updateBoard() {
		for(int i = 0; i < bm.getBoard().getWidth(); i++)
		{
			for(int j = 0; j < bm.getBoard().getHeight() - 1; j++)
			{
				if (bm.getBoard().getTile(i, j).getGameObjects().size() > 0)
				{
					if (bm.getBoard().getTile(i, j-1).getGameObjects().size() == 0)
					{
						//bm.moveGameObject(bm.getBoard().getTile(i, j).getGameObjects().get(0).getObjID(), bm.getBoard().getTile(i, j).getGameObjects().get(0).getOwner(), j-1, i, bm.getBoard().g, numberOfPlayers);
					}
				}
			}
		}
	}

}
