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

import bw.body.itfget.itfGetEatableArea;
import bw.body.itfget.itfGetInternalEnergyConsumption;
import bw.body.itfget.itfGetRadiation;
import bw.body.itfget.itfGetVision;
import bw.utils.config.clsBWProperties;
import enums.eEntityType;

//import tstBw.*;

/**
 * Host of the Bubble body and brain
 * 
 * @author langr
 * 
 */
public class clsBubble extends clsAnimate implements itfGetVision, itfGetEatableArea, itfGetRadiation {

	public static final String P_BUBBLE_RADIUS = "bubble_radius";

	public clsBubble(String poPrefix, clsBWProperties poProp) {
		super(poPrefix, poProp, 
		      new sim.physics2D.shape.Circle(poProp.getPropertyDouble(P_BUBBLE_RADIUS), 
		      new Color(poProp.getPropertyFloat(P_ENTITY_COLOR_R),
		    		    poProp.getPropertyFloat(P_ENTITY_COLOR_G),
		    		    poProp.getPropertyFloat(P_ENTITY_COLOR_B)) ) );
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);

		clsBWProperties oProp = new clsBWProperties();
		
		//TODO: (langr) - should pass the config to the decision unit!
		//oProp.putAll( clsDumbMindA.getDefaultProperties(pre) ); //clsDumbMindA.getDefaultProperties(pre)
		oProp.setProperty(pre+P_DECISION_TYPE, "DU_DUMB_MIND_A");
		
		oProp.setProperty(pre+P_BUBBLE_RADIUS, "10.0");
		oProp.setProperty(pre+P_ENTITY_COLOR_R, "0");
		oProp.setProperty(pre+P_ENTITY_COLOR_G, "200");
		oProp.setProperty(pre+P_ENTITY_COLOR_B, "0");
		
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
	public Object[] getSensorExternal() { return moBody.getExternalIO().moSensorExternal.values().toArray();}

	/* (non-Javadoc)
	 * @see bw.clsEntity#setEntityType()
	 */
	@Override
	protected void setEntityType() {
		meEntityType = eEntityType.BUBBLE;
		
	}
}
