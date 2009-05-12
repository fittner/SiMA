/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import java.awt.Color;
import java.awt.Paint;

import simple.dumbmind.clsDumbMindA;
import bw.actionresponses.clsBubbleResponses;
import bw.body.clsBaseBody;
import bw.body.clsComplexBody;
import bw.body.io.sensors.external.clsSensorEatableArea;
import bw.body.io.sensors.external.clsSensorVision;
import bw.body.itfget.itfGetInternalEnergyConsumption;
import ARSsim.physics2D.util.clsPose;
import bw.physicalObjects.sensors.clsEntityPartVision;
import bw.utils.container.clsConfigMap;
import enums.eEntityType;

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
	public clsBubble(int pnId, clsPose poStartingPose, sim.physics2D.util.Double2D poStartingVelocity, Paint poColor,  clsConfigMap poConfig)
    {
		super(
				pnId, 
				poStartingPose, 
				poStartingVelocity, 
				new sim.physics2D.shape.Circle(clsBubble.mrDefaultRadius, poColor), 
				clsBubble.mrDefaultWeight,
				getFinalConfig(poConfig)
				);
			
		applyConfig();
		
		setEntityActionResponse(new clsBubbleResponses());
		setDecisionUnit(new clsDumbMindA());
    } 

	public clsBaseBody createBody() {
		return  new clsComplexBody(this, moConfig);
	}
	
	private void applyConfig() {
		//TODO add ...

	}
	
	private static clsConfigMap getFinalConfig(clsConfigMap poConfig) {
		clsConfigMap oDefault = getDefaultConfig();
		oDefault.overwritewith(poConfig);
		return oDefault;
	}
	
	private static clsConfigMap getDefaultConfig() {
		clsConfigMap oDefault = new clsConfigMap();
		
		//TODO add ...
		
		return oDefault;
	}	
	
	// TODO: this code should be transferred to the entities inspector class - used only for inspectors
	public float getInternalEnergyConsuptionSUM() {	return ((itfGetInternalEnergyConsumption)moBody).getInternalEnergyConsumption().getSum();	} 
	public Object[] getInternalEnergyConsumption() { return ((itfGetInternalEnergyConsumption)moBody).getInternalEnergyConsumption().getMergedList().values().toArray();	}
	public Object[] getSensorExternal() { return moBody.getExternalIO().moSensorExternal.values().toArray();}




	/* (non-Javadoc)
	 * @see bw.clsEntity#setEntityType()
	 */
	@Override
	protected void setEntityType() {
		meEntityType = eEntityType.BUBBLE;
		
	}
	
	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.02.2009, 17:33:53
	 * 
	 * @see bw.entities.clsEntity#processing(java.util.ArrayList)
	 */
	@Override
	public void processing() {
		moActionList = moBody.getBrain().stepProcessing();
	}


	public clsEntityPartVision getVision()
	{
		return ((clsSensorVision)this.moBody
					.getExternalIO().moSensorExternal
					.get(enums.eSensorExtType.VISION)).getMoVisionArea(); 
	}
	
		
	public clsEntityPartVision getEatableAreaVision()
	{
		return ((clsSensorEatableArea)this.moBody
					.getExternalIO().moSensorExternal
					.get(enums.eSensorExtType.EATABLE_AREA)).getMoVisionArea(); 
	}




}
