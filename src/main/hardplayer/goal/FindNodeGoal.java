package hardplayer.goal;

import hardplayer.Static;
import hardplayer.message.MessageHandler;

import battlecode.common.Clock;
import battlecode.common.MapLocation;
import battlecode.common.Message;

public class FindNodeGoal extends Static implements Goal, MessageHandler {

	static MapLocation requestLoc;
	static int requestTime;
	static int archonDist;

	public int maxPriority() { return FIND_NODE; }

	public int priority() {
		if(Clock.getRoundNum()-requestTime<=10)
			return FIND_NODE;
		else
			return 0;
	}

	public void execute() {
		myNav.moveToForward(requestLoc);
	}

	public void receivedMessage(Message m) {
		//System.out.println("got message");
		int d = myLoc.distanceSquaredTo(m.locations[0]);
		if(d>=archonDist&&Clock.getRoundNum()<=requestTime)
			return;
		requestLoc = m.locations[0].add(m.locations[0].directionTo(m.locations[1]),4);
		requestTime = Clock.getRoundNum();
		archonDist = d;
	}

}
