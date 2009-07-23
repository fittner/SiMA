/**
 * @author deutsch
 * 24.06.2009, 11:33:38
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.sensors.external;

import ARSsim.physics2D.util.clsPose;
import bw.body.io.clsBaseIO;
import bw.entities.clsEntity;
import bw.utils.config.clsBWProperties;
import bw.utils.enums.eBodyParts;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 24.06.2009, 11:33:38
 * 
 */
public class clsSensorPositionChange extends clsSensorExt {

	private clsPose moLastPos;
	private clsPose moDiv;
	
	/**
	 * TODO (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 24.06.2009, 11:33:44
	 *
	 * @param poBaseIO
	 * @param poConfig
	 */
	private clsEntity moEntity;
	
	
	public clsSensorPositionChange(String poPrefix, clsBWProperties poProp, clsBaseIO poBaseIO, clsEntity poEntity) {
		super(poPrefix, poProp);
		moEntity = poEntity;
		moLastPos = new clsPose(poEntity.getPose());
		moDiv = new clsPose(0,0,0);
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
	 * @author deutsch
	 * 24.06.2009, 11:33:38
	 * 
	 * @see bw.body.io.sensors.itfSensorUpdate#updateSensorData()
	 */
	public void updateSensorData() {
		clsPose oCurrentPose = new clsPose(moEntity.getPose());
		moDiv = new clsPose(oCurrentPose);
		moDiv.subtract(moLastPos);
		
		moLastPos.setPose(oCurrentPose);

	}
	
	public clsPose getPositionChange() {
		return moDiv;
	}
	
	/* (non-Javadoc)
	 * @see bw.body.io.clsSensorActuatorBase#setBodyPartId()
	 */
	@Override
	protected void setBodyPartId() {
		mePartId = eBodyParts.SENSOR_EXT_POSITIONCHANGE;
	}

	/* (non-Javadoc)
	 * @see bw.body.io.clsSensorActuatorBase#setName()
	 */
	@Override
	protected void setName() {
		moName = "ext. Sensor Position Change";
		
	}	
}
