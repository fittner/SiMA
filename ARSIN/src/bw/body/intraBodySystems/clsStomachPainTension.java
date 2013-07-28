/**
 * clsStomachDamageTension.java: BW - bw.body.intraBodySystems
 * 
 * @author deutsch
 * 05.10.2009, 11:37:42
 */
package bw.body.intraBodySystems;

import config.clsProperties;
import bw.body.itfStepUpdateInternalState;
import bw.body.internalSystems.clsFastMessengerSystem;
import bw.body.internalSystems.clsDigestiveSystem;
import bw.utils.enums.eBodyParts;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 05.10.2009, 11:37:42
 * 
 */
public class clsStomachPainTension implements itfStepUpdateInternalState  {
	public static final String P_PAINTHRESHOLD = "painthreshold";
	public static final String P_PAINFACTOR = "painfactor";
	
	private double mrPainThreshold;
	private double mrPainFactor;	
	
	private clsDigestiveSystem moStomachSystem;
	private clsFastMessengerSystem moFastMessengerSystem;
	
	public clsStomachPainTension(String poPrefix, clsProperties poProp, clsDigestiveSystem poStomachSystem, clsFastMessengerSystem poFastMessengerSystem) {
		moStomachSystem = poStomachSystem;		
		moFastMessengerSystem = poFastMessengerSystem;
		
		applyProperties(poPrefix, poProp);
	}

	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
		oProp.setProperty(pre+P_PAINTHRESHOLD, 0.8);
		oProp.setProperty(pre+P_PAINFACTOR, 1);
				
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
		
		mrPainThreshold = poProp.getPropertyDouble(pre+P_PAINTHRESHOLD);
		mrPainFactor = poProp.getPropertyDouble(pre+P_PAINFACTOR);
	}			

	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @author deutsch
	 * 20.02.2009, 12:00:00
	 *
	 * @param prPenaltySum
	 */
	private void pain(double prPenaltySum) {
		if (prPenaltySum > mrPainThreshold) {
			if (moFastMessengerSystem != null) {
				moFastMessengerSystem.addMessage(eBodyParts.INTER_PAIN_STOMACHTENSION, eBodyParts.BRAIN, prPenaltySum * mrPainFactor);
			}
		}
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 05.10.2009, 11:38:16
	 * 
	 * @see bw.body.itfStepUpdateInternalState#stepUpdateInternalState()
	 */
	@Override
	public void stepUpdateInternalState() {
    	double rPenaltySum = moStomachSystem.getWeight() / moStomachSystem.getMaxWeight();
    	
    	pain(rPenaltySum);
	}
}
