/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.intraBodySystems;

import config.clsBWProperties;
import bw.body.itfStepUpdateInternalState;
import bw.body.internalSystems.clsInternalSystem;
import bw.entities.clsEntity;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsIntraBodySystem implements itfStepUpdateInternalState{
	public static final String P_BODYCOLOR = "bodycolor";	
	public static final String P_GROWTHSYSTEM = "growthsystem";	
	public static final String P_DAMAGENUTRITION = "damagenutrition";	
	public static final String P_DAMAGETEMPERATURE = "damagetemperature";	
	public static final String P_FACIALEXPRESSION = "facialexpression";
	
    private clsBodyColor moColorSystem;
    private clsGrowth moGrowthSystem;
    private clsDamageNutrition moDamageNutrition;
    private clsDamageTemperature moDamageTemperature;
    private clsFacialExpression moFacialExpression;
    
    public clsIntraBodySystem(String poPrefix, clsBWProperties poProp, clsInternalSystem poInternalSystem, clsEntity poEntity) {
		applyProperties(poPrefix, poProp, poInternalSystem, poEntity);
	}

	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();

		oProp.putAll( clsBodyColor.getDefaultProperties(pre+P_BODYCOLOR) );
		oProp.putAll( clsGrowth.getDefaultProperties(pre+P_GROWTHSYSTEM) );
		oProp.putAll( clsDamageNutrition.getDefaultProperties(pre+P_DAMAGENUTRITION) );
		oProp.putAll( clsDamageTemperature.getDefaultProperties(pre+P_DAMAGETEMPERATURE) );
		oProp.putAll( clsFacialExpression.getDefaultProperties(pre+P_FACIALEXPRESSION) );
				
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp, clsInternalSystem poInternalSystem, clsEntity poEntity) {
		String pre = clsBWProperties.addDot(poPrefix);

		moColorSystem 		= new clsBodyColor(pre+P_BODYCOLOR, poProp, poEntity);
		moGrowthSystem 		= new clsGrowth(pre+P_GROWTHSYSTEM, poProp);
		moFacialExpression 		= new clsFacialExpression(pre+P_FACIALEXPRESSION, poProp, poEntity);
		moDamageNutrition 		= new clsDamageNutrition(pre+P_DAMAGENUTRITION, poProp, poInternalSystem.getHealthSystem(), poInternalSystem.getStomachSystem(), poInternalSystem.getFastMessengerSystem());
		moDamageTemperature 	= new clsDamageTemperature(pre+P_DAMAGETEMPERATURE, poProp, poInternalSystem.getHealthSystem(), poInternalSystem.getTemperatureSystem(), poInternalSystem.getFastMessengerSystem());
	}	
	    
    /**
	 * @return the moBioSystem
	 */
	public clsBodyColor getColorSystem() {
		return moColorSystem;
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

	public clsFacialExpression getFacialExpression()  {
		return moFacialExpression;
	}


	/**
     * DOCUMENT (deutsch) - insert description
     *
     */
    public void stepUpdateInternalState() {
    	//moFacialExpression.stepUpdateInternalState(); - nothing to do!!!
    	moColorSystem.stepUpdateInternalState();
    	moGrowthSystem.stepUpdateInternalState();
     	moDamageNutrition.stepUpdateInternalState();
    	moDamageTemperature.stepUpdateInternalState();
    }
}
