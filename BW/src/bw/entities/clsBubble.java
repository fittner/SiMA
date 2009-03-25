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
import sim.display.GUIState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;

import bw.actionresponses.clsBubbleResponses;
import bw.body.io.sensors.external.clsSensorEatableArea;
import bw.body.io.sensors.external.clsSensorVision;
import bw.body.motionplatform.clsBrainActionContainer;
import ARSsim.physics2D.util.clsPose;
import bw.physicalObjects.sensors.clsEntityPartVision;
import bw.utils.enums.eEntityType;
import bw.utils.inspectors.mind.clsDumbBrainInspector;

//import tstBw.*;

/**
 * Host of the Bubble body and brain
 * 
 * @author langr
 * 
 */
public class clsBubble extends clsAnimate {

	private static double mrDefaultWeight = 100.0f;
	private static double mrDefaultRadius = 10.0f;
	private static Color moDefaultColor = Color.green;


	/**
	 * TODO (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 26.02.2009, 11:29:23
	 *
	 * @param pnId
	 * @param poStartingPose
	 * @param poStartingVelocity
	 */
	public clsBubble(int pnId, clsPose poStartingPose, sim.physics2D.util.Double2D poStartingVelocity)
    {
		super(pnId, 
				poStartingPose, 
				poStartingVelocity, 
				new sim.physics2D.shape.Circle(clsBubble.mrDefaultRadius, clsBubble.moDefaultColor), 
				clsBubble.mrDefaultWeight
				);
		
		this.setEntityActionResponse(new clsBubbleResponses());
    } 
	
	// TODO: this code should be transfarred to the entities inspector class - used only for inspectors
	public float getInternalEnergyConsuptionSUM() {	return super.moAgentBody.getInternalSystem().getInternalEnergyConsumption().getSum();	} 
	public Object[] getInternalEnergyConsumption() {	return moAgentBody.getInternalSystem().getInternalEnergyConsumption().getList().values().toArray();	}
	public Object[] getSensorExternal() { return moAgentBody.getExternalIO().moSensorExternal.values().toArray();}




	/* (non-Javadoc)
	 * @see bw.clsEntity#setEntityType()
	 */
	@Override
	protected void setEntityType() {
		meEntityType = eEntityType.BUBBLE;
		
	}
	
	/* (non-Javadoc)
	 * @see bw.clsEntity#sensing()
	 */
	@Override
	public void sensing() {
		super.sensing();
	}	

	/* (non-Javadoc)
	 * @see bw.clsEntity#execution(java.util.ArrayList)
	 */
	@Override
	public void execution(clsBrainActionContainer poActionList) {
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
	public void processing(clsBrainActionContainer poActionList) {

		this.getAgentBody().getBrain().stepProcessing((clsAnimate)this, poActionList);

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

	public clsEntityPartVision getVision()
	{
		return ((clsSensorVision)this.moAgentBody
					.getExternalIO().moSensorExternal
					.get(bw.utils.enums.eSensorExtType.VISION)).getMoVisionArea(); 
	}
	
		
	public clsEntityPartVision getEatableAreaVision()
	{
		return ((clsSensorEatableArea)this.moAgentBody
					.getExternalIO().moSensorExternal
					.get(bw.utils.enums.eSensorExtType.EATABLE_AREA)).getMoVisionArea(); 
	}
	
}
