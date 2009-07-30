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
import java.util.HashMap;

import bw.body.io.clsBaseIO;
import bw.body.io.clsExternalIO;
import bw.utils.config.clsBWProperties;
import bw.utils.enums.eBodyParts;
import sim.physics2D.physicalObject.PhysicalObject2D;
import sim.physics2D.util.Double2D;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 18.07.2009, 17:06:30
 * 
 */
public class clsSensorAcoustic extends clsSensorExt{


	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 18.07.2009, 17:08:51
	 *
	 * @param poBaseIO
	 * @param poConfig
	 * @param poSensorEngine
	 */

	public clsSensorAcoustic(String poPrefix, clsBWProperties poProp,clsBaseIO poBaseIO) {
		super(poPrefix, poProp, poBaseIO);

		// TODO (zeilinger) - Auto-generated constructor stub
		applyProperties(poPrefix, poProp);
	}


	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		oProp.putAll(clsSensorExt.getDefaultProperties(pre) );

		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		double nFieldOfView= poProp.getPropertyDouble(pre+P_SENSOR_FIELD_OF_VIEW);
		double nRange = poProp.getPropertyDouble(pre+clsExternalIO.P_SENSORRANGE);
		Double2D oOffset =  new Double2D(poProp.getPropertyDouble(pre+P_SENSOR_OFFSET_X),
																 poProp.getPropertyDouble(pre+P_SENSOR_OFFSET_Y));
	
		assignSensorData(oOffset, nRange, nFieldOfView);		
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
		mePartId = eBodyParts.SENSOR_EXT_ACOUSTIC;	
		
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
		moName="ext. Sensor Acoustic";
		
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
		// TODO (zeilinger) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 22.07.2009, 14:59:59
	 * 
	 * @see bw.body.io.sensors.ext.clsSensorExt#updateSensorData(java.lang.Double, java.util.ArrayList, java.util.HashMap)
	 */
	@Override
	public void updateSensorData(Double pnAreaRange,
			ArrayList<PhysicalObject2D> peDetectedObjInAreaList,
			HashMap<Integer, Double2D> peCollisionPointList) {

		setDetectedObjectsList(pnAreaRange, peDetectedObjInAreaList, peCollisionPointList);
	}


	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 27.07.2009, 11:02:59
	 * 
	 * @see bw.body.io.sensors.ext.clsSensorExt#setDetectedObjectsList(java.lang.Double, java.util.ArrayList, java.util.HashMap)
	 */
	@Override
	public void setDetectedObjectsList(Double pnAreaRange,
			ArrayList<PhysicalObject2D> peDetectedObjInAreaList,
			HashMap<Integer, Double2D> peCollisionPointList) {
		
		calculateObjInFieldOfView(pnAreaRange, peDetectedObjInAreaList, peCollisionPointList); 
	}

}
