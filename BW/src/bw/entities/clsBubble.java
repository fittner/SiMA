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

import sim.physics2D.util.Angle;
import sim.physics2D.util.Double2D;
import bw.actionresponses.clsBubbleResponses;
import bw.body.motionplatform.clsBrainAction;
import ARSsim.physics2D.physicalObject.clsMobileObject2D;
import bw.utils.enums.eEntityType;

//import tstBw.*;

/**
 * Host of the Bubble body and brain
 * 
 * @author langr
 * 
 */
public class clsBubble extends clsAnimate {



	/**
	 * @param poStartingPosition
	 * @param poStartingVelocity
	 * @param pnId
	 */
	public clsBubble(Double2D poStartingPosition, Double2D poStartingVelocity,  int pnId)
    {
		super(poStartingVelocity, poStartingVelocity, new sim.physics2D.shape.Circle(10, Color.CYAN), 300, pnId);
		
		this.setEntityActionResponse(new clsBubbleResponses());
		
		setShape(new sim.physics2D.shape.Circle(10, Color.GREEN));
		setMass(300.0f);
		
		clsMobileObject2D oMobileObject2D = getMobileObject2D();
		
		oMobileObject2D.setPose(getPosition(), new Angle(0));
		oMobileObject2D.setVelocity(getVelocity());
		oMobileObject2D.setShape(getShape(), getMass());
		
    } 
	
	public float getInternalEnergyConsuptionSUM() {	return super.moAgentBody.getInternalSystem().getInternalEnergyConsumption().getSum();	} 
	
	//just testing (cm)
	public Object[] getInternalEnergyConsumption() {	return moAgentBody.getInternalSystem().getInternalEnergyConsumption().getList().values().toArray();	}
	
	
	public Object[] getSensorExternal() { return moAgentBody.getExternalIO().moSensorExternal.toArray();}
	


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
	 * @see bw.clsEntity#execution(java.util.ArrayList)
	 */
	@Override
	public void execution(ArrayList<clsBrainAction> poActionList) {
		super.execution(poActionList);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.02.2009, 17:33:53
	 * 
	 * @see bw.entities.clsEntity#processing(java.util.ArrayList)
	 */
	@Override
	public void processing(ArrayList<clsBrainAction> poActionList) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.02.2009, 17:33:53
	 * 
	 * @see bw.entities.clsEntity#updateInternalState()
	 */
	@Override
	public void updateInternalState() {
		// TODO Auto-generated method stub
		
	}



}
