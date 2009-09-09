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
import enums.eActionKissIntensity;
import enums.eEntityType;
import enums.eSensorExtType;
import bw.body.io.clsExternalIO;
import bw.body.io.actuators.actionProxies.itfAPKissable;
import bw.body.io.sensors.ext.clsSensorVision;

//import tstBw.*;

/**
 * Host of the Bubble body and brain
 * 
 * @author langr
 * 
 */
public class clsBubble extends clsAnimate implements itfGetSensorEngine, itfGetRadiation, itfAPKissable {

	public clsBubble(String poPrefix, clsBWProperties poProp) {
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
		
		clsAddThreeRangeVision(pre, oProp);

		//TODO: (langr) - should pass the config to the decision unit!
		//oProp.putAll( clsDumbMindA.getDefaultProperties(pre) ); //clsDumbMindA.getDefaultProperties(pre)
		oProp.setProperty(pre+P_DECISION_TYPE, eDecisionType.PA.name());
		oProp.putAll( pa.clsPsychoAnalysis.getDefaultProperties(pre+P_DU_PROPERTIES) );
		
		oProp.setProperty(pre+P_SHAPE+"."+clsShapeCreator.P_DEFAULT_SHAPE, P_SHAPENAME);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShapeCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShapeCreator.P_RADIUS, 10.0);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShapeCreator.P_COLOR, new Color(0,200,0));
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShapeCreator.P_IMAGE_PATH, "/BW/src/resources/images/bubble_red.png");
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShapeCreator.P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());		
		
		oProp.setProperty(pre+P_STRUCTURALWEIGHT, 50.0);
		
		return oProp;
	}

	/**
	 * DOCUMENT (langr) - insert description
	 *
	 * @author langr
	 * 09.09.2009, 10:08:50
	 *
	 * @param poPre
	 * @param poProp
	 */
	private static void clsAddThreeRangeVision(String poPre,
			clsBWProperties poProp) {
		// removes standard vision from complex body and adds the 3-range-vision
		poProp.setProperty(poPre+clsAnimate.P_BODY+"."+clsComplexBody.P_SENSORSEXT+"."+"2."+clsExternalIO.P_SENSORACTIVE, false);
		poProp.setProperty(poPre+clsAnimate.P_BODY+"."+clsComplexBody.P_SENSORSEXT+"."+clsExternalIO.P_NUMSENSORS, 9);
		//add 3-range-vision
		poProp.putAll( clsSensorVision.getDefaultProperties( poPre+clsAnimate.P_BODY+"."+clsComplexBody.P_SENSORSEXT+"."+"6") );
		poProp.setProperty(poPre+clsAnimate.P_BODY+"."+clsComplexBody.P_SENSORSEXT+"."+"6."+clsExternalIO.P_SENSORACTIVE, true);
		poProp.setProperty(poPre+clsAnimate.P_BODY+"."+clsComplexBody.P_SENSORSEXT+"."+"6."+clsExternalIO.P_SENSORTYPE, eSensorExtType.VISION_NEAR.name());
		poProp.setProperty(poPre+clsAnimate.P_BODY+"."+clsComplexBody.P_SENSORSEXT+"."+"6."+clsExternalIO.P_SENSORRANGE, 20);
		poProp.setProperty(poPre+clsAnimate.P_BODY+"."+clsComplexBody.P_SENSORSEXT+"."+"6."+clsSensorVision.P_SENSOR_MIN_DISTANCE, 0 );
		poProp.setProperty(poPre+clsAnimate.P_BODY+"."+clsComplexBody.P_SENSORSEXT+"."+"6."+clsSensorVision.P_SENSOR_FIELD_OF_VIEW, Math.PI );

		poProp.putAll( clsSensorVision.getDefaultProperties( poPre+clsAnimate.P_BODY+"."+clsComplexBody.P_SENSORSEXT+"."+"7") );
		poProp.setProperty(poPre+clsAnimate.P_BODY+"."+clsComplexBody.P_SENSORSEXT+"."+"7."+clsExternalIO.P_SENSORACTIVE, true);
		poProp.setProperty(poPre+clsAnimate.P_BODY+"."+clsComplexBody.P_SENSORSEXT+"."+"7."+clsExternalIO.P_SENSORTYPE, eSensorExtType.VISION_MEDIUM.name());
		poProp.setProperty(poPre+clsAnimate.P_BODY+"."+clsComplexBody.P_SENSORSEXT+"."+"7."+clsExternalIO.P_SENSORRANGE, 40 );
		poProp.setProperty(poPre+clsAnimate.P_BODY+"."+clsComplexBody.P_SENSORSEXT+"."+"7."+clsSensorVision.P_SENSOR_MIN_DISTANCE, 20 );
		poProp.setProperty(poPre+clsAnimate.P_BODY+"."+clsComplexBody.P_SENSORSEXT+"."+"7."+clsSensorVision.P_SENSOR_FIELD_OF_VIEW, Math.PI );

		poProp.putAll( clsSensorVision.getDefaultProperties( poPre+clsAnimate.P_BODY+"."+clsComplexBody.P_SENSORSEXT+"."+"8") );
		poProp.setProperty(poPre+clsAnimate.P_BODY+"."+clsComplexBody.P_SENSORSEXT+"."+"8."+clsExternalIO.P_SENSORACTIVE, true);
		poProp.setProperty(poPre+clsAnimate.P_BODY+"."+clsComplexBody.P_SENSORSEXT+"."+"8."+clsExternalIO.P_SENSORTYPE, eSensorExtType.VISION_FAR.name());
		poProp.setProperty(poPre+clsAnimate.P_BODY+"."+clsComplexBody.P_SENSORSEXT+"."+"8."+clsExternalIO.P_SENSORRANGE, 60);
		poProp.setProperty(poPre+clsAnimate.P_BODY+"."+clsComplexBody.P_SENSORSEXT+"."+"8."+clsSensorVision.P_SENSOR_MIN_DISTANCE, 40 );
		poProp.setProperty(poPre+clsAnimate.P_BODY+"."+clsComplexBody.P_SENSORSEXT+"."+"8."+clsSensorVision.P_SENSOR_FIELD_OF_VIEW, Math.PI );
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