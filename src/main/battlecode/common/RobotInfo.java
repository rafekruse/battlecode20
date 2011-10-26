package battlecode.common;

/**
 * Struct that stores basic information that was 'sensed' of another Robot. This
 * info is ephemeral and there is no guarantee any of it will remain the same
 * between rounds.
 * 
 * @author Teh Devs
 */
public class RobotInfo {

    /** The robot that was sensed. */
    public final Robot robot;
    /** Location of this Robot. */
    public final MapLocation location;
    /** Hit points of this Robot. */
    public final double energon;
    /** Maximum hit points of this Robot. */
    public final double maxEnergon;
	public final double flux;
    /** The direction this Robot is facing. */
    public final Direction direction;
    public final RobotType type;
	public final Team team;
	/** True if this robot is scheduled to regenerate
	 * at the beginning of its next turn. */
   	public boolean regen;

    public RobotInfo(Robot robot, MapLocation location,
            double hitpoints, double maxHp, double flux, Direction direction,
            RobotType type, Team team, boolean regen) {
        super();
        this.robot = robot;
        this.location = location;
        this.energon = hitpoints;
        this.maxEnergon = maxHp;
		this.flux = flux;
        this.direction = direction;
        this.type = type;
		this.team = team;
		this.regen = regen;
    }

    public int hashCode() {
        return robot.hashCode();
    }
}
