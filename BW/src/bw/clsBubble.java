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

	//members...
	private static final long serialVersionUID = -329155160020488088L;
	public float getInternalEnergyConsuptionSUM() {	return super.moAgentBody.getInternalStates().getInternalEnergyConsumption().getSum();	} 
	
	//just testing (cm)
	public Object[] getInternalEnergyConsumption() {	return moAgentBody.getInternalStates().getInternalEnergyConsumption().getList().values().toArray();	}
	
	//public HashMap<Integer, clsMutableFloat> getStomach() {	return moAgentBody.moInternalStates.moStomachSystem..getInternalEnergyConsumption().getList();	} 
	
	public Object[] getSensorExternal() { return moAgentBody.getExternalIO().moSensorExternal.toArray();}
	

	/**
	 * @param poStartingPosition
	 * @param poStartingVelocity
	 * @param pnId
	 */
	public clsBubble(Double2D poStartingPosition, Double2D poStartingVelocity,  int pnId)
    {
		super(poStartingVelocity, poStartingVelocity, pnId);
		
		setShape();
		
		//set the starting values
		this.setPose(poStartingPosition, new Angle(0));
	    this.setVelocity(poStartingVelocity);
    } 
	
	/**
	 * Set the shape for Mason representation
	 *
	 */
	private void setShape(){
		this.setShape(new sim.physics2D.shape.Circle(10, Color.GREEN), 300);
	}

	
	/* (non-Javadoc)
	 * @see sim.physics2D.forceGenerator.ForceGenerator#addForce()
	 */
	@Override
	public void addForce() {
		// TODO Auto-generated method stub
	}
	
	
	/* (non-Javadoc)
	 * @see bw.clsAnimate#step(sim.engine.SimState)
	 */
	public void step(SimState state)
    {
		super.step(state);
    }


}
