package hardplayer.goal;

import battlecode.common.*;

import java.util.Random;

import hardplayer.ArchonPlayer;
import hardplayer.Static;

public class MakeArmyGoal extends Static implements Goal {

	private static RobotType typeToSpawn;
	private static int spawnRand;

	public MakeArmyGoal() {
		debug_startTiming();
		spawnRand = nextInt();
		debug_stopTiming();
	}

	public RobotType chooseTypeToSpawn() {
		try {
			MapLocation targetLoc = myLoc.add(myDir);
			if((enemies.size<0||enemyInfos[0].type==RobotType.TOWER)
				&&myRC.senseObjectAtLocation(targetLoc,RobotLevel.POWER_NODE)!=null
				&&myRC.senseObjectAtLocation(targetLoc,RobotLevel.ON_GROUND)==null)
			return RobotType.TOWER;
		} catch(Exception e) {
			debug_stackTrace(e);
		}
		switch(spawnRand%7) {
		case 0:
		case 1:
		case 2:
		case 3:
			return RobotType.SOLDIER;
		case 4:
			return RobotType.DISRUPTER;
		case 5:
			return RobotType.SCORCHER;
		default:
			if(alliedScouts.size<alliedArchons.size)
				return RobotType.SCOUT;
			else
				return RobotType.SOLDIER;
		}
	}

	public int maxPriority() {
		return MAKE_ARMY;
	}

	public int priority() {
		typeToSpawn = chooseTypeToSpawn();
		if(myRC.getFlux()>=typeToSpawn.spawnCost+ArchonPlayer.MIN_FLUX)
			return MAKE_ARMY;
		else
			return 0;
	}
	
	static public boolean canSpawn(RobotLevel level, Direction dir) throws GameActionException {
		MapLocation loc = myLoc.add(dir);
		return myRC.senseTerrainTile(loc).isTraversableAtHeight(level)&&(myRC.senseObjectAtLocation(loc,level)==null);
	}

	public void spawn() throws GameActionException {
		myRC.spawn(typeToSpawn);
		spawnRand = nextInt();
	}

	public void execute() {
		try {
			if(typeToSpawn==RobotType.TOWER) {
				Direction d=myLoc.directionTo(ArchonExploreGoal.target);
				if(d!=myDir) {
					moveAdjacentTo(ArchonExploreGoal.target);
					return;
				}
				if(canSpawn(typeToSpawn.level,myDir))
					spawn();
				return;
			}
			if(canSpawn(typeToSpawn.level,myDir))
				spawn();
			else {
				for(int i=7;i>=0;i--) {
					if(canSpawn(typeToSpawn.level,Direction.values()[i])) {
						myRC.setDirection(Direction.values()[i]);
						return;
					}
				}
				mySender.sendCrowded();
			}
		} catch(Exception e) {
			debug_stackTrace(e);
		}
	}

}
