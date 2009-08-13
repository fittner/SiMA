/**
 * clsEffectKiss.java: BW - bw.body.interBodyWorldSystems
 * 
 * @author deutsch
 * 13.08.2009, 10:35:53
 */
package bw.body.interBodyWorldSystems;

import config.clsBWProperties;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 13.08.2009, 10:35:53
 * 
 */
public class clsEffectKiss {
//TODO Muchitsch
	public clsEffectKiss(String poPrefix, clsBWProperties poProp) {
		applyProperties(poPrefix, poProp);
	}

	public static clsBWProperties getDefaultProperties(String poPrefix) {
		//String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		// nothing to do
		
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
//		String pre = clsBWProperties.addDot(poPrefix);
		// nothing to do

	}
}
