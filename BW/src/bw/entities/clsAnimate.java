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
import decisionunit.clsBaseDecisionUnit;
import du.utils.enums.eDecisionType;
import simple.dumbmind.clsDumbMindA;
import simple.reactive.clsReactive;
import simple.remotecontrol.clsRemoteControl;
import bw.body.clsMeatBody;
import bw.body.itfGetBrain;
import bw.body.itfGetExternalIO;
import bw.body.io.sensors.ext.clsSensorEngine;
//import bw.body.io.sensors.external.clsSensorRadiation;
import bw.physicalObjects.sensors.clsEntityPartVision;
import bw.physicalObjects.sensors.clsEntitySensorEngine;
import bw.utils.enums.eBodyType;

/**
 * Animates represents living objects that can e.g. move, grow, think.
 * 
 * @author langr
 * 
 */
public abstract class clsAnimate extends clsMobile {

	public static final String P_DECISION_TYPE = "decisionunit_type";
	public static final String P_DU_PROPERTIES = "decisionunit_props";
	

	
	public clsAnimate(String poPrefix, clsBWProperties poProp) {
		super(poPrefix, poProp);
		
		applyProperties(poPrefix, poProp);
	}

	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);

		clsBWProperties oProp = new clsBWProperties();
		oProp.putAll( clsMobile.getDefaultProperties(pre) );
		oProp.putAll( clsMeatBody.getDefaultProperties(pre+P_BODY) );
		oProp.setProperty(pre+P_BODY_TYPE, eBodyType.MEAT.toString());
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre 	= clsBWProperties.addDot(poPrefix);

		setDecisionUnit( createDecisionUnit(pre, poProp) );		
	}	

	
	
	private clsBaseDecisionUnit createDecisionUnit(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		eDecisionType nDecisionType = eDecisionType.valueOf( poProp.getPropertyString(pre+P_DECISION_TYPE) );
		
		clsBaseDecisionUnit oDecisionUnit = null;
		
		//create the defined decision unit...
		switch(nDecisionType) {
			case DUMB_MIND_A:
				oDecisionUnit = new clsDumbMindA();
				break;
			case FUNGUS_EATER:
				oDecisionUnit = new clsReactive();
				break;
			case REMOTE:
				oDecisionUnit = new clsRemoteControl();
				break;
			case HARE_JADEX:
				oDecisionUnit = new lifeCycle.JADEX.clsHareMind();
				break;			
			case HARE_JAM:
				oDecisionUnit = new lifeCycle.JAM.clsHareMind();
				break;		
			case HARE_IFTHENELSE:
				oDecisionUnit = new lifeCycle.IfThenElse.clsHareMind();
				break;	
			case LYNX_JADEX:
				oDecisionUnit = new lifeCycle.JADEX.clsLynxMind();
				break;			
			case LYNX_JAM:
				oDecisionUnit = new lifeCycle.JAM.clsLynxMind();
				break;	
			case LYNX_IFTHENELSE:
				oDecisionUnit = new lifeCycle.IfThenElse.clsLynxMind();
				break;	
			case PA:
				oDecisionUnit = new pa.clsPsychoAnalysis(pre+P_DU_PROPERTIES, poProp);
				break;
			default:
				oDecisionUnit = null;
			break;
		}
		
		return oDecisionUnit;
	}
	
	public void setDecisionUnit(clsBaseDecisionUnit poDecisionUnit) {
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
	
	@Deprecated
	public clsEntityPartVision getVision()
	{
		return null; 
	}
	
	/**
	 * 
	 * (horvath) - returns the radiation sensor
	 *
	 * @author horvath
	 * 16.07.2009, 12:11:00
	 *
	 * @return clsEntityPartRadiation
	 */
	
	public clsEntityPartVision getRadiation()
	{
//		if (moBody instanceof itfGetExternalIO) {
//			return ((clsSensorRadiation)
//					(((itfGetExternalIO)moBody).getExternalIO().moSensorExternal
//					.get(enums.eSensorExtType.RADIATION))).getMoVisionArea();
//		} else {
			return null;
		//}
	}
	
	
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
	
	@Deprecated	
	public clsEntityPartVision getEatableArea()
	{
		return null; 
//		if (moBody instanceof itfGetExternalIO) {		
//			return ((clsSensorEatableArea)
//					(((itfGetExternalIO)moBody).getExternalIO().
//					.get(enums.eSensorExtType.EATABLE_AREA))).getMoVisionArea(); 
//		} else {
//			return null;
//		}			
	}	
	
}
