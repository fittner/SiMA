/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.intraBodySystems;

import bw.body.interBodyWorldSystems.clsDamageBump;
import bw.body.interBodyWorldSystems.clsDamageLightning;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsIntraBodySystem {
    private clsBodyColor moBioSystem;
    private clsGrowth moGrowthSystem;
    private clsDamageBump moDamageBump;
    private clsDamageLightning moDamageLightning;
    private clsDamageNutrition moDamageNutrition;
    private clsDamageTemperature moDamageTemperature;

    public clsIntraBodySystem() {
   	   moBioSystem = new clsBodyColor();
	   moGrowthSystem = new clsGrowth();  
	   
	   moDamageBump = new clsDamageBump();
	   moDamageLightning = new clsDamageLightning();
	   moDamageNutrition = new clsDamageNutrition();
	   moDamageTemperature = new clsDamageTemperature();
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
	 * @return the moDamageBump
	 */
	public clsDamageBump getDamageBump() {
		return moDamageBump;
	}



	/**
	 * @return the moDamageLightning
	 */
	public clsDamageLightning getDamageLightning() {
		return moDamageLightning;
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
 
    	moDamageBump.step();
    	moDamageLightning.step();
    	moDamageNutrition.step();
    	moDamageTemperature.step();
    }
}
