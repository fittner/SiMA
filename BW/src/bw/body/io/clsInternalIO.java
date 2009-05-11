/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io;

import java.util.HashMap;

import enums.eSensorIntType;

import bw.body.clsBaseBody;
import bw.body.itfGetBody;
import bw.body.io.sensors.internal.clsSensorInt;
import bw.body.motionplatform.clsBrainActionContainer;
import bw.entities.clsEntity;
import bw.utils.container.clsConfigMap;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsInternalIO extends clsBaseIO{
	public HashMap<eSensorIntType, clsSensorInt> moSensorInternal;

	public clsBaseBody moBody;
    
	public clsInternalIO(clsEntity poEntity, clsConfigMap poConfig) {
		super(poEntity, poConfig);

		if (poEntity instanceof itfGetBody) {
			moBody = ((itfGetBody)poEntity).getBody();
		} else {
			moBody = null;
		}
	}
	
	protected clsConfigMap getDefaultConfig() {
		clsConfigMap oDefault = new clsConfigMap();
		//TODO add default values
		return oDefault;
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
