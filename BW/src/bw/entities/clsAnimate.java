/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;


import java.awt.Color;
import java.util.ArrayList;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.physics2D.constraint.ConstraintEngine;
import sim.physics2D.forceGenerator.ForceGenerator;
import sim.physics2D.physicalObject.MobileObject2D;
import sim.physics2D.shape.Shape;
import sim.physics2D.util.Double2D;
import bw.body.clsAgentBody;

import bw.body.motionplatform.clsBrainAction;
import ARSsim.motionplatform.clsMotionPlatform;
import bw.sim.clsBWMain;


/**
 * Animates represents living objects that can e.g. move, grow, think.
 * 
 * @author langr
 * 
 */
public abstract class clsAnimate extends clsMobile{

	public clsAgentBody moAgentBody; // the instance of a body
	
	/**
	 * @param poStartingPosition
	 * @param poStartingVelocity
	 * @param pnId
	 */
	public clsAnimate(Double2D poStartingPosition, Double2D poStartingVelocity, Shape poShape, double poMass, int pnId) {
		super(poStartingPosition, poStartingVelocity, poShape, poMass, pnId);
		
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
		getAgentBody().stepSensing();
	}
	
	public void execution(ArrayList<clsBrainAction> poActionList) {
		getAgentBody().stepExecution(poActionList);
	}
}
