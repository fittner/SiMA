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

import sim.physics2D.util.Angle;
import simple.dumbmind.clsDumbMindA;
import simple.dumbmind.clsDumbMindOrientationTest;
import bw.actionresponses.clsBubbleResponses;
import bw.body.clsBaseBody;
import bw.body.clsComplexBody;
import bw.body.io.sensors.external.clsSensorEatableArea;
import bw.body.io.sensors.external.clsSensorVision;
import bw.body.itfget.itfGetEatableArea;
import bw.body.itfget.itfGetInternalEnergyConsumption;
import bw.body.itfget.itfGetVision;
import bw.factories.clsSingletonMasonGetter;
import ARSsim.physics2D.util.clsPose;
import bw.physicalObjects.bodyparts.clsBotHands;
import bw.physicalObjects.sensors.clsEntityPartVision;
import bw.utils.container.clsConfigMap;
import bw.utils.enums.eConfigEntries;
import enums.eEntityType;

//import tstBw.*;

/**
 * Host of the Bubble body and brain
 * 
 * @author langr
 * 
 */
public class clsBubble extends clsAnimate implements itfGetVision, itfGetEatableArea {

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
		//setDecisionUnit(new clsDumbMindA());
		setDecisionUnit(new clsDumbMindOrientationTest( this ));
    } 

	public clsBaseBody createBody() {
		return  new clsComplexBody(this, (clsConfigMap)moConfig.get(eConfigEntries.BODY));
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
	

	public clsEntityPartVision getVision()
	{
		return ((clsSensorVision)moBody
					.getExternalIO().moSensorExternal
					.get(enums.eSensorExtType.VISION)).getMoVisionArea(); 
	}
}
