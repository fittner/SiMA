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
import bw.body.clsComplexBody;
import bw.body.io.clsBaseIO;
import bw.body.itfget.itfGetBody;
import bw.entities.clsAnimate;
import bw.entities.clsEntity;
import bw.utils.config.clsBWProperties;
import bw.utils.enums.eBodyParts;

/**
 * implementation of a vision sensor, returns the <clsCollidingObject> list of the actual bumped objects
 * 
 * @author muchitsch
 * 
 */
public class clsSensorBump extends clsSensorExt {

	private clsEntity moEntity;
	private boolean mnBumped;
	public ArrayList<clsCollidingObject> moCollisionList; 

	public clsSensorBump(String poPrefix, clsBWProperties poProp, clsBaseIO poBaseIO, clsEntity poEntity) {
		super(poPrefix, poProp, poBaseIO);
		this.moEntity = poEntity;

		setBumped(false);
		
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
	 * @see bw.body.io.sensors.external.clsSensorExt#updateSensorData()
	 */
	public void updateSensorData() {
		clsMobileObject2D oMobile = ((clsAnimate)moEntity).getMobileObject2D();
		moCollisionList = oMobile.moCollisionList;
		if(moCollisionList.isEmpty()) {
			mnBumped = false;
		}
		else {
			mnBumped = true;
		}
		
		if (mnBumped) {
			if ( ((itfGetBody)moEntity).getBody() instanceof clsComplexBody) {
				//TODO calculate true force
				double rForce = 1.0;
				((clsComplexBody)((itfGetBody)moEntity).getBody()).getInterBodyWorldSystem().getDamageBump().bumped(eBodyParts.SENSOR_EXT_TACTILE_BUMP, rForce);
			}
		}
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

	/**
	 * @author langr
	 * 20.02.2009, 13:09:07
	 * 
	 * @param mnBumped the mnBumped to set
	 */
	public void setBumped(boolean mnBumped) {
		this.mnBumped = mnBumped;
	}

	/**
	 * @author langr
	 * returns true when collision with other object(s)	 * 
	 * @return the mnBumped
	 */
	public boolean isBumped() {
		return mnBumped;
	}

	/**
	 * returns the collision list of the actual bumped objects
	 * @return the moCollisionList
	 */
	public ArrayList<clsCollidingObject> getMoCollisionList() {
		return moCollisionList;
	}
	
	

	
	
}
