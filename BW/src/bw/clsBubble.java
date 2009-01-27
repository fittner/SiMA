/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw;

import java.awt.Color;
import java.util.HashMap;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.physics2D.constraint.ConstraintEngine;
import sim.physics2D.forceGenerator.ForceGenerator;
import sim.physics2D.util.Angle;
import sim.physics2D.util.Double2D;
import sim.portrayal.DrawInfo2D;
import bw.body.itfStep;
import bw.body.physicalObject.mobile.clsMotionPlatform;
import bw.sim.clsBWMain;
import bw.utils.datatypes.clsMutableFloat;

//import tstBw.*;

/**
 * Host of the Bubble body and brain
 * 
 * @author langr
 * 
 */
public class clsBubble extends clsAnimate {

	
	private static final long serialVersionUID = -329155160020488088L;


	public float getInternalEnergyConsuptionSUM() {	return super.moAgentBody.getInternalStates().getInternalEnergyConsumption().getSum();	} 
	
	//just testing (cm)
	public HashMap<Integer, clsMutableFloat> getInternalEnergyConsumption() {	return moAgentBody.getInternalStates().getInternalEnergyConsumption().getList();	}
	
	//public HashMap<Integer, clsMutableFloat> getStomach() {	return moAgentBody.moInternalStates.moStomachSystem..getInternalEnergyConsumption().getList();	} 
	
	

	public clsBubble(Double2D poStartingPosition, Double2D poStartingVelocity,  int pnId)
    {
		super(poStartingVelocity, poStartingVelocity, pnId);
		
		this.setPose(poStartingPosition, new Angle(0));
	    this.setVelocity(poStartingVelocity);
    } 

	
	/* (non-Javadoc)
	 * @see sim.physics2D.forceGenerator.ForceGenerator#addForce()
	 */
	@Override
	public void addForce() {
		// TODO Auto-generated method stub
		
	}
	
	public void step(SimState state)
    {
		super.step(state);
    }


}
