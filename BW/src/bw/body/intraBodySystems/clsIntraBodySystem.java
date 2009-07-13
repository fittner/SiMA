/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.intraBodySystems;

import bw.body.itfStepUpdateInternalState;
import bw.body.internalSystems.clsInternalSystem;
import bw.utils.container.clsConfigMap;
import bw.utils.enums.eConfigEntries;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsIntraBodySystem implements itfStepUpdateInternalState{
    private clsBodyColor moBioSystem;
    private clsGrowth moGrowthSystem;
    private clsDamageNutrition moDamageNutrition;
    private clsDamageTemperature moDamageTemperature;
    
    private clsConfigMap moConfig;
    
    public clsIntraBodySystem(clsInternalSystem poInternalSystem, clsConfigMap poConfig) {
    	moConfig = getFinalConfig(poConfig);    	
		applyConfig();
		    	
   	    moBioSystem = new clsBodyColor((clsConfigMap) moConfig.get(eConfigEntries.INTRA_DAMAGE_NUTRITION));
	    moGrowthSystem = new clsGrowth((clsConfigMap) moConfig.get(eConfigEntries.INTRA_DAMAGE_TEMPERATURE));  
	    moDamageNutrition = new clsDamageNutrition(poInternalSystem, (clsConfigMap) moConfig.get(eConfigEntries.INTRA_BODYCOLOR));
	    moDamageTemperature = new clsDamageTemperature(poInternalSystem, (clsConfigMap) moConfig.get(eConfigEntries.INTRA_GROWTH));
    }
    
	private void applyConfig() {
		//TODO add ...

	}

	private static clsConfigMap getFinalConfig(clsConfigMap poConfig) {
		clsConfigMap oDefault = getDefaultConfig();
		oDefault.overwritewith(poConfig);
		return oDefault;
	}
	
	private static clsConfigMap getDefaultConfig() {
		clsConfigMap oDefault = new clsConfigMap();
		
		oDefault.add(eConfigEntries.INTRA_DAMAGE_NUTRITION, null);
		oDefault.add(eConfigEntries.INTRA_DAMAGE_TEMPERATURE, null);
		oDefault.add(eConfigEntries.INTRA_BODYCOLOR, null);
		oDefault.add(eConfigEntries.INTRA_GROWTH, null);
		
		return oDefault;
	}	
	    
    
    /**
	 * @return the moBioSystem
	 */
	public clsBodyColor getBioSystem() {
		return moBioSystem;
	}



	/**
	 * @return the moGrowthSystem
	 */
	public clsGrowth getGrowthSystem() {
		return moGrowthSystem;
	}


	/**
	 * @return the moDamageNutrition
	 */
	public clsDamageNutrition getDamageNutrition() {
		return moDamageNutrition;
	}



	/**
	 * @return the moDamageTemperature
	 */
	public clsDamageTemperature getDamageTemperature() {
		return moDamageTemperature;
	}



	/**
     * TODO (deutsch) - insert description
     *
     */
    public void stepUpdateInternalState() {
    	moBioSystem.stepUpdateInternalState();
    	moGrowthSystem.stepUpdateInternalState();
     	moDamageNutrition.stepUpdateInternalState();
    	moDamageTemperature.stepUpdateInternalState();
    }
}
