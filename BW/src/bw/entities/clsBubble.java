/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import java.awt.Paint;
import simple.dumbmind.clsDumbMindA;
import bw.body.clsBaseBody;
import bw.body.clsComplexBody;
import bw.body.itfget.itfGetEatableArea;
import bw.body.itfget.itfGetInternalEnergyConsumption;
import bw.body.itfget.itfGetRadiation;
import bw.body.itfget.itfGetVision;
import ARSsim.physics2D.util.clsPose;
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
public class clsBubble extends clsAnimate implements itfGetVision, itfGetEatableArea, itfGetRadiation {

	private static double mrDefaultWeight = 100.0f;
	private static double mrDefaultRadius = 10.0f;


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
		
		setDecisionUnit(new clsDumbMindA());
    } 

	@Override
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
	public double getInternalEnergyConsuptionSUM() {	return ((itfGetInternalEnergyConsumption)moBody).getInternalEnergyConsumption().getSum();	} 
	public Object[] getInternalEnergyConsumption() { return ((itfGetInternalEnergyConsumption)moBody).getInternalEnergyConsumption().getMergedList().values().toArray();	}
	public Object[] getSensorExternal() { return moBody.getExternalIO().moSensorExternal.values().toArray();}




	/* (non-Javadoc)
	 * @see bw.clsEntity#setEntityType()
	 */
	@Override
	protected void setEntityType() {
		meEntityType = eEntityType.BUBBLE;
		
	}
	








}
