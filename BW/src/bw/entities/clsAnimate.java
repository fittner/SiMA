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
	
	protected clsBaseBody moBody; // the instance of a body
	
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

		setBody( createBody(pre, poProp) );
		setDecisionUnit( createDecisionUnit(pre, poProp) );		
	}	

	private void setBody(clsBaseBody poBody) {
		moBody = poBody;
	}
	
	private clsBaseBody createBody(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
		eBodyType oBodyType = eBodyType.valueOf( poProp.getPropertyString(pre+P_BODY_TYPE) );
		
		clsBaseBody oRetVal = null;
		switch( oBodyType ) {
		case MEAT:
			oRetVal = new clsMeatBody(pre+P_BODY, poProp);
			break;
		case COMPLEX:
			oRetVal = new clsComplexBody(pre+P_BODY, poProp, this);
			break;
		default:
			oRetVal = new clsMeatBody(pre+P_BODY, poProp);
			break;
		}
		
		return oRetVal;
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
	 * @author deutsch
	 * 11.05.2009, 18:40:22
	 * 
	 * @see bw.body.itfGetBody#getBody()
	 */
	public clsBaseBody getBody() {
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
