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
		moConfig = getDefaultConfig();
		moConfig.overwritewith(poConfig);
		
    	
   	   moBioSystem = new clsBodyColor();
	   moGrowthSystem = new clsGrowth();  
	   moDamageNutrition = new clsDamageNutrition(poInternalSystem);
	   moDamageTemperature = new clsDamageTemperature(poInternalSystem);
    }
    
	private clsConfigMap getDefaultConfig() {
		clsConfigMap oDefault = new clsConfigMap();
		//TODO add default values
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
//     	moDamageNutrition.stepUpdateInternalState();
//    	moDamageTemperature.stepUpdateInternalState();
    }
}
