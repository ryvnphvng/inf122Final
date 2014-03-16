package edu.uci.ics.BoardGameServer.Action;

import java.util.concurrent.atomic.AtomicInteger;

public class GameObjectIDGenerator {

	AtomicInteger idGen;
	
	GameObjectIDGenerator()
	{
		idGen = new AtomicInteger(0);
	}
	
	public int getNextID()
	{
		return idGen.getAndIncrement();
	}
}
