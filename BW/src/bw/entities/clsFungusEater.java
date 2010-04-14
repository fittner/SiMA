/**
 * @author horvath
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import java.awt.Color;

import config.clsBWProperties;

import du.utils.enums.eDecisionType;

import bw.body.clsComplexBody;
import bw.body.itfGetExternalIO;
import bw.body.itfget.itfGetInternalEnergyConsumption;
import bw.body.itfget.itfGetRadiation;
import bw.body.itfget.itfGetSensorEngine;
import bw.entities.tools.clsShapeCreator;
import bw.entities.tools.eImagePositioning;
import bw.utils.enums.eBodyType;
import bw.utils.enums.eShapeType;
import enums.eEntityType;

//import tstBw.*;

/**
 * Host of the Fungus Eater body and brain
 * 
 * @author horvath
 * 
 */
public class clsFungusEater extends clsAnimate implements itfGetSensorEngine, itfGetRadiation {

	public clsFungusEater(String poPrefix, clsBWProperties poProp) {
		super(poPrefix, poProp);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);

		clsBWProperties oProp = new clsBWProperties();
		oProp.putAll( clsAnimate.getDefaultProperties(pre) );
		
		// remove whatever body has been assigned by getDefaultProperties
		oProp.removeKeysStartingWith(pre+clsAnimate.P_BODY);
		//add correct body
		oProp.putAll( clsComplexBody.getDefaultProperties(pre+P_BODY) );
		oProp.setProperty(pre+P_BODY_TYPE, eBodyType.COMPLEX.toString());
		
		//TODO: (langr) - should pass the config to the decision unit!
		//oProp.putAll( clsDumbMindA.getDefaultProperties(pre) ); //clsDumbMindA.getDefaultProperties(pre)
		oProp.setProperty(pre+P_DECISION_TYPE, eDecisionType.FUNGUS_EATER.name());
		
		oProp.setProperty(pre+P_SHAPE+"."+clsShapeCreator.P_DEFAULT_SHAPE, P_SHAPENAME);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShapeCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShapeCreator.P_RADIUS, 10.0);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShapeCreator.P_COLOR, new Color(0,200,0));
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShapeCreator.P_IMAGE_PATH, "/BW/src/resources/images/bubble_red.png");
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShapeCreator.P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());		
		
		oProp.setProperty(pre+P_STRUCTURALWEIGHT, 50.0);
		
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		// String pre = clsBWProperties.addDot(poPrefix);
		// nothing to do
	}

	// TODO: this code should be transferred to the entities inspector class - used only for inspectors
	public double getInternalEnergyConsuptionSUM() { return ((itfGetInternalEnergyConsumption)moBody).getInternalEnergyConsumption().getSum();	} 
	public Object[] getInternalEnergyConsumption() { return ((itfGetInternalEnergyConsumption)moBody).getInternalEnergyConsumption().getMergedList().values().toArray();	}
	public Object[] getSensorExternal() {
		if (moBody instanceof itfGetExternalIO) {
			return ((itfGetExternalIO)moBody).getExternalIO().moSensorEngine.getMeRegisteredSensors().values().toArray();
		} else {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see bw.clsEntity#setEntityType()
	 */
	@Override
	protected void setEntityType() {
		meEntityType = eEntityType.FUNGUS_EATER;
	}
}