/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;


import java.util.TreeMap;

import decisionunit.clsBaseDecisionUnit;
import du.utils.enums.eDecisionType;
import simple.dumbmind.clsDumbMindA;
import simple.remotecontrol.clsRemoteControl;
import bw.body.clsBaseBody;
import bw.body.clsComplexBody;
import bw.body.clsMeatBody;
import bw.body.itfGetBrain;
import bw.body.itfGetExternalIO;
import bw.body.io.sensors.ext.clsSensorEngine;
import bw.body.io.sensors.external.clsSensorEatableArea;
import bw.body.io.sensors.external.clsSensorVision;
import bw.body.io.sensors.external.clsSensorRadiation;
import bw.body.itfget.itfGetBody;
import bw.physicalObjects.sensors.clsEntityPartVision;
import bw.physicalObjects.sensors.clsEntitySensorEngine;
import bw.utils.config.clsBWProperties;
import bw.utils.enums.eBodyType;

/**
 * Animates represents living objects that can e.g. move, grow, think.
 * 
 * @author langr
 * 
 */
public abstract class clsAnimate extends clsMobile implements itfGetBody {

	public static final String P_BODY_TYPE = "body_type";
	public static final String P_BODY = "body";
	public static final String P_DECISION_TYPE = "decision_type";
	
	public clsBaseBody moBody; // the instance of a body
	public eBodyType moBodyType;
	public eDecisionType moDecisionType;
	
	public clsAnimate(String poPrefix, clsBWProperties poProp) {
		super(poPrefix, poProp);
		
		applyProperties(poPrefix, poProp);
	}

	private clsBaseBody createBody(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBaseBody oRetVal = null;
		switch( moBodyType ) {
		case BODY_TYPE_MEAT:
			oRetVal = new clsMeatBody(pre, poProp);
			break;
		case BODY_TYPE_COMPLEX:
			oRetVal = new clsComplexBody(pre, poProp, this);
			break;
		default:
			oRetVal = new clsMeatBody(pre, poProp);
			break;
		}
		
		return oRetVal;
	}

	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);

		clsBWProperties oProp = new clsBWProperties();
		oProp.putAll( clsMobile.getDefaultProperties(pre) );
		oProp.putAll( clsMeatBody.getDefaultProperties(pre+P_BODY) );
		oProp.setProperty(pre+P_BODY_TYPE, eBodyType.BODY_TYPE_MEAT.toString());
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre 	= clsBWProperties.addDot(poPrefix);
		moBodyType 	= eBodyType.valueOf( poProp.getPropertyString(pre+P_BODY_TYPE) );
		moBody 		= createBody(poPrefix+P_BODY, poProp);
	}	
	
	public void setDecisionUnit(eDecisionType poDecisionType) {
		if (moBody instanceof itfGetBrain) {
			clsBaseDecisionUnit oDecisionUnit = null;
			
			//create the defined decision unit...
			switch(poDecisionType) {
			case DU_DUMB_MIND_A:
				oDecisionUnit = new clsDumbMindA();
				break;
			case DU_REMOTE:
				oDecisionUnit = new clsRemoteControl();
				break;
			case DU_HARE_MIND_JADEX:
				oDecisionUnit = new lifeCycle.JADEX.clsHareMind();
				break;			
			case DU_HARE_MIND_JAM:
				oDecisionUnit = new lifeCycle.JAM.clsHareMind();
				break;		
			case DU_HARE_MIND_IFTHENELSE:
				oDecisionUnit = new lifeCycle.IfThenElse.clsHareMind();
				break;	
			case DU_LYNX_MIND_JADEX:
				oDecisionUnit = new lifeCycle.JADEX.clsLynxMind();
				break;			
			case DU_LYNX_MIND_JAM:
				oDecisionUnit = new lifeCycle.JAM.clsLynxMind();
				break;	
			case DU_LYNX_MIND_IFTHENELSE:
				oDecisionUnit = new lifeCycle.IfThenElse.clsLynxMind();
				break;			
			default:
				oDecisionUnit = null;
			break;
			}
			
			((itfGetBrain)moBody).getBrain().setDecisionUnit(oDecisionUnit);
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
	 * @author deutsch
	 * 11.05.2009, 18:40:22
	 * 
	 * @see bw.body.itfGetBody#getBody()
	 */
	public clsBaseBody getBody() {
		// TODO Auto-generated method stub
		return moBody;
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
	
	public clsEntityPartVision getVision()
	{
		if (moBody instanceof itfGetExternalIO) {		
			return ((clsSensorVision)
					(((itfGetExternalIO)moBody).getExternalIO().moSensorExternal
					.get(enums.eSensorExtType.VISION))).getMoVisionArea();
		} else {
			return null;
		}		
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
		if (moBody instanceof itfGetExternalIO) {
			return ((clsSensorRadiation)
					(((itfGetExternalIO)moBody).getExternalIO().moSensorExternal
					.get(enums.eSensorExtType.RADIATION))).getMoVisionArea();
		} else {
			return null;
		}
	}
	
		
	public clsEntityPartVision getEatableArea()
	{
		if (moBody instanceof itfGetExternalIO) {		
			return ((clsSensorEatableArea)
					(((itfGetExternalIO)moBody).getExternalIO().moSensorExternal
					.get(enums.eSensorExtType.EATABLE_AREA))).getMoVisionArea(); 
		} else {
			return null;
		}			
	}	
	
	//ZEILINGER - integrate SensorEngine 
	public TreeMap<Double, clsEntitySensorEngine> getSensorEngineAreas()
	{
		if (moBody instanceof itfGetExternalIO) {		
			return ((clsSensorEngine)
					(((itfGetExternalIO)moBody)
					.getExternalIO().moSensorEngine)).getMeSensorAreas();
		} else {
			return null;
		}			
	}
}
