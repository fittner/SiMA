/**
 * clsSensorManipulateArea.java: BW - bw.body.io.sensors.ext
 * 
 * @author langr
 * 09.09.2009, 13:28:44
 */
package bw.body.io.sensors.ext;

import bw.body.io.clsBaseIO;
import bw.utils.enums.eBodyParts;
import config.clsBWProperties;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 09.09.2009, 13:28:44
 * 
 */
public class clsSensorManipulateArea extends clsSensorRingSegment {

	public clsSensorManipulateArea(String poPrefix, clsBWProperties poProp,clsBaseIO poBaseIO) {
		super(poPrefix, poProp, poBaseIO);
	}

	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties( );
		oProp.putAll( clsSensorRingSegment.getDefaultProperties(pre) );
		oProp.setProperty(pre+P_SENSOR_FIELD_OF_VIEW, Math.PI/4); 
		return oProp;
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