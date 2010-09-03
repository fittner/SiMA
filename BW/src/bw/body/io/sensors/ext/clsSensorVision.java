/**
 * clsSensorVision.java: BW - bw.body.io.sensors.ext
 * 
 * @author langr
 * 09.09.2009, 13:14:37
 */
package bw.body.io.sensors.ext;

import bw.body.io.clsBaseIO;
import bw.utils.enums.eBodyParts;
import config.clsBWProperties;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 09.09.2009, 13:14:37
 * 
 */
public class clsSensorVision extends clsSensorRingSegment {

	   public clsSensorVision(String poPrefix, clsBWProperties poProp, clsBaseIO poBaseIO) {
			super(poPrefix, poProp, poBaseIO);
			applyProperties(poPrefix, poProp);
		}

		public static clsBWProperties getDefaultProperties(String poPrefix) {
			String pre = clsBWProperties.addDot(poPrefix);
			
			clsBWProperties oProp = clsSensorRingSegment.getDefaultProperties(poPrefix);
			oProp.putAll( clsSensorRingSegment.getDefaultProperties(pre));
			return oProp;
		}	
		
		private void applyProperties(String poPrefix, clsBWProperties poProp) {
			//String pre = clsBWProperties.addDot(poPrefix);
		}
		
		@Override
		protected void setBodyPartId() {
			mePartId = eBodyParts.SENSOR_EXT_VISION;
		}
		
		@Override
		protected void setName() {
			moName = "ext. Sensor Vision";
		}
}