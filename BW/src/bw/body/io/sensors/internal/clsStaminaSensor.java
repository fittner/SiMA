/**
 * @author langr
 * 12.05.2009, 17:38:42
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.sensors.internal;

import bw.body.clsBaseBody;
import bw.body.clsSimpleBody;
import bw.body.internalSystems.clsHealthSystem;
import bw.body.internalSystems.clsStaminaSystem;
import bw.body.io.clsBaseIO;
import bw.body.itfget.itfGetBody;
import bw.entities.clsEntity;
import bw.utils.container.clsConfigFloat;
import bw.utils.container.clsConfigMap;
import bw.utils.enums.eConfigEntries;

/**
 * TODO (langr) - insert description 
 * 
 * @author langr
 * 12.05.2009, 17:38:42
 * 
 */
public class clsStaminaSensor extends clsSensorInt {

	private clsBaseBody moBody;
	
	private float mrStaminaValue;
	private float mrRecoveryRate;
	private float mrLowerBound;
	private float mrUpperBound;
	
	
	/**
	 * TODO (langr) - insert description 
	 * 
	 * @author langr
	 * 12.05.2009, 17:38:42
	 *
	 * @param poBaseIO
	 */
	public clsStaminaSensor(clsBaseBody poBody, clsBaseIO poBaseIO, clsConfigMap poConfig) {
		super(poBaseIO, clsStaminaSensor.getFinalConfig(poConfig));
		// TODO Auto-generated constructor stub
		
		applyConfig();
		setEntity(poBody);
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
		
		//oDefault.add(eConfigEntries.ENERGYCONSUMPTION, new clsConfigFloat(5.0f));
		
		return oDefault;
	}
	
	/**
	 * TODO (muchitsch) - insert description
	 *
	 * @param poEntity
	 */
	private void setEntity(clsBaseBody poBody) {
		this.moBody = poBody;
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.05.2009, 17:38:42
	 * 
	 * @see bw.body.io.clsSensorActuatorBase#setBodyPartId()
	 */
	@Override
	protected void setBodyPartId() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.05.2009, 17:38:42
	 * 
	 * @see bw.body.io.clsSensorActuatorBase#setName()
	 */
	@Override
	protected void setName() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.05.2009, 17:38:42
	 * 
	 * @see bw.body.io.sensors.itfSensorUpdate#updateSensorData()
	 */
	@Override
	public void updateSensorData() {

		if ( ((itfGetBody)moBody).getBody() instanceof clsSimpleBody) {
			
			//does not exist in a simple body
			
			//clsStaminaSystem oStaminaSystem = ((clsSimpleBody)moBody).getStaminaSystem();

//			mrStaminaValue = oStaminaSystem.getStamina().getContent();
//			mrRecoveryRate = oStaminaSystem.getRecoveryRate();
//			mrLowerBound = oStaminaSystem.getStamina().getLowerBound();
//			mrUpperBound = oStaminaSystem.getStamina().getUpperBound();
		}
	}
	
	/**
	 * @param mrStaminaValue the mrStaminaValue to set
	 */
	public void setStaminaValue(float mrStaminaValue) {
		this.mrStaminaValue = mrStaminaValue;
	}

	/**
	 * @return the mrStaminaValue
	 */
	public float getStaminaValue() {
		return mrStaminaValue;
	}

	/**
	 * @param mrRecoveryRate the mrRecoveryRate to set
	 */
	public void setRecoveryRate(float mrRecoveryRate) {
		this.mrRecoveryRate = mrRecoveryRate;
	}

	/**
	 * @return the mrRecoveryRate
	 */
	public float getRecoveryRate() {
		return mrRecoveryRate;
	}

	/**
	 * @param mrLowerBound the mrLowerBound to set
	 */
	public void setLowerBound(float mrLowerBound) {
		this.mrLowerBound = mrLowerBound;
	}

	/**
	 * @return the mrLowerBound
	 */
	public float getLowerBound() {
		return mrLowerBound;
	}

	/**
	 * @param mrUpperBound the mrUpperBound to set
	 */
	public void setUpperBound(float mrUpperBound) {
		this.mrUpperBound = mrUpperBound;
	}

	/**
	 * @return the mrUpperBound
	 */
	public float getUpperBound() {
		return mrUpperBound;
	}

}
