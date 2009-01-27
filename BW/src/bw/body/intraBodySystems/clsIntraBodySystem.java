/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.intraBodySystems;

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
