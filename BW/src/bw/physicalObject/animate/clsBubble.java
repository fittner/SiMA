/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.physicalObject.animate;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.physics2D.constraint.ConstraintEngine;
import sim.physics2D.forceGenerator.ForceGenerator;
import sim.physics2D.util.Angle;
import sim.physics2D.util.Double2D;
import sim.portrayal.DrawInfo2D;
import bw.actionresponses.clsBubbleResponses;
import bw.body.itfStep;
import bw.body.motionplatform.clsBrainAction;
import ARSsim.motionplatform.clsMotionPlatform;
import ARSsim.physics2D.physicalObject.clsMobileObject2D;
import bw.sim.clsBWMain;
import bw.utils.datatypes.clsMutableFloat;
import bw.utils.enums.eEntityType;

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
	public float getInternalEnergyConsuptionSUM() {	return super.moAgentBody.getInternalSystem().getInternalEnergyConsumption().getSum();	} 
	
	//just testing (cm)
	public Object[] getInternalEnergyConsumption() {	return moAgentBody.getInternalSystem().getInternalEnergyConsumption().getList().values().toArray();	}
	
	//public HashMap<Integer, clsMutableFloat> getStomach() {	return moAgentBody.moInternalStates.moStomachSystem..getInternalEnergyConsumption().getList();	} 
	
	public Object[] getSensorExternal() { return moAgentBody.getExternalIO().moSensorExternal.toArray();}
	

	/**
	 * @param poStartingPosition
	 * @param poStartingVelocity
	 * @param pnId
	 */
	public clsBubble(Double2D poStartingPosition, Double2D poStartingVelocity,  int pnId)
    {
		super(poStartingVelocity, poStartingVelocity, new sim.physics2D.shape.Circle(10, Color.CYAN), pnId);
		
		this.setEntityActionResponse(new clsBubbleResponses());
		
		setShape();
		
		//set the starting values
		clsMobileObject2D oMobile = getMobile();
		oMobile.setPose(poStartingPosition, new Angle(0));
		oMobile.setVelocity(poStartingVelocity);
    } 
	
	/**
	 * Set the shape for Mason representation
	 *
	 */
	private void setShape(){
		getMobile().setShape(new sim.physics2D.shape.Circle(10, Color.GREEN), 300);
	}

	/* (non-Javadoc)
	 * @see bw.clsEntity#getEntityType()
	 */
	@Override
	public eEntityType getEntityType() {
		return eEntityType.BUBBLE;
	}


	/* (non-Javadoc)
	 * @see bw.clsEntity#sensing()
	 */
	@Override
	public void sensing() {
		super.sensing();
	}

	/* (non-Javadoc)
	 * @see bw.clsEntity#setEntityType()
	 */
	@Override
	protected void setEntityType() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see bw.clsEntity#thinking()
	 */
	@Override
	public void thinking() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see bw.clsEntity#execution(java.util.ArrayList)
	 */
	@Override
	public void execution(ArrayList<clsBrainAction> poActionList) {
		// TODO Auto-generated method stub
		
	}



}
