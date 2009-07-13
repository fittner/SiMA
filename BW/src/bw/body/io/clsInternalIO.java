/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io;

import java.util.HashMap;
import java.util.Iterator;

import enums.eSensorIntType;

import bw.body.clsBaseBody;
import bw.body.io.sensors.internal.clsEnergySensor;
import bw.body.io.sensors.internal.clsHealthSensor;
import bw.body.io.sensors.internal.clsSensorInt;
import bw.body.io.sensors.internal.clsStaminaSensor;
import bw.body.io.sensors.internal.clsStomachSensor;
import bw.utils.container.clsBaseConfig;
import bw.utils.container.clsConfigBoolean;
import bw.utils.container.clsConfigEnum;
import bw.utils.container.clsConfigList;
import bw.utils.container.clsConfigMap;
import bw.utils.enums.eConfigEntries;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsInternalIO extends clsBaseIO{
	public HashMap<eSensorIntType, clsSensorInt> moSensorInternal;

	public clsBaseBody moBody;
    
	public clsInternalIO(clsBaseBody poBody, clsConfigMap poConfig) {
		super(poBody, clsInternalIO.getFinalConfig(poConfig));

		moSensorInternal = new HashMap<eSensorIntType, clsSensorInt>();
		moBody = poBody;
		
		applyConfig();
		
	}
	
	private void applyConfig() {
		
		//initialization of sensors
		if ( ((clsConfigBoolean)moConfig.get(eConfigEntries.ACTIVATE)).get() ) {
			initSensorInternal((clsConfigList)moConfig.get(eConfigEntries.INTSENSORS), (clsConfigMap)moConfig.get(eConfigEntries.INTSENSORCONFIG));
		}


	}
	
	private static clsConfigMap getFinalConfig(clsConfigMap poConfig) {
		clsConfigMap oDefault = getDefaultConfig();
		oDefault.overwritewith(poConfig);
		return oDefault;
	}
	
	private static clsConfigMap getDefaultConfig() {
		clsConfigMap oDefault = new clsConfigMap();
		
		oDefault.add(eConfigEntries.ACTIVATE, new clsConfigBoolean(true));
		
		clsConfigList oSensors = new clsConfigList();
		oSensors.add(new clsConfigEnum<eConfigEntries>(eConfigEntries.ENERGY_CONSUMPTION));
		oSensors.add(new clsConfigEnum<eConfigEntries>(eConfigEntries.HEALTH_SYSTEM));
		oSensors.add(new clsConfigEnum<eConfigEntries>(eConfigEntries.STAMINA));
		oSensors.add(new clsConfigEnum<eConfigEntries>(eConfigEntries.STOMACH));	
		oDefault.add(eConfigEntries.INTSENSORS, oSensors);
		

		clsConfigMap oSensorConfigs = new clsConfigMap();
		clsConfigMap oSC_Temp;
		
		oSC_Temp = new clsConfigMap();
		oSC_Temp.add(eConfigEntries.ACTIVATE, new clsConfigBoolean(true));
		oSensorConfigs.add(eConfigEntries.ENERGY_CONSUMPTION, oSC_Temp);
		
		oSC_Temp = new clsConfigMap();
		oSC_Temp.add(eConfigEntries.ACTIVATE, new clsConfigBoolean(true));
		oSensorConfigs.add(eConfigEntries.HEALTH_SYSTEM, oSC_Temp);

		oSC_Temp = new clsConfigMap();
		oSC_Temp.add(eConfigEntries.ACTIVATE, new clsConfigBoolean(true));
		oSensorConfigs.add(eConfigEntries.STAMINA, oSC_Temp);
		
		oSC_Temp = new clsConfigMap();
		oSC_Temp.add(eConfigEntries.ACTIVATE, new clsConfigBoolean(true));
		oSensorConfigs.add(eConfigEntries.STOMACH, oSC_Temp);
		
		oDefault.add(eConfigEntries.INTSENSORCONFIG, oSensorConfigs);
		
		return oDefault;
	}	
	
	@SuppressWarnings("unchecked") // EH: probably unsafe, please refactor
	private void initSensorInternal(clsConfigList poInternalSensors, clsConfigMap poSensorConfigs) {
		Iterator<clsBaseConfig> i = poInternalSensors.iterator();
		while (i.hasNext()) {
			eConfigEntries eType = (eConfigEntries) ((clsConfigEnum)i.next()).get();
			clsConfigMap oConfig = (clsConfigMap)poSensorConfigs.get(eType);
			boolean nActivate = ((clsConfigBoolean)oConfig.get(eConfigEntries.ACTIVATE)).get();

			if (nActivate) {
				switch (eType) {
					case ENERGY_CONSUMPTION: 
						clsEnergySensor oTemp =  new clsEnergySensor(moBody, this, oConfig);
						moSensorInternal.put(eSensorIntType.ENERGY_CONSUMPTION, oTemp); 
						break;
					case HEALTH_SYSTEM: 
						moSensorInternal.put(eSensorIntType.HEALTH_SYSTEM, new clsHealthSensor(moBody, this, oConfig)); 
						break;
					case STAMINA: 
						moSensorInternal.put(eSensorIntType.STAMINA, new clsStaminaSensor(moBody, this, oConfig)); 
						break;
					case STOMACH: 
						moSensorInternal.put(eSensorIntType.STOMACH, new clsStomachSensor(moBody, this, oConfig)); 
						break;
				}
			}
		}
	}
	

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.02.2009, 16:51:12
	 * 
	 * @see bw.body.itfStepSensing#stepSensing()
	 */
	public void stepSensing() {
		for (clsSensorInt sensor : moSensorInternal.values()) {
			sensor.updateSensorData();
		}		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.02.2009, 16:51:12
	 * 
	 * @see bw.body.itfStepExecution#stepExecution()
	 */
	public void stepExecution() {
		
	}
	
}
