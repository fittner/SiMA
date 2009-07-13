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
import bw.utils.container.clsConfigMap;
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
	
	/**
	 * constructor takes the entity stored as a local reference 
	 */
	public clsSensorPositionChange(clsEntity poEntity, clsBaseIO poBaseIO, clsConfigMap poConfig) {
		super(poBaseIO, clsSensorPositionChange.getFinalConfig(poConfig));
		moLastPos = new clsPose(poEntity.getPose());
		moDiv = new clsPose(0,0,0);
		
		applyConfig();
		setEntity(poEntity);
		// TODO Auto-generated constructor stub
	}
	

	private void applyConfig() {
		
		//TODO add ...

	}
	
	private static clsConfigMap getFinalConfig(clsConfigMap poConfig) {
		clsConfigMap oDefault = getDefaultConfig();
		oDefault.overwritewith(poConfig);
		return oDefault;
	}
	
	private static clsConfigMap getDefaultConfig() {
		clsConfigMap oDefault = new clsConfigMap();
		
		//TODO add ...
		
		return oDefault;
	}
	
	/**
	 * @param moEntity the moEntity to set
	 */
	public void setEntity(clsEntity poEntity) {
		moEntity = poEntity;
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
