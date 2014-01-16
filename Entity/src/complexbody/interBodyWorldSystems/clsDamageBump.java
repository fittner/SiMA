/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package complexbody.interBodyWorldSystems;

import properties.clsProperties;
import utils.exceptions.exSlowMessengerDoesNotExist;
import utils.exceptions.exValueNotWithinRange;
import complexbody.internalSystems.clsFastMessengerSystem;
import complexbody.internalSystems.clsHealthSystem;
import complexbody.internalSystems.clsSlowMessengerSystem;

import du.enums.eSlowMessenger;
import entities.enums.eBodyParts;
import body.itfStep;


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
	public static final String P_ADRENALINFACTOR = "adrenalinfactor";
	public static final String P_ADRENALINTHRESHOLD = "adrenalinthreshold";
	
	private double mrPainThreshold;
	private double mrPainFactor;	
	private double mrHealthPenalty;
	private double mrHurtThreshold;
	private double mrAdrenalinFactor;
	private double mrAdrenalinThreshold;


	private clsHealthSystem moHealthSystem; // reference
	private clsFastMessengerSystem moFastMessengerSystem; // reference
	private clsSlowMessengerSystem moSlowMessengerSystem;
	
	public clsDamageBump(String poPrefix, clsProperties poProp, clsHealthSystem poHealthSystem, clsFastMessengerSystem poFastMessengerSystem, clsSlowMessengerSystem poSlowMessengerSystem) {
		moHealthSystem = poHealthSystem;
		moFastMessengerSystem = poFastMessengerSystem;
		moSlowMessengerSystem = poSlowMessengerSystem;
		
		applyProperties(poPrefix, poProp);
	}

	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
		oProp.setProperty(pre+P_PAINTHRESHOLD, 0);
		oProp.setProperty(pre+P_PAINFACTOR, 1);
		oProp.setProperty(pre+P_HURTTHRESHOLD, 0);
		oProp.setProperty(pre+P_HEALTHPENALTY, 1);
		oProp.setProperty(pre+P_ADRENALINFACTOR, 1);
		oProp.setProperty(pre+P_ADRENALINTHRESHOLD, 0);
		
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
		
		mrPainThreshold = poProp.getPropertyDouble(pre+P_PAINTHRESHOLD);
		mrPainFactor = poProp.getPropertyDouble(pre+P_PAINFACTOR);
		mrHealthPenalty = poProp.getPropertyDouble(pre+P_HURTTHRESHOLD);
		mrHurtThreshold = poProp.getPropertyDouble(pre+P_HEALTHPENALTY);
		mrAdrenalinFactor = poProp.getPropertyDouble(pre+P_ADRENALINFACTOR);
		mrAdrenalinThreshold = poProp.getPropertyDouble(pre+P_ADRENALINTHRESHOLD);
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
	
	private void adrenalin(double prPenaltySum) {
		if (prPenaltySum > mrAdrenalinThreshold) {
			double rDiff = prPenaltySum - mrAdrenalinThreshold;
			try {
				moSlowMessengerSystem.inject(eSlowMessenger.ADREANLIN, rDiff * mrAdrenalinFactor);
			} catch (exSlowMessengerDoesNotExist e) {
				// nothing to do
			} catch (exValueNotWithinRange e) {
				// nothing to do
			}
		}
	}	
	
	public void bumped(eBodyParts poSource, double prForce) {
		hurt(prForce);
		pain(poSource, prForce);
		adrenalin(prForce);
	}
	
	
    /**
     * DOCUMENT (deutsch) - insert description
     *
     */
    public void step() {
    	//do nothing
    }
}
