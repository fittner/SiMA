/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.brainsocket;

import java.util.HashMap;

import decisionunit.clsBaseDecisionUnit;
import decisionunit.itf.actions.clsActionCommands;
import decisionunit.itf.sensors.clsSensorData;
import bw.body.itfStepProcessing;
import bw.body.brain.symbolization.clsSymbolization;
import bw.body.io.sensors.external.clsSensorExt;
import bw.body.io.sensors.internal.clsSensorInt;
import bw.body.motionplatform.clsBrainActionContainer;
import bw.entities.clsAnimate;
import bw.mind.clsMind;
import bw.mind.ai.clsDumbMindA;
import bw.utils.enums.eSensorExtType;
import bw.utils.enums.eSensorIntType;

/**
 * The brain is the container for the mind and has a direct connection to external and internal IO.
 * Done: re-think if we insert a clsCerebellum for the neuroscientific perception-modules like R. Velik.
 * Answer: moSymbolization
 * 
 * @author langr
 * 
 */
public class clsBrainSocket implements itfStepProcessing {

	private clsBaseDecisionUnit moDecisionUnit;
	private HashMap<eSensorExtType, clsSensorExt> moSensorsExt;
	private HashMap<eSensorIntType, clsSensorInt> moSensorsInt;
	
	
	public clsBrainSocket(HashMap<eSensorExtType, clsSensorExt> poSensorsExt, HashMap<eSensorIntType, clsSensorInt> poSensorsInt) {
		moSensorsExt = poSensorsExt;
		moSensorsInt = poSensorsInt;
	}

	/* (non-Javadoc)
	 * @see bw.body.itfStep#step()
	 */
//	public void stepProcessing(clsAnimate poAnimate, clsBrainActionContainer poActionList) {
	public clsBrainActionContainer stepProcessing() {
		moDecisionUnit.update(convertSensorData());
		clsActionCommands oDecisionUnitResult = moDecisionUnit.process();

		return convertActionCommands(oDecisionUnitResult);	
	}
	
	private clsSensorData convertSensorData() {
		return null;
	}
	
	private clsBrainActionContainer convertActionCommands(clsActionCommands poCommands) {
		return null;
	}
	
	
	/*************************************************
	 *         GETTER & SETTER
	 ************************************************/

	public clsBaseDecisionUnit getDecisionUnit() {
		return moDecisionUnit;		
	}

	public void setDecisionUnit(clsBaseDecisionUnit poDecisionUnit) {
		moDecisionUnit = poDecisionUnit;
	}
	

}
