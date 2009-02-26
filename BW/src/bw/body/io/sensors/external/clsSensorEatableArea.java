/**
 * @author muchitsch
 * 26.02.2009, 10:21:40
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.sensors.external;

import sim.physics2D.util.Double2D;
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
public class clsSensorEatableArea extends clsSensorExt {

	/**
	 * A sensor to define what is directly in front of your mouth the area where you can eat something
	 * 
	 * @author muchitsch
	 * 26.02.2009, 10:21:40
	 * @param poEntity 
	 *
	 * @param poBaseIO
	 * @param  
	 */
	public clsSensorEatableArea(clsEntity poEntity, clsBaseIO poBaseIO, Double2D poCenterOffset ) {
		super(poBaseIO);
		// TODO Auto-generated constructor stub

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
		// TODO  clemens can be donne by heimos base?

	}

}
