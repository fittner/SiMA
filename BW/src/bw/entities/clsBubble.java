/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import java.awt.Color;

import du.utils.enums.eDecisionType;

import bw.body.clsComplexBody;
import bw.body.itfGetExternalIO;
import bw.body.itfget.itfGetEatableArea;
import bw.body.itfget.itfGetInternalEnergyConsumption;
import bw.body.itfget.itfGetRadiation;
import bw.body.itfget.itfGetVision;
import bw.utils.config.clsBWProperties;
import bw.utils.enums.eBodyType;
import bw.utils.enums.eShapeType;
import enums.eEntityType;

//import tstBw.*;

/**
 * Host of the Bubble body and brain
 * 
 * @author langr
 * 
 */
public class clsBubble extends clsAnimate implements itfGetVision, itfGetEatableArea, itfGetRadiation {

	public clsBubble(String poPrefix, clsBWProperties poProp) {
		super(poPrefix, poProp);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);

		clsBWProperties oProp = new clsBWProperties();
		oProp.putAll( clsAnimate.getDefaultProperties(pre) );
		
		oProp.removeKeysStartingWith(pre+clsAnimate.P_BODY);
		
		oProp.putAll( clsComplexBody.getDefaultProperties(pre+P_BODY) );
		oProp.setProperty(pre+P_BODY_TYPE, eBodyType.COMPLEX.toString());
		
		//TODO: (langr) - should pass the config to the decision unit!
		//oProp.putAll( clsDumbMindA.getDefaultProperties(pre) ); //clsDumbMindA.getDefaultProperties(pre)
		oProp.setProperty(pre+P_DECISION_TYPE, eDecisionType.DUMB_MIND_A.name());
		
		oProp.setProperty(pre+P_SHAPE_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE_RADIUS, "10.0");
		oProp.setProperty(pre+P_ENTITY_COLOR_RGB, new Color(0,200,0));

		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
		moDecisionType = eDecisionType.valueOf( poProp.getPropertyString(pre+P_DECISION_TYPE) );
		//create the defined decision unit...
		setDecisionUnit(moDecisionType);
	}

	// TODO: this code should be transferred to the entities inspector class - used only for inspectors
	public double getInternalEnergyConsuptionSUM() {	return ((itfGetInternalEnergyConsumption)moBody).getInternalEnergyConsumption().getSum();	} 
	public Object[] getInternalEnergyConsumption() { return ((itfGetInternalEnergyConsumption)moBody).getInternalEnergyConsumption().getMergedList().values().toArray();	}
	public Object[] getSensorExternal() {
		if (moBody instanceof itfGetExternalIO) {
			return ((itfGetExternalIO)moBody).getExternalIO().moSensorExternal.values().toArray();
		} else {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see bw.clsEntity#setEntityType()
	 */
	@Override
	protected void setEntityType() {
		meEntityType = eEntityType.BUBBLE;
		
	}
}
