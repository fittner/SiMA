package ARSsim.physics2D.util;

import java.text.NumberFormat;

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
	 * TODO (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 24.06.2009, 11:38:42
	 *
	 * @param pose
	 */
	public clsPose(clsPose pose) {
		setPosition(pose.moPosition);
		setAngle(pose.moAngle);
	}

	public void setPose(clsPose pose) {
		setPosition(pose.moPosition);
		setAngle(pose.moAngle);		
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

	public void subtract(clsPose other) {
		moPosition = moPosition.subtract(other.moPosition);
		moAngle = moAngle.add(-other.moAngle.radians);
	}
	/**
	 * @return the moAngle
	 */
	public sim.physics2D.util.Angle getAngle() {
		return moAngle;
	}
	
	public String toString() {
	    NumberFormat nf = java.text.NumberFormat.getInstance( );
	    nf.setMinimumFractionDigits( 1 );
	    nf.setMaximumFractionDigits( 3 );
		return "[x:"+nf.format(moPosition.x)+"/y:"+nf.format(moPosition.y)+"|a:"+nf.format(moAngle.radians)+"]";
	}
}
