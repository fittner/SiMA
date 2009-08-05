/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.interBodyWorldSystems;

import bw.body.itfStep;
import bw.body.internalSystems.clsFastMessengerSystem;
import bw.body.internalSystems.clsHealthSystem;
import bw.utils.config.clsBWProperties;
import bw.utils.enums.eBodyParts;


/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsDamageBump implements itfStep {
	public static final String P_PAINTHRESHOLD = "painthreshold";
	public static final String P_PAINFACTOR = "painfactor";
	public static final String P_HEALTHPENALTY = "healthpenalty";
	public static final String P_HURTTHRESHOLD = "hurthreshold";
	
	private double mrPainThreshold;
	private double mrPainFactor;	
	private double mrHealthPenalty;
	private double mrHurtThreshold;

	private clsHealthSystem moHealthSystem; // reference
	private clsFastMessengerSystem moFastMessengerSystem; // reference
	
	public clsDamageBump(String poPrefix, clsBWProperties poProp, clsHealthSystem poHealthSystem, clsFastMessengerSystem poFastMessengerSystem) {
		moHealthSystem = poHealthSystem;
		moFastMessengerSystem = poFastMessengerSystem;
		
		applyProperties(poPrefix, poProp);
	}

	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.setProperty(pre+P_PAINTHRESHOLD, 0);
		oProp.setProperty(pre+P_PAINFACTOR, 1);
		oProp.setProperty(pre+P_HURTTHRESHOLD, 0);
		oProp.setProperty(pre+P_HEALTHPENALTY, 1);
				
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		mrPainThreshold = poProp.getPropertyDouble(pre+P_PAINTHRESHOLD);
		mrPainFactor = poProp.getPropertyDouble(pre+P_PAINFACTOR);
		mrHealthPenalty = poProp.getPropertyDouble(pre+P_HURTTHRESHOLD);
		mrHurtThreshold = poProp.getPropertyDouble(pre+P_HEALTHPENALTY);

	}	
		
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @author deutsch
	 * 20.02.2009, 12:00:02
	 *
	 * @param prPenaltySum
	 */
	private void hurt(double prPenaltySum) {
		if (prPenaltySum > mrHurtThreshold) {
			double rHealthPenalty = prPenaltySum * mrHealthPenalty;
			moHealthSystem.hurt(rHealthPenalty);
		}
	}
	
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @author deutsch
	 * 20.02.2009, 12:00:00
	 *
	 * @param prPenaltySum
	 */
	private void pain(eBodyParts poSource, double prPenaltySum) {
		if (prPenaltySum > mrPainThreshold) {
			moFastMessengerSystem.addMessage(poSource, eBodyParts.BRAIN, prPenaltySum * mrPainFactor);
		}
	}
	
	public void bumped(eBodyParts poSource, double prForce) {
		hurt(prForce);
		pain(poSource, prForce);
	}
	
	
    /**
     * DOCUMENT (deutsch) - insert description
     *
     */
    public void step() {
    	//do nothing
    }
}
