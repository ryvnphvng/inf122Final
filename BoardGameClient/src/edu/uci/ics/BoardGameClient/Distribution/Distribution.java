package edu.uci.ics.BoardGameClient.Distribution;

import edu.uci.ics.BoardGameClient.Common.Definitions;
import edu.uci.ics.BoardGameClient.Common.Message;
import edu.uci.ics.BoardGameClient.Engine.TalkDistribution;

public class Distribution implements Runnable {

	private TalkDistribution talkDistribution;
	private volatile boolean stopRunning = false;
	private Client client;
	private Thread threadClient;

	public void setTalkDistribution(TalkDistribution talkDistribution) {
		this.talkDistribution = talkDistribution;
	}

	public void stop() {
		stopRunning = true;
	}

	@Override
	public void run() {

		client = new Client();
		client.setDistribution(this);

		threadClient = new Thread(client);
		threadClient.start();

		while (!stopRunning) {
			talkDistribution.waitForOutputQueue();
			messageToServer(talkDistribution.getOutputQueue());
		}

		client.stop();
		threadClient.interrupt();

	}

	public void createGame(int gameType) {
		if(gameType == 0)
			client.messageToServer("createGame " + Definitions.GAMETYPETICTACTOE);
		else if(gameType == 1)
			client.messageToServer("createGame " + Definitions.GAMETYPECONNECTFOUR);
		else if(gameType == Definitions.GAMETYPECHECKERS)
			client.messageToServer("createGame " + Definitions.GAMETYPECHECKERS);
	}

	public void messageFromServer(String data) {
		Message message = new Message();
		message.message = data;
		talkDistribution.messageFromServer(message);
	}

	private void messageToServer(Message message) {
		if (message == null) {
			return;
		}
		client.messageToServer(message.message);
	}

}
