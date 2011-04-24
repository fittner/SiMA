/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;


import java.util.TreeMap;

import config.clsBWProperties;
import du.itf.itfDecisionUnit;
import bw.body.clsMeatBody;
import bw.body.itfGetBrain;
import bw.body.itfGetExternalIO;
import bw.body.io.sensors.ext.clsSensorEngine;
import bw.physicalObjects.sensors.clsEntitySensorEngine;
import bw.utils.enums.eBodyType;

/**
 * Animates represents living objects that can e.g. move, grow, think.
 * 
 * @author langr
 * 
 */
public abstract class clsAnimate extends clsMobile {

	public clsAnimate(itfDecisionUnit poDU, String poPrefix, clsBWProperties poProp, String uid) {
		super(poPrefix, poProp, uid);
		
		applyProperties(poDU, poPrefix, poProp);
	}

	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);

		clsBWProperties oProp = new clsBWProperties();
		oProp.putAll( clsMobile.getDefaultProperties(pre) );
		oProp.putAll( clsMeatBody.getDefaultProperties(pre+P_BODY) );
		oProp.setProperty(pre+P_BODY_TYPE, eBodyType.MEAT.toString());
		return oProp;
	}	
	
	private void applyProperties(itfDecisionUnit poDU, String poPrefix, clsBWProperties poProp) {
	//	String pre 	= clsBWProperties.addDot(poPrefix);

		setDecisionUnit( poDU );		
	}	
	
	public void setDecisionUnit(itfDecisionUnit poDecisionUnit) {
		if (moBody instanceof itfGetBrain && poDecisionUnit != null) {
			((itfGetBrain)moBody).getBrain().setDecisionUnit(poDecisionUnit);
		}
	}
	
	@Override
	public void sensing() {
		moBody.stepSensing();
	}
	
	@Override
	public void execution() {
		moBody.stepExecution();
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
		moBody.stepUpdateInternalState();
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
		if (moBody instanceof itfGetBrain) {
			((itfGetBrain)moBody).getBrain().stepProcessing();
		}
	}
	
	/**
	 * 
	 * (horvath) - returns the radiation sensor
	 *  (zeilinger) - this methos is deprecated - stays within the code due to clarify if the method 
	 *  has to be adapted as it is needed by other classes, or can be deleted
	 * @author horvath
	 * 16.07.2009, 12:11:00
	 *
	 * @return clsEntityPartRadiation
	 */
	
//	public clsEntityPartVision getRadiation()
//	{
//		if (moBody instanceof itfGetExternalIO) {
//			return ((clsSensorRadiation)
//					(((itfGetExternalIO)moBody).getExternalIO().moSensorExternal
//					.get(enums.eSensorExtType.RADIATION))).getMoVisionArea();
//		} else {
//			return null;
//		}
//	}
	
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 28.07.2009, 11:30:28 Integrate SensorEngine 
	 * 
	 * @see bw.body.itfget.itfGetSensorEngine#getSensorEngineAreas()
	 */
	
	public TreeMap<Double, clsEntitySensorEngine> getSensorEngineAreas() {
		if (moBody instanceof itfGetExternalIO) {
			return ((itfGetExternalIO)moBody).getExternalIO().moSensorEngine.getMeSensorAreas();
		} else {
			return null;
		}
	}
	
	public clsSensorEngine getSensorEngine() {
		if (moBody instanceof itfGetExternalIO) {
			return ((itfGetExternalIO)moBody).getExternalIO().moSensorEngine;
		} else {
			return null;
		}
	}
	
//	@Deprecated	
	// - HZ same as for getRadiation()
//	public clsEntityPartVision getEatableArea()
//	{
//		return null; 
//		if (moBody instanceof itfGetExternalIO) {		
//			return ((clsSensorEatableArea)
//					(((itfGetExternalIO)moBody).getExternalIO().
//					.get(enums.eSensorExtType.EATABLE_AREA))).getMoVisionArea(); 
//		} else {
//			return null;
//		}			
//	}	
	
}
