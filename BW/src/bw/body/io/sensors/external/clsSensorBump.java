/**
 * @author zia
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.sensors.external;

import java.util.ArrayList;
import ARSsim.physics2D.physicalObject.clsCollidingObject;
import ARSsim.physics2D.physicalObject.clsMobileObject2D;
import bw.clsEntity;
import bw.body.io.clsBaseIO;
import bw.physicalObject.animate.clsAnimate;
import bw.utils.enums.eBodyParts;

/**
 * implementation of a vision sensor, returns the <clsCollidingObject> list of the actual bumped objects
 * 
 * @author muchitsch
 * 
 */
public class clsSensorBump extends clsSensorExt {

	private clsEntity moEntity;
	private ArrayList<clsCollidingObject> moCollisionList;


	/**
	 * constructor takes the entity stored as a local reference 
	 */
	public clsSensorBump(clsEntity poEntity, clsBaseIO poBaseIO) {
		super(poBaseIO);
		setEntity(poEntity);
		
		//this registeres a static energy consuption
		registerEnergyConsumption(5); //TODO register the real value, 

	}
	
	/**
	 * returns the collision list of the actual bumped objects
	 * @return the moCollisionList
	 */
	public ArrayList<clsCollidingObject> getMoCollisionList() {
		return moCollisionList;
	}
	
	/**
	 * TODO (muchitsch) - insert description
	 *
	 * @param poEntity
	 */
	private void setEntity(clsEntity poEntity) {
		this.moEntity = poEntity;
	}

	/* (non-Javadoc)
	 * @see bw.body.io.sensors.external.clsSensorExt#updateSensorData()
	 */
	@Override
	public void updateSensorData() {
		clsMobileObject2D oMobile = ((clsAnimate)moEntity).getMobile();
		moCollisionList = oMobile.moCollisionList;
	}

	/* (non-Javadoc)
	 * @see bw.body.io.clsSensorActuatorBase#setBodyPartId()
	 */
	@Override
	protected void setBodyPartId() {
		mePartId = eBodyParts.SENSOR_EXT_TACTILE_BUMP;
		
	}

	/* (non-Javadoc)
	 * @see bw.body.io.clsSensorActuatorBase#setName()
	 */
	@Override
	protected void setName() {
		moName = "ext. Bumpsensor";
		
	}

	
	

	
	
}
