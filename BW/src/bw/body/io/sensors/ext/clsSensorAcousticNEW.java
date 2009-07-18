/**
 * @author zeilinger
 * 18.07.2009, 17:06:30
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.sensors.ext;

import java.util.ArrayList;

import bw.body.io.clsBaseIO;
import bw.utils.container.clsConfigMap;

import sim.physics2D.physicalObject.PhysicalObject2D;

/**
 * TODO (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 18.07.2009, 17:06:30
 * 
 */
public class clsSensorAcousticNEW extends clsSensorExt{

	/**
	 * TODO (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 18.07.2009, 17:08:51
	 *
	 * @param poBaseIO
	 * @param poConfig
	 * @param poSensorEngine
	 */
	public clsSensorAcousticNEW(clsBaseIO poBaseIO, clsConfigMap poConfig,
			clsSensorEngine poSensorEngine) {
		super(poBaseIO, poConfig, poSensorEngine);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 18.07.2009, 17:08:48
	 * 
	 * @see bw.body.io.sensors.ext.clsSensorExt#updateSensorData(java.lang.Double, java.util.ArrayList)
	 */
	@Override
	public void updateSensorData(Double pnRange,
			ArrayList<PhysicalObject2D> peObj) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 18.07.2009, 17:08:48
	 * 
	 * @see bw.body.io.clsSensorActuatorBase#setBodyPartId()
	 */
	@Override
	protected void setBodyPartId() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 18.07.2009, 17:08:48
	 * 
	 * @see bw.body.io.clsSensorActuatorBase#setName()
	 */
	@Override
	protected void setName() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 18.07.2009, 17:08:48
	 * 
	 * @see bw.body.io.sensors.itfSensorUpdate#updateSensorData()
	 */
	@Override
	public void updateSensorData() {
		// TODO Auto-generated method stub
		
	}

}
