package ARSsim.physics2D.util;

import sim.physics2D.util.Angle;
import sim.physics2D.util.Double2D;

/**
 * @author deutsch
 *
 */
public class clsPose {
	/**
	 * 
	 */
	private sim.physics2D.util.Double2D moPosition;
	/**
	 * 
	 */
	private sim.physics2D.util.Angle moAngle;
	
	/**
	 * @param prX
	 * @param prY
	 * @param prAngle
	 */
	public clsPose(double prX, double prY, double prAngle) {
		setAngle(new Angle(prAngle));
		setPosition(new Double2D(prX, prY));
	}
	
	/**
	 * @param poPosition
	 * @param poAngle
	 */
	public clsPose(sim.physics2D.util.Double2D poPosition, sim.physics2D.util.Angle poAngle) {
		setAngle(poAngle);
		setPosition(poPosition);
	}

	/**
	 * @param moPosition the moPosition to set
	 */
	public void setPosition(sim.physics2D.util.Double2D moPosition) {
		this.moPosition = moPosition;
	}

	/**
	 * @return the moPosition
	 */
	public sim.physics2D.util.Double2D getPosition() {
		return moPosition;
	}

	/**
	 * @param moAngle the moAngle to set
	 */
	public void setAngle(sim.physics2D.util.Angle moAngle) {
		this.moAngle = moAngle;
	}

	/**
	 * @return the moAngle
	 */
	public sim.physics2D.util.Angle getAngle() {
		return moAngle;
	}
}
