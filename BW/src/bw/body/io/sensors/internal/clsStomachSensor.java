/**
 * @author langr
 * 12.05.2009, 17:40:44
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.sensors.internal;

import bw.body.clsSimpleBody;
import bw.body.internalSystems.clsHealthSystem;
import bw.body.internalSystems.clsStomachSystem;
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
 * 12.05.2009, 17:40:44
 * 
 */
public class clsStomachSensor extends clsSensorInt {

	private clsEntity moEntity;
	
	private float mrEnergy;
	
	/**
	 * TODO (langr) - insert description 
	 * 
	 * @author langr
	 * 12.05.2009, 17:40:44
	 *
	 * @param poBaseIO
	 */
	public clsStomachSensor(clsEntity poEntity, clsBaseIO poBaseIO, clsConfigMap poConfig) {
		super(poBaseIO, clsStomachSensor.getFinalConfig(poConfig));
		// TODO Auto-generated constructor stub
		
		applyConfig();
		setEntity(poEntity);
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
	private void setEntity(clsEntity poEntity) {
		this.moEntity = poEntity;
	}
	
	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.05.2009, 17:40:44
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
	 * 12.05.2009, 17:40:44
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
	 * 12.05.2009, 17:40:44
	 * 
	 * @see bw.body.io.sensors.itfSensorUpdate#updateSensorData()
	 */
	@Override
	public void updateSensorData() {

		if ( ((itfGetBody)moEntity).getBody() instanceof clsSimpleBody) {
			clsStomachSystem oStomachSystem = ((clsSimpleBody)((itfGetBody)moEntity).getBody()).getStomachSystem();

			mrEnergy = oStomachSystem.getEnergy();
		}
		
	}
	
	/**
	 * @param mrEnergy the mrEnergy to set
	 */
	public void setEnergy(float mrEnergy) {
		this.mrEnergy = mrEnergy;
	}

	/**
	 * @return the mrEnergy
	 */
	public float getEnergy() {
		return mrEnergy;
	}

}
