/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io;

import java.util.ArrayList;
import java.util.HashMap;

import bw.body.clsAgentBody;
import bw.body.io.sensors.external.clsSensorExt;
import bw.body.io.sensors.internal.clsSensorInt;
import bw.body.motionplatform.clsBrainAction;
import bw.body.motionplatform.clsBrainActionContainer;
import bw.utils.enums.eSensorExtType;
import bw.utils.enums.eSensorIntType;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsInternalIO extends clsBaseIO{
	public HashMap<eSensorIntType, clsSensorInt> moSensorInternal;

	public clsAgentBody moBody;
	
	public clsInternalIO(clsAgentBody poBody) {
		super(poBody.getInternalSystem().getInternalEnergyConsumption());

		moBody = poBody;
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.02.2009, 16:51:12
	 * 
	 * @see bw.body.itfStepSensing#stepSensing()
	 */
	public void stepSensing() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.02.2009, 16:51:12
	 * 
	 * @see bw.body.itfStepExecution#stepExecution()
	 */
	public void stepExecution(clsBrainActionContainer poActionList) {
		// TODO Auto-generated method stub
		
	}
	
}
