/**
 * @author muchitsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.actuators.external;

import bw.clsEntity;
import bw.body.io.clsBaseIO;
import bw.utils.enums.eBodyParts;

/**
 *  implementation of a actuator for eating
 * 
 * @author muchitsch
 * 
 */
public class clsActuatorEat extends clsActuatorExt {

	private clsEntity moEntity;

	/**
	 * @param poEntity 
	 * @param poBaseIO
	 */
	public clsActuatorEat(clsEntity poEntity, clsBaseIO poBaseIO) {
		super(poBaseIO);
		setEntity(poEntity);
		
		//this registeres a static energy consuption
		registerEnergyConsumption(5); //TODO register the real value, 
	}
	
	/**
	 * @param poEntity the Entity to set
	 */
	public void setEntity(clsEntity poEntity) {
		this.moEntity = poEntity;
	}

	/* (non-Javadoc)
	 * @see bw.body.io.clsSensorActuatorBase#setBodyPartId()
	 */
	@Override
	protected void setBodyPartId() {
		mePartId = eBodyParts.ACTUATOR_EXT_EAT;
	}

	/* (non-Javadoc)
	 * @see bw.body.io.clsSensorActuatorBase#setName()
	 */
	@Override
	protected void setName() {
		moName = "ext. eat actuator";
	}

	/* (non-Javadoc)
	 * @see bw.body.io.actuators.itfActuatorUpdate#updateActuatorData()
	 */
	@Override
	public void updateActuatorData() {
		
		//is there something present?
		// can i put it in?
		//eat/bite object
		//food = XYZ.withdrawFood()
		//internal system.addFood(food)
		

	}
	
	
	public void eat(clsEntity poEntity){
		
		//try
		//eat entity 
		//catch(ex EntityNotEatable)
	
		
	}

}
