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
import bw.body.io.clsBaseIO;
import bw.entities.clsAnimate;
import bw.entities.clsEntity;
import bw.utils.container.clsConfigFloat;
import bw.utils.container.clsConfigMap;
import bw.utils.enums.eBodyParts;
import bw.utils.enums.eConfigEntries;
import bw.utils.enums.partclass.clsPartSensorBump;

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
	private clsPartSensorBump moPartSensorBump;

	/**
	 * constructor takes the entity stored as a local reference 
	 */
	public clsSensorBump(clsEntity poEntity, clsBaseIO poBaseIO, clsConfigMap poConfig) {
		super(poBaseIO, clsSensorBump.getFinalConfig(poConfig));
		
		applyConfig();
		setEntity(poEntity);

		setBumped(false);
		
		moPartSensorBump = new clsPartSensorBump();
	}
	
	private void applyConfig() {

		
		//this registeres a static energy consuption
		registerEnergyConsumption( ((clsConfigFloat)moConfig.get(eConfigEntries.ENERGYCONSUMPTION)).get() ); 

	}
	
	private static clsConfigMap getFinalConfig(clsConfigMap poConfig) {
		clsConfigMap oDefault = getDefaultConfig();
		oDefault.overwritewith(poConfig);
		return oDefault;
	}
		
	private static clsConfigMap getDefaultConfig() {
		clsConfigMap oDefault = new clsConfigMap();
		
		oDefault.add(eConfigEntries.ENERGYCONSUMPTION, new clsConfigFloat(5.0f));
		
		return oDefault;
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
			// TODO calculate true force
			float rForce = 1.0f;
			((clsAnimate)moEntity).getAgentBody().getInterBodyWorldSystem().getDamageBump().bumped(moPartSensorBump, rForce);
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
