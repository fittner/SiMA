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
import bw.utils.enums.partclass.clsPartBrain;
import bw.utils.enums.partclass.clsPartDamageLightning;
import bw.utils.enums.partclass.clsPartSensorBump;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsDamageLightning implements itfStep {
	public static final String P_PAINTHRESHOLD = "painthreshold";
	public static final String P_PAINFACTOR = "painfactor";
	public static final String P_HEALTHPENALTY = "healthpenalt";
	public static final String P_HURTTHRESHOLD = "hurthreshold";
	
	private double mrPainThreshold;
	private double mrPainFactor;	
	private double mrHealthPenalty;
	private double mrHurtThreshold;

	private clsHealthSystem moHealthSystem; // EH - make warning free
	private clsFastMessengerSystem moFastMessengerSystem;
	
	public clsDamageLightning(String poPrefix, clsBWProperties poProp, clsHealthSystem poHealthSystem, clsFastMessengerSystem poFastMessengerSystem) {
		moHealthSystem = poHealthSystem;
		moFastMessengerSystem = poFastMessengerSystem;
		moFastMessengerSystem.addMapping(new clsPartDamageLightning(), new clsPartBrain());
		
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
	 * TODO (deutsch) - insert description
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
	 * TODO (deutsch) - insert description
	 *
	 * @author deutsch
	 * 20.02.2009, 12:00:00
	 *
	 * @param prPenaltySum
	 */
	private void pain(clsPartSensorBump poSource, double prPenaltySum) {
		if (prPenaltySum > mrPainThreshold) {
			moFastMessengerSystem.addMessage((clsPartSensorBump)poSource.clone(), new clsPartBrain(), prPenaltySum * mrPainFactor);
		}
	}
	
	public void bumped(clsPartSensorBump poSource, double prForce) {
		hurt(prForce);
		pain(poSource, prForce);
	}
	
    /**
     * TODO (deutsch) - insert description
     *
     */
    public void step() {
    	
    }
}
