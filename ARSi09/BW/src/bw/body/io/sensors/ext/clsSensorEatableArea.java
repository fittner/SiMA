/**
 * @author zeilinger
 * 18.07.2009, 17:07:05
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.sensors.ext;

import config.clsBWProperties;
import bw.body.io.clsBaseIO;
import bw.utils.enums.eBodyParts;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 18.07.2009, 17:07:05
 * 
 */

public class clsSensorEatableArea extends clsSensorRingSegment{

	public clsSensorEatableArea(String poPrefix, clsBWProperties poProp,clsBaseIO poBaseIO) {
		super(poPrefix, poProp, poBaseIO);
		applyProperties(poPrefix, poProp);
	}

	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties( );
		oProp.putAll( clsSensorRingSegment.getDefaultProperties(pre) );
		oProp.setProperty(pre+P_SENSOR_FIELD_OF_VIEW, Math.PI/4); 

		oProp.setProperty(pre+P_SENSOR_MIN_DISTANCE, 0);
		oProp.setProperty(pre+P_SENSOR_MAX_DISTANCE, 30); //FIXME (zeilinger): range was former set to 15 --> problem: cake will never be in range
		oProp.setProperty(pre+P_BASEENERGYCONSUMPTION, 0.0);
		
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		//String pre = clsBWProperties.addDot(poPrefix);
		
		//nothing to do
	}

	@Override
	protected void setBodyPartId() {
		mePartId = eBodyParts.SENSOR_EXT_VISION_EATABLE_AREA;
		
	}

	@Override
	protected void setName() {
		moName = "ext. Sensor Vision Eatable Area";
		
	}
}
