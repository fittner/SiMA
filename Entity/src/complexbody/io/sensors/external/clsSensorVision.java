/**
 * clsSensorVision.java: BW - bw.body.io.sensors.ext
 * 
 * @author langr
 * 09.09.2009, 13:14:37
 */
package complexbody.io.sensors.external;

import complexbody.io.clsBaseIO;

import config.clsProperties;
import entities.enums.eBodyParts;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 09.09.2009, 13:14:37
 * 
 */
public class clsSensorVision extends clsSensorRingSegment {

	   public clsSensorVision(String poPrefix, clsProperties poProp, clsBaseIO poBaseIO) {
			super(poPrefix, poProp, poBaseIO);
			applyProperties(poPrefix, poProp);
		}

		public static clsProperties getDefaultProperties(String poPrefix) {
			String pre = clsProperties.addDot(poPrefix);
			
			clsProperties oProp = clsSensorRingSegment.getDefaultProperties(poPrefix);
			oProp.putAll( clsSensorRingSegment.getDefaultProperties(pre));
			return oProp;
		}	
		
		private void applyProperties(String poPrefix, clsProperties poProp) {
			//String pre = clsProperties.addDot(poPrefix);
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