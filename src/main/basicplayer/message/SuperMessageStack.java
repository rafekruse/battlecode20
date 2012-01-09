package basicplayer.message;

import battlecode.common.Message;

public class SuperMessageStack implements MessageHandler {

	static public final int KEEP_TIME = 2;

	static public int t;
	public Message [][] messages = new Message [][] { new Message [100], new Message[100], new Message[100] };
	public int [] lengths = new int [KEEP_TIME];

	public SuperMessageStack() { }

	public void receivedMessage(Message m) {
		/*
		if(m.ints[0]==messageTypeIShotThat) {
			basicplayer.players.BasePlayer.debug_println("I heard that a cannon shot: ");
			basicplayer.goal.debug_printInt(m.ints[1]);
		}
		*/
		messages[t][lengths[t]++]=m;
	}

}
