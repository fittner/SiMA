/**
 * @author muchitsch
 * 26.02.2009, 10:21:40
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.sensors.external;

import bw.body.io.clsBaseIO;
import bw.entities.clsEntity;
import bw.utils.container.clsConfigDouble;
import bw.utils.container.clsConfigMap;
import bw.utils.enums.eBodyParts;
import bw.utils.enums.eConfigEntries;

/**
 * A sensor to define what is directly in front of your mouth the area where you can eat something
 * 
 * @author muchitsch
 * 26.02.2009, 10:21:40
 * 
 */
public class clsSensorEatableArea extends clsSensorVision {
	
	
	/*
	 * @param poEntity 
	 *
	 * @param poBaseIO
	 * @param  
	 */
	public clsSensorEatableArea(clsEntity poEntity, clsBaseIO poBaseIO, clsConfigMap poConfig ) {
		super(poEntity, poBaseIO, clsSensorEatableArea.getFinalConfig(poConfig));
		
		applyConfig();
	}

	private void applyConfig() {
		mnViewRad = ((clsConfigDouble)moConfig.get(eConfigEntries.ANGLE)).get();
		mnVisRange = ((clsConfigDouble)moConfig.get(eConfigEntries.RANGE)).get();
		mnVisOffset = ((clsConfigDouble)moConfig.get(eConfigEntries.OFFSET)).get();
	}
	
	private static clsConfigMap getFinalConfig(clsConfigMap poConfig) {
		clsConfigMap oDefault = getDefaultConfig();
		oDefault.overwritewith(poConfig);
		return oDefault;
	}
	
	private static clsConfigMap getDefaultConfig() {
		clsConfigMap oDefault = new clsConfigMap();
		
		oDefault.add(eConfigEntries.ANGLE, new clsConfigDouble(2.0f * (float) Math.PI));
		oDefault.add(eConfigEntries.RANGE, new clsConfigDouble(5.0f));
		oDefault.add(eConfigEntries.OFFSET, new clsConfigDouble(15.0f));
		
		return oDefault;
	}
	
	/* (non-Javadoc)
	 *
	 * @author muchitsch
	 * 26.02.2009, 10:21:40
	 * 
	 * @see bw.body.io.clsSensorActuatorBase#setBodyPartId()
	 */
	@Override
	protected void setBodyPartId() {
		mePartId = eBodyParts.SENSOR_EXT_VISION_EATABLE_AREA;

	}

	/* (non-Javadoc)
	 *
	 * @author muchitsch
	 * 26.02.2009, 10:21:40
	 * 
	 * @see bw.body.io.clsSensorActuatorBase#setName()
	 */
	@Override
	protected void setName() {
		moName = "ext. Sensor Vision Eatable Area";

	}

	/* (non-Javadoc)
	 *
	 * @author muchitsch
	 * 26.02.2009, 10:21:40
	 * 
	 * @see bw.body.io.sensors.itfSensorUpdate#updateSensorData()
	 */
	@Override
	public void updateSensorData() {
		// done by vision base
		super.updateSensorData();
	}

}
