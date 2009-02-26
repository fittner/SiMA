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
import ARSsim.physics2D.physicalObject.clsMobileObject2D;
import bw.body.io.clsBaseIO;
import bw.entities.clsEntity;
import bw.entities.clsMobile;
import bw.utils.enums.eBodyParts;

/**
 * A sensor to define what is directly in front of your mouth the area where you can eat something
 * 
 * @author muchitsch
 * 26.02.2009, 10:21:40
 * 
 */
public class clsSensorEatableArea extends clsSensorVision {
	
	private final static double VIEWDEGREE = Math.PI;
	private final static double VISRANGE = 10; 

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
		super(poEntity, poBaseIO, VIEWDEGREE, VISRANGE, poCenterOffset, ((clsMobile)poEntity).getMobileObject2D().getOrientation());
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
