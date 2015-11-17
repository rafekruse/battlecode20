package battlecode.engine;

import battlecode.engine.instrumenter.IndividualClassLoader;
import battlecode.engine.instrumenter.InstrumentationException;
import battlecode.engine.scheduler.ScheduledRunnable;
import battlecode.server.Config;
import battlecode.world.RobotControllerImpl;

/*
TODO:
 - better error reporting
 */
public class PlayerFactory {

    private static boolean _debugMethodsEnabled = false;

    private PlayerFactory() {
    }

    public static void checkOptions() {
        Config options = Config.getGlobalConfig();
        _debugMethodsEnabled = options.getBoolean("bc.engine.debug-methods");
    }

    public static void loadPlayer(RobotControllerImpl rc, String teamName) {
        if (Config.getGlobalConfig().getBoolean("bc.engine.unit-test-mode")) {
            return;
        }

        // now, we instantiate and instrument the player's class
        Class playerClass;
        try {
            // The classloaders ignore silenced now - RobotMonitor takes care of it
            ClassLoader icl = new IndividualClassLoader(teamName, _debugMethodsEnabled, false, true);
            playerClass = icl.loadClass(teamName + ".RobotPlayer");
            //~ System.out.println("PF done loading");
        } catch (InstrumentationException ie) {
            // if we get an InstrumentationException, then the error should have been reported, so we just kill the robot
            System.out.println("[Engine] Error during instrumentation of " + rc.getRobot().toString() + ".\n[Engine] Robot will self-destruct in 3...2...1...");
            rc.getRobot().suicide();
            return;
        } catch (Exception e) {
            ErrorReporter.report(e);
            rc.getRobot().suicide();
            return;
        }

        // finally, create the player's thread, and let it loose
        new ScheduledRunnable(new RobotRunnable(playerClass, rc), rc.getRobot().getID());

    }
    
    public static void loadZombiePlayer(RobotControllerImpl rc) {
        if (Config.getGlobalConfig().getBoolean("bc.engine.unit-test-mode")) {
            return;
        }

        // instantiate and instrument the ZombiePlayer class
        Class playerClass;
        try {
            // The classloaders ignore silenced now - RobotMonitor takes care of it
            ClassLoader icl = new IndividualClassLoader("ZombiePlayer", _debugMethodsEnabled, false, true);
            playerClass = icl.loadClass("ZombiePlayer.ZombiePlayer");
            //~ System.out.println("PF done loading");
        } catch (InstrumentationException ie) {
            // if we get an InstrumentationException, then the error should have been reported, so we just kill the robot
            System.out.println("[Engine] Error during instrumentation of " + rc.getRobot().toString() + ".\n[Engine] Robot will self-destruct in 3...2...1...");
            rc.getRobot().suicide();
            return;
        } catch (Exception e) {
            ErrorReporter.report(e);
            rc.getRobot().suicide();
            return;
        }

        // finally, create the ZombiePlayer's thread, and let it loose
        new ScheduledRunnable(new RobotRunnable(playerClass, rc), rc.getRobot().getID());

    }
}
