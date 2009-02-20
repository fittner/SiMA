/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.intraBodySystems;

import bw.body.internalSystems.clsInternalSystem;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsIntraBodySystem {
    private clsBodyColor moBioSystem;
    private clsGrowth moGrowthSystem;
    private clsDamageNutrition moDamageNutrition;
    private clsDamageTemperature moDamageTemperature;

    public clsIntraBodySystem(clsInternalSystem poInternalSystem) {
   	   moBioSystem = new clsBodyColor();
	   moGrowthSystem = new clsGrowth();  
	   moDamageNutrition = new clsDamageNutrition(poInternalSystem);
	   moDamageTemperature = new clsDamageTemperature(poInternalSystem);
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
    public void step() {
    	moBioSystem.step();
    	moGrowthSystem.step();
//     	moDamageNutrition.step();
//    	moDamageTemperature.step();
    }
}
