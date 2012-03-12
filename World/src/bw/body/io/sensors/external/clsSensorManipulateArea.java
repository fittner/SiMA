/**
 * clsSensorManipulateArea.java: BW - bw.body.io.sensors.ext
 * 
 * @author langr
 * 09.09.2009, 13:28:44
 */
package bw.body.io.sensors.external;

import bw.body.io.clsBaseIO;
import bw.utils.enums.eBodyParts;
import config.clsProperties;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 09.09.2009, 13:28:44
 * 
 */
public class clsSensorManipulateArea extends clsSensorRingSegment {

	public clsSensorManipulateArea(String poPrefix, clsProperties poProp,clsBaseIO poBaseIO) {
		super(poPrefix, poProp, poBaseIO);
		applyProperties(poPrefix, poProp);
	}

	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties( );
		oProp.putAll( clsSensorRingSegment.getDefaultProperties(pre) );
		oProp.setProperty(pre+P_SENSOR_FIELD_OF_VIEW, Math.PI/4);

		//BD@WhoeverDeletedThis: Without this no entities are found!!!
		oProp.setProperty(pre+P_SENSOR_MIN_DISTANCE, 0);
		oProp.setProperty(pre+P_SENSOR_MAX_DISTANCE, 30);
		oProp.setProperty(pre+P_BASEENERGYCONSUMPTION, 0.0);
		
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsProperties poProp) {
		//String pre = clsProperties.addDot(poPrefix);
		
		//nothing to do
	}
	
	@Override
	protected void setBodyPartId() {
		mePartId = eBodyParts.SENSOR_EXT_VISION_MANIPULATE_AREA;
		
	}

	@Override
	protected void setName() {
		moName = "ext. Sensor Vision ManipulateArea";
		
	}
}