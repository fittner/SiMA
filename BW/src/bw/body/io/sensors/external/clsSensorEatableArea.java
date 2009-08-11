/**
 * @author muchitsch
 * 26.02.2009, 10:21:40
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.sensors.external;

import config.clsBWProperties;
import bw.body.io.clsBaseIO;
import bw.entities.clsEntity;
import bw.utils.enums.eBodyParts;

/**
 * A sensor to define what is directly in front of your mouth the area where you can eat something
 * 
 * @author muchitsch
 * 26.02.2009, 10:21:40
 * 
 */
public class clsSensorEatableArea extends clsSensorVision {
	
	public clsSensorEatableArea(String poPrefix, clsBWProperties poProp, clsBaseIO poBaseIO, clsEntity poEntity)	{
		super(poPrefix, poProp, poBaseIO, poEntity);		
		
		applyProperties(poPrefix, poProp);
	}	
	
        public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		oProp.putAll( clsSensorVision.getDefaultProperties(pre) );
		
		oProp.setProperty(pre+P_SENSOR_ANGLE, (2.0 *  Math.PI) );
		oProp.setProperty(pre+P_SENSOR_RANGE, 5 );
		oProp.setProperty(pre+P_SENSOR_OFFSET, 15 );		
				
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		//String pre = clsBWProperties.addDot(poPrefix);
		
		// nothing to do
	}

	
	/* (non-Javadoc)
	 *
	 * @author muchitsch
	 * 26.02.2009, 10:21:40
	 * 
	 * @see bw.body.io.clsSensorActuatorBase#setBodyPartId()
	 */
	@Override
	protected void setBodyPartId() {
		mePartId = eBodyParts.SENSOR_EXT_VISION_EATABLE_AREA;

	}

	/* (non-Javadoc)
	 *
	 * @author muchitsch
	 * 26.02.2009, 10:21:40
	 * 
	 * @see bw.body.io.clsSensorActuatorBase#setName()
	 */
	@Override
	protected void setName() {
		moName = "ext. Sensor Vision Eatable Area";

	}

	/* (non-Javadoc)
	 *
	 * @author muchitsch
	 * 26.02.2009, 10:21:40
	 * 
	 * @see bw.body.io.sensors.itfSensorUpdate#updateSensorData()
	 */
	@Override
	public void updateSensorData() {
		// done by vision base
		super.updateSensorData();
	}

}
