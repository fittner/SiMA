/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.physicalObject.inanimate;

import java.awt.Color;

import sim.physics2D.physicalObject.MobileObject2D;
import sim.physics2D.util.Angle;
import sim.physics2D.util.Double2D;
import ARSsim.robot2D.clsMotionPlatform;
import bw.clsEntity;
import bw.body.clsAgentBody;
import bw.physicalObject.inanimate.mobile.clsMobile;


/**
 * Inanimates represent dead objects (cannot grow, move, ...)
 * 
 * @author langr
 * 
 */
public abstract class clsInanimate extends clsMobile {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8003531210919357291L;
	private int mnId;
	
	public int getId() {	return mnId;	}
	public void setId(int pnId) {		this.mnId = pnId;	}
	
	/**
	 * Whe u use this constructor use caution!!!!
	 * u need to implement the things from clsMobileObject2D yourself
	 */
	public clsInanimate(int pnId){
		super();
		mnId = pnId;
	}

	/**
	 * @param poStartingPosition
	 * @param poStartingVelocity
	 * @param pnId
	 */
	public clsInanimate(Double2D poStartingPosition, Double2D poStartingVelocity, sim.physics2D.shape.Shape poShape,  int pnId) {
		super(poStartingPosition, poStartingVelocity, poShape, 300);

	}
	
}
