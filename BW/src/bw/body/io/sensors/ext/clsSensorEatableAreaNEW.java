/**
 * @author zeilinger
 * 18.07.2009, 17:07:05
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.sensors.ext;

import java.util.ArrayList;
import java.util.HashMap;

import bw.body.io.clsBaseIO;
import bw.entities.clsEntity;
import bw.utils.config.clsBWProperties;
import sim.physics2D.physicalObject.PhysicalObject2D;
import sim.physics2D.util.Double2D;

/**
 * TODO (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 18.07.2009, 17:07:05
 * 
 */
public class clsSensorEatableAreaNEW extends clsSensorExt{


	/**
	 * TODO (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 18.07.2009, 17:08:12
	 *
	 * @param poBaseIO
	 * @param poConfig
	 * @param poSensorEngine
	 */

	public clsSensorEatableAreaNEW(String poPrefix, clsBWProperties poProp,
									clsBaseIO poBaseIO, clsSensorEngine poSensorEngine,clsEntity poEntity) {
		super(poPrefix, poProp, poBaseIO, poSensorEngine, poEntity);

		// TODO Auto-generated constructor stub
		applyProperties(poPrefix, poProp);
	}


	public static clsBWProperties getDefaultProperties(String poPrefix) {
		// String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		//nothing to do
				
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		//String pre = clsBWProperties.addDot(poPrefix);

		//nothing to do
	}	

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 18.07.2009, 17:07:41
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
	 * 18.07.2009, 17:07:41
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
	 * 18.07.2009, 17:07:41
	 * 
	 * @see bw.body.io.sensors.itfSensorUpdate#updateSensorData()
	 */
	@Override
	public void updateSensorData() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 22.07.2009, 14:59:07
	 * 
	 * @see bw.body.io.sensors.ext.clsSensorExt#updateSensorData(java.lang.Double, java.util.ArrayList, java.util.HashMap)
	 */
	@Override
	public void updateSensorData(Double pnRange,
			ArrayList<PhysicalObject2D> peDetectedObj,
			HashMap<Integer, Double2D> peCollisionPoints) {
		// TODO Auto-generated method stub
		
	}

}
