package edu.uci.ics.BoardGameClient.Engine;

import edu.uci.ics.BoardGameClient.Action.Action;
import edu.uci.ics.BoardGameClient.Common.Message;

public class Game {

	private Action action = new Action();
	private TalkGame talkGame;

	public void setTalkGame(TalkGame talkGame) {
		this.talkGame = talkGame;
	}

	public void messageFromServer(Message message) {
		action.messageFromServer(message);
	}

	public void messageToServer(Message message) {
		talkGame.messageToServer(message);
	}

}
