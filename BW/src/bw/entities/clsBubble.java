/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import java.awt.Color;

import config.clsBWProperties;

import du.enums.eActionKissIntensity;
import du.enums.eEntityType;
import du.itf.itfDecisionUnit;
import bw.body.clsComplexBody;
import bw.body.itfGetExternalIO;
import bw.body.attributes.clsAttributeAntenna;
import bw.body.attributes.clsAttributeEye;
import bw.body.attributes.clsAttributes;
import bw.body.itfget.itfGetInternalEnergyConsumption;
import bw.body.itfget.itfGetRadiation;
import bw.body.itfget.itfGetSensorEngine;
import bw.entities.tools.clsShapeCreator;
import bw.entities.tools.eImagePositioning;
import bw.utils.enums.eBodyAttributes;
import bw.utils.enums.eBodyType;
import bw.utils.enums.eShapeType;
import bw.body.io.clsExternalIO;
import bw.body.io.actuators.actionProxies.itfAPKissable;

//import tstBw.*;

/**
 * Host of the Bubble body and brain
 * 
 * @author langr
 * 
 */
public class clsBubble extends clsAnimate implements itfGetSensorEngine, itfGetRadiation, itfAPKissable {

	public clsBubble(itfDecisionUnit poDU, String poPrefix, clsBWProperties poProp) {
		super(poDU, poPrefix, poProp);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);

		clsBWProperties oProp = new clsBWProperties();
		oProp.putAll( clsAnimate.getDefaultProperties(pre) );
		
		// remove whatever body has been assigned by getDefaultProperties
		oProp.removeKeysStartingWith(pre+clsEntity.P_BODY);
		//add correct body
		oProp.putAll( clsComplexBody.getDefaultProperties(pre+P_BODY) );
		oProp.setProperty(pre+P_BODY_TYPE, eBodyType.COMPLEX.toString());
		
		//add correct default sensor values (three range vision)
		oProp.removeKeysStartingWith(pre+clsEntity.P_BODY+"."+clsComplexBody.P_EXTERNALIO+"."+clsExternalIO.P_SENSORS);
		oProp.putAll( clsExternalIO.getDefaultSensorProperties(pre+clsEntity.P_BODY+"."+clsComplexBody.P_EXTERNALIO+"."+clsExternalIO.P_SENSORS, true));

	
		oProp.setProperty(pre+P_SHAPE+"."+clsShapeCreator.P_DEFAULT_SHAPE, P_SHAPENAME);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShapeCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShapeCreator.P_RADIUS, 10.0);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShapeCreator.P_COLOR, new Color(0,200,0));
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShapeCreator.P_IMAGE_PATH, "/BW/src/resources/images/bubble_red.png");
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShapeCreator.P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());		
		
		oProp.setProperty(pre+P_STRUCTURALWEIGHT, 50.0);
		
		//add attributes
		String att_pre = pre+P_BODY+"."+clsComplexBody.P_ATTRIBUTES+".";
		int num = oProp.getPropertyInt(att_pre+clsAttributes.P_NUMATTRIBUTES);
		
		oProp.putAll( clsAttributeAntenna.getDefaultProperties( att_pre+num) );
		oProp.setProperty(att_pre+num+"."+clsAttributes.P_ATTRIBUTETYPE, eBodyAttributes.ANTENNA_LEFT.name());
		oProp.setProperty(att_pre+num+"."+clsAttributes.P_ATTRIBUTEACTIVE, true);
		num++;

		oProp.putAll( clsAttributeAntenna.getDefaultProperties( att_pre+num) );
		oProp.setProperty(att_pre+num+"."+clsAttributes.P_ATTRIBUTETYPE, eBodyAttributes.ANTENNA_RIGHT.name());
		oProp.setProperty(att_pre+num+"."+clsAttributes.P_ATTRIBUTEACTIVE, true);
		num++;

		oProp.putAll( clsAttributeEye.getDefaultProperties( att_pre+num) );
		oProp.setProperty(att_pre+num+"."+clsAttributes.P_ATTRIBUTETYPE, eBodyAttributes.EYE.name());
		oProp.setProperty(att_pre+num+"."+clsAttributes.P_ATTRIBUTEACTIVE, true);
		num++;
		
		oProp.setProperty(att_pre+clsAttributes.P_NUMATTRIBUTES, num);		
		
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
		meEntityType = eEntityType.BUBBLE;
	}
	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.02.2009, 17:36:09
	 * 
	 * @see bw.entities.clsEntity#processing(java.util.ArrayList)
	 */
	@Override
	public void processing() {

//	    ((clsRemoteControl)(moBody.getBrain().getDecisionUnit())).setKeyPressed(clsKeyListener.getKeyPressed());		
		super.processing();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @author Benny Dönz
	 * 28.08.2009, 18:16:17
	 * 
	 * @see bw.body.io.actuators.actionProxies.itfAPKissable#tryKiss(enums.eActionKissIntensity)
	 */
	public boolean tryKiss(eActionKissIntensity peIntensity) {
		return true;
	}
	public void kiss(eActionKissIntensity peIntensity) {
		double rIntensity=1;
		if (peIntensity==eActionKissIntensity.MIDDLE) rIntensity=2;
		if (peIntensity==eActionKissIntensity.STRONG) rIntensity=4;

		((clsComplexBody) this.getBody()).getInterBodyWorldSystem().getEffectKiss().kiss(null, rIntensity);
	}
	
}