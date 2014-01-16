/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package complexbody.io;

import java.util.HashMap;

import properties.clsProperties;

import complexbody.io.sensors.internal.clsEnergyConsumptionSensor;
import complexbody.io.sensors.internal.clsEnergySensor;
import complexbody.io.sensors.internal.clsFastMessengerSensor;
import complexbody.io.sensors.internal.clsHealthSensor;
import complexbody.io.sensors.internal.clsIntestinePressureSensor;
import complexbody.io.sensors.internal.clsSensorInt;
import complexbody.io.sensors.internal.clsSlowMessengerSensor;
import complexbody.io.sensors.internal.clsStaminaSensor;
import complexbody.io.sensors.internal.clsStomachSensor;
import complexbody.io.sensors.internal.clsStomachTensionSensor;
import complexbody.io.sensors.internal.clsTemperatureSensor;

import du.enums.eSensorIntType;
import body.clsBaseBody;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsInternalIO extends clsBaseIO{
	public static final String P_NUMSENSORS = "numsensors";
	public static final String P_SENSORTYPE = "sensortype";
	public static final String P_SENSORACTIVE = "sensoractive";	

	public HashMap<eSensorIntType, clsSensorInt> moSensorInternal;

	public clsBaseBody moBody;
	
	public clsInternalIO(String poPrefix, clsProperties poProp, clsBaseBody poBody) {
		super(poPrefix, poProp, poBody);
		
		moSensorInternal = new HashMap<eSensorIntType, clsSensorInt>();
		moBody = poBody;
		
		applyProperties(poPrefix, poProp, poBody);
	}

	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
		
		int i=0;
		oProp.putAll( clsEnergyConsumptionSensor.getDefaultProperties( pre+i) );
		oProp.setProperty(pre+i+"."+P_SENSORACTIVE, true);
		oProp.setProperty(pre+i+"."+P_SENSORTYPE, eSensorIntType.ENERGY_CONSUMPTION.name());
		i++;
				
		oProp.putAll( clsHealthSensor.getDefaultProperties( pre+i) );
		oProp.setProperty(pre+i+"."+P_SENSORACTIVE, true);
		oProp.setProperty(pre+i+"."+P_SENSORTYPE, eSensorIntType.HEALTH.name());
		i++;
		
		oProp.putAll( clsStaminaSensor.getDefaultProperties( pre+i) );
		oProp.setProperty(pre+i+"."+P_SENSORACTIVE, true);
		oProp.setProperty(pre+i+"."+P_SENSORTYPE, eSensorIntType.STAMINA.name());
		i++;
				
		oProp.putAll( clsStomachSensor.getDefaultProperties( pre+i) );
		oProp.setProperty(pre+i+"."+P_SENSORACTIVE, true);
		oProp.setProperty(pre+i+"."+P_SENSORTYPE, eSensorIntType.STOMACH.name());
		i++;
		
		oProp.putAll( clsHealthSensor.getDefaultProperties( pre+i) );
		oProp.setProperty(pre+i+"."+P_SENSORACTIVE, true);
		oProp.setProperty(pre+i+"."+P_SENSORTYPE, eSensorIntType.TEMPERATURE.name());
		i++;
		
		oProp.putAll( clsFastMessengerSensor.getDefaultProperties( pre+i) );
		oProp.setProperty(pre+i+"."+P_SENSORACTIVE, true);
		oProp.setProperty(pre+i+"."+P_SENSORTYPE, eSensorIntType.FASTMESSENGER.name());
		i++;		
				
		oProp.putAll( clsEnergySensor.getDefaultProperties( pre+i) );
		oProp.setProperty(pre+i+"."+P_SENSORACTIVE, true);
		oProp.setProperty(pre+i+"."+P_SENSORTYPE, eSensorIntType.ENERGY.name());
		i++;		
		
		oProp.putAll( clsStomachTensionSensor.getDefaultProperties( pre+i) );
		oProp.setProperty(pre+i+"."+P_SENSORACTIVE, true);
		oProp.setProperty(pre+i+"."+P_SENSORTYPE, eSensorIntType.STOMACHTENSION.name());
		i++;		

		oProp.putAll( clsSlowMessengerSensor.getDefaultProperties( pre+i) );
		oProp.setProperty(pre+i+"."+P_SENSORACTIVE, true);
		oProp.setProperty(pre+i+"."+P_SENSORTYPE, eSensorIntType.SLOWMESSENGER.name());
		i++;		

		oProp.putAll( clsIntestinePressureSensor.getDefaultProperties( pre+i) );
		oProp.setProperty(pre+i+"."+P_SENSORACTIVE, true);
		oProp.setProperty(pre+i+"."+P_SENSORTYPE, eSensorIntType.INTESTINEPRESSURE.name());
		i++;		
		
		oProp.setProperty(pre+P_NUMSENSORS, i);
		
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsProperties poProp, clsBaseBody poBody) {
		String pre = clsProperties.addDot(poPrefix);

		int num = poProp.getPropertyInt(pre+P_NUMSENSORS);
		for (int i=0; i<num; i++) {
			String tmp_pre = pre+i+".";
			
			boolean nActive = poProp.getPropertyBoolean(tmp_pre+P_SENSORACTIVE);
			if (nActive) {
				String oType = poProp.getPropertyString(tmp_pre+P_SENSORTYPE);
				eSensorIntType eType = eSensorIntType.valueOf(oType);
				
				switch(eType) {
					case ENERGY_CONSUMPTION:
						moSensorInternal.put(eType, new clsEnergyConsumptionSensor(tmp_pre, poProp, this, poBody)); 
						break;
					case HEALTH:
						moSensorInternal.put(eType, new clsHealthSensor(tmp_pre, poProp, this, poBody)); 
						break;
					case STAMINA:
						moSensorInternal.put(eType, new clsStaminaSensor(tmp_pre, poProp, this, poBody)); 
						break;
					case INTESTINEPRESSURE:
						moSensorInternal.put(eType, new clsIntestinePressureSensor(tmp_pre, poProp, this, poBody)); 
						break;
					case STOMACH:
						moSensorInternal.put(eType, new clsStomachSensor(tmp_pre, poProp, this, poBody)); 
						break;
					case TEMPERATURE:
						moSensorInternal.put(eType, new clsTemperatureSensor(tmp_pre, poProp, this, poBody)); 
						break;
					case FASTMESSENGER:
						moSensorInternal.put(eType, new clsFastMessengerSensor(tmp_pre, poProp, this, poBody));
						break;
					case ENERGY:
						moSensorInternal.put(eType, new clsEnergySensor(tmp_pre, poProp, this, poBody));
						break;		
					case STOMACHTENSION:
						moSensorInternal.put(eType, new clsStomachTensionSensor(tmp_pre, poProp, this, poBody));
						break;
					case SLOWMESSENGER:
						moSensorInternal.put(eType, new clsSlowMessengerSensor(tmp_pre, poProp, this, poBody));
						break;
						
					default:
						throw new java.lang.NoSuchMethodError(eType.toString());
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
	@Override
	public void stepSensing() {
		for (clsSensorInt sensor : moSensorInternal.values()) {
			sensor.updateSensorData();
		}		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 22.07.2009, 23:19:16
	 * 
	 * @see bw.body.itfStepExecution#stepExecution()
	 */
	@Override
	public void stepExecution() {
		// nothing to do
		
	}

}
