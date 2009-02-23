/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import java.awt.Color;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.physics2D.constraint.ConstraintEngine;
import sim.physics2D.forceGenerator.ForceGenerator;
import sim.physics2D.physicalObject.MobileObject2D;
import sim.physics2D.shape.Shape;
import sim.physics2D.util.Angle;
import sim.physics2D.util.Double2D;
import sim.portrayal.DrawInfo2D;
import tstBw.body.*;
import bw.clsEntity;
import bw.body.clsAgentBody;
import ARSsim.motionplatform.clsMotionPlatform;
import bw.sim.clsBWMain;


/**
 * Animates represents living objects that can e.g. move, grow, think.
 * 
 * @author langr
 * 
 */
public abstract class clsAnimate extends clsMobile{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1734482865454985954L;
	private int mnId;
	
	public int getId() {	return mnId;	}
	public void setId(int pnId) {		this.mnId = pnId;	}

	public clsAgentBody moAgentBody; // the instance of a body
	private clsMotionPlatform moMotionPlatform; //motion platform for user steering

	
	/**
	 * @param poStartingPosition
	 * @param poStartingVelocity
	 * @param pnId
	 */
	public clsAnimate(Double2D poStartingPosition, Double2D poStartingVelocity, Shape poShape, int pnId) {
		super(poStartingPosition, poStartingVelocity, poShape, 300);
		
		MobileObject2D oMobile = getMobile();
		moMotionPlatform = new clsMotionPlatform(oMobile);
		moAgentBody = new clsAgentBody(this);
	}
	
	
	/**
	 * @author langr
	 * 20.02.2009, 11:40:14
	 * 
	 * @return the moAgentBody
	 */
	public clsAgentBody getAgentBody() {
		return moAgentBody;
	}
	
	public void sensing() {
		getAgentBody().step();
	}
}
