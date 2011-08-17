/**
 * clsEffectKiss.java: BW - bw.body.interBodyWorldSystems
 * 
 * @author deutsch
 * 13.08.2009, 10:35:53
 */
package bw.body.interBodyWorldSystems;

import bw.body.internalSystems.clsFastMessengerSystem;
import bw.body.internalSystems.clsSlowMessengerSystem;
import bw.utils.enums.eBodyParts;
import config.clsProperties;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 13.08.2009, 10:35:53
 * 
 */
public class clsEffectKiss {
	
	private clsFastMessengerSystem moFastMessengerSystem; // reference
	//private clsSlowMessengerSystem moSlowMessengerSystem; // reference  //never used!
	
	

	public clsEffectKiss(String poPrefix, clsProperties poProp, clsFastMessengerSystem poFastMessengerSystem, clsSlowMessengerSystem poSlowMessengerSystem) {
		moFastMessengerSystem = poFastMessengerSystem;
		//moSlowMessengerSystem = poSlowMessengerSystem; //never used!
		
		applyProperties(poPrefix, poProp);
	}

	public static clsProperties getDefaultProperties(String poPrefix) {
		//String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
		// nothing to do
		
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsProperties poProp) {
//		String pre = clsProperties.addDot(poPrefix);
		// nothing to do

	}
	
	/**
	 * DOCUMENT (muchitsch) - insert description
	 *
	 * @author muchitsch
	 * Aug 13, 2009, 11:54:10 AM
	 *
	 */
	public void kiss(eBodyParts poSource, double prIntensity)
	{
		
		moFastMessengerSystem.addMessage(poSource, eBodyParts.BRAIN, prIntensity);
		
		//moSlowMessengerSystem.addSlowMessenger(poMessengerId, prDefaultContent, prDefaultMaxContent, prDefaultIncreaseRate, prDefaultDecayRate)
		
	}
}
