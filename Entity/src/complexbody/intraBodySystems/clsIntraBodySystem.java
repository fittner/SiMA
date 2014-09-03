/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package complexbody.intraBodySystems;

import properties.clsProperties;
import complexbody.internalSystems.clsInternalSystem;

import entities.abstractEntities.clsEntity;

import body.itfStepUpdateInternalState;


/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsIntraBodySystem implements itfStepUpdateInternalState{
	public static final String P_BODYCOLOR = "bodycolor";	
	public static final String P_GROWTHSYSTEM = "growthsystem";	
	public static final String P_DAMAGETEMPERATURE = "damagetemperature";	
	public static final String P_FACIALEXPRESSION = "facialexpression";
	public static final String P_SPEECHEXPRESSION = "speechexpression";
	public static final String P_STOMACHDAMAGENUTRITION = "damagenutrition";	
	public static final String P_STOMACHDAMAGETENSION = "damagetension";
	public static final String P_STOMACHTOSLOWMESSENGER = "stomachtoslowmessenger";
	public static final String P_EROGENOUSZONESSYSTEM = "erogenouszonessystem";
	
    private clsBodyColor moColorSystem;
    private clsGrowth moGrowthSystem;
    private clsDamageTemperature moDamageTemperature;
    private clsFacialExpression moFacialExpression;
    private clsSpeechExpression moSpeechExpression;
    private clsStomachDamageNutrition moStomachDamageNutrition;
    private clsStomachPainTension moStomachDamageTension;
    private clsStomachToSlowMessenger moStomachToSlowMessenger;
    private clsErogenousZonesSystem moErogenousZonesSystem;
    
    public clsIntraBodySystem(String poPrefix, clsProperties poProp, clsInternalSystem poInternalSystem, clsEntity poEntity) {
		applyProperties(poPrefix, poProp, poInternalSystem, poEntity);
	}

	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();

		oProp.putAll( clsBodyColor.getDefaultProperties(pre+P_BODYCOLOR) );
		oProp.putAll( clsGrowth.getDefaultProperties(pre+P_GROWTHSYSTEM) );
		oProp.putAll( clsStomachDamageNutrition.getDefaultProperties(pre+P_STOMACHDAMAGENUTRITION) );
		oProp.putAll( clsStomachPainTension.getDefaultProperties(pre+P_STOMACHDAMAGETENSION) );
		oProp.putAll( clsStomachToSlowMessenger.getDefaultProperties(pre+P_STOMACHTOSLOWMESSENGER) );
		oProp.putAll( clsDamageTemperature.getDefaultProperties(pre+P_DAMAGETEMPERATURE) );
		oProp.putAll( clsFacialExpression.getDefaultProperties(pre+P_FACIALEXPRESSION) );
		oProp.putAll( clsFacialExpression.getDefaultProperties(pre+P_SPEECHEXPRESSION) );
		oProp.putAll( clsErogenousZonesSystem.getDefaultProperties(pre+P_EROGENOUSZONESSYSTEM) );
				
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsProperties poProp, clsInternalSystem poInternalSystem, clsEntity poEntity) {
		String pre = clsProperties.addDot(poPrefix);

		moColorSystem 				= new clsBodyColor(pre+P_BODYCOLOR, poProp, poEntity);
		moGrowthSystem 				= new clsGrowth(pre+P_GROWTHSYSTEM, poProp);
		moFacialExpression 			= new clsFacialExpression(pre+P_FACIALEXPRESSION, poProp, poEntity);
		moSpeechExpression 			= new clsSpeechExpression(pre+P_SPEECHEXPRESSION, poProp, poEntity);
		moStomachDamageNutrition 	= new clsStomachDamageNutrition(pre+P_STOMACHDAMAGENUTRITION, poProp, poInternalSystem.getHealthSystem(), poInternalSystem.getStomachSystem(), poInternalSystem.getFastMessengerSystem());
		moStomachDamageTension 		= new clsStomachPainTension(pre+P_STOMACHDAMAGETENSION,	poProp, poInternalSystem.getStomachSystem(), poInternalSystem.getFastMessengerSystem());		
		moStomachToSlowMessenger 	= new clsStomachToSlowMessenger(pre+P_STOMACHTOSLOWMESSENGER, poProp, poInternalSystem.getStomachSystem(), poInternalSystem.getSlowMessengerSystem());		
		moDamageTemperature 		= new clsDamageTemperature(pre+P_DAMAGETEMPERATURE, poProp, poInternalSystem.getHealthSystem(), poInternalSystem.getTemperatureSystem(), poInternalSystem.getFastMessengerSystem());
		moErogenousZonesSystem 		= new clsErogenousZonesSystem(pre+P_EROGENOUSZONESSYSTEM,	poProp, poEntity , poInternalSystem.getFastMessengerSystem());
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
	public clsStomachDamageNutrition getDamageNutrition() {
		return moStomachDamageNutrition;
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
	
	public clsSpeechExpression getSpeechExpression()  {
		return moSpeechExpression;
	}

	public clsStomachPainTension getStomachDamageTension() {
		return moStomachDamageTension;
	}
	
	public clsStomachToSlowMessenger getStomachToSlowMessenger() {
		return moStomachToSlowMessenger;
	}
	
	public clsErogenousZonesSystem getErogenousZonesSystem() {
		return moErogenousZonesSystem;
	}

	/**
     * DOCUMENT (deutsch) - insert description
     *
     */
    @Override
	public void stepUpdateInternalState() {
    	moFacialExpression.stepUpdateInternalState(); // Facial Expressions added! -volkan
    	moColorSystem.stepUpdateInternalState();
    	moGrowthSystem.stepUpdateInternalState();
     	moStomachDamageNutrition.stepUpdateInternalState();
     	moStomachDamageTension.stepUpdateInternalState();
     	moStomachToSlowMessenger.stepUpdateInternalState();
    	moDamageTemperature.stepUpdateInternalState();
    	moErogenousZonesSystem.stepUpdateInternalState();
    }
}

