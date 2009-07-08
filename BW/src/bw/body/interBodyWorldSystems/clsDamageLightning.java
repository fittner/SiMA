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
import bw.body.internalSystems.clsInternalSystem;
import bw.utils.container.clsConfigDouble;
import bw.utils.container.clsConfigMap;
import bw.utils.enums.eConfigEntries;
import bw.utils.enums.partclass.clsPartBrain;
import bw.utils.enums.partclass.clsPartDamageBump;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsDamageLightning implements itfStep {

	private double mrPainThreshold;
	private double mrHealthPenalty;
	private double mrHurtThreshold;

	private clsHealthSystem moHealthSystem;
	private clsFastMessengerSystem moFastMessengerSystem;
	
    private clsConfigMap moConfig;	
	
	/**
	 * TODO (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 19.02.2009, 19:51:48
	 *
	 * @param poInternalSystem
	 */
	public clsDamageLightning(clsInternalSystem poInternalSystem, clsConfigMap poConfig) {
		moConfig = getFinalConfig(poConfig);		
		applyConfig();
		
		moHealthSystem = poInternalSystem.getHealthSystem();
		moFastMessengerSystem = poInternalSystem.getFastMessengerSystem();
		
		moFastMessengerSystem.addMapping(new clsPartDamageBump(), new clsPartBrain());
	}	
	
	
	private void applyConfig() {
		
		mrPainThreshold = ((clsConfigDouble)moConfig.get(eConfigEntries.PAINTHRESHOLD)).get();
		mrHealthPenalty = ((clsConfigDouble)moConfig.get(eConfigEntries.HEALTHPENALTY)).get();
		mrHurtThreshold = ((clsConfigDouble)moConfig.get(eConfigEntries.HURTTHRESHOLD)).get();	
	}
	
	private static clsConfigMap getFinalConfig(clsConfigMap poConfig) {
		clsConfigMap oDefault = getDefaultConfig();
		oDefault.overwritewith(poConfig);
		return oDefault;
	}
	
	private static clsConfigMap getDefaultConfig() {
		clsConfigMap oDefault = new clsConfigMap();
		
		oDefault.add(eConfigEntries.PAINTHRESHOLD, new clsConfigDouble(0.0f));
		oDefault.add(eConfigEntries.HEALTHPENALTY, new clsConfigDouble(1.0f));
		oDefault.add(eConfigEntries.HURTTHRESHOLD, new clsConfigDouble(0.0f));
		
		return oDefault;
	}
	
    /**
     * TODO (deutsch) - insert description
     *
     */
    public void step() {
    	
    }
}
