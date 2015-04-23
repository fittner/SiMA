/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package complexbody.interBodyWorldSystems;

import properties.clsProperties;
import body.itfStepUpdateInternalState;

import complexbody.internalSystems.clsInternalSystem;

import entities.abstractEntities.clsEntity;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsInterBodyWorldSystem implements itfStepUpdateInternalState {
	public static final String P_CONSUMEFOOD = "consumefood";	
	public static final String P_DAMAGEBUMP = "damagebump";	
	public static final String P_DAMAGELIGHTNING = "damagelightning";	
	public static final String P_EFFECTKISS = "effectkiss";
	public static final String P_CREATEEXCREMENT = "createexcrement";
	public static final String P_CREATESPEECH = "createspeech"; // MW 
	
	private clsConsumeFood moConsumeFood;
	private clsDamageBump moDamageBump;
	private clsDamageLightning moDamageLightning;
	private clsCreateExcrement moCreateExcrement;
	private clsEffectKiss moEffectKiss;
	private clsCreateSpeech moCreateSpeech; // MW 
    
	public clsInterBodyWorldSystem(String poPrefix, clsProperties poProp, clsInternalSystem poInternalSystem, clsEntity poEntity) {
		applyProperties(poPrefix, poProp, poInternalSystem, poEntity);
	}

	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();

		oProp.putAll( clsConsumeFood.getDefaultProperties(pre+P_CONSUMEFOOD) );
		oProp.putAll( clsDamageBump.getDefaultProperties(pre+P_DAMAGEBUMP) );
		oProp.putAll( clsDamageLightning.getDefaultProperties(pre+P_DAMAGELIGHTNING) );
		oProp.putAll( clsCreateExcrement.getDefaultProperties(pre+P_CREATEEXCREMENT) );
		oProp.putAll( clsEffectKiss.getDefaultProperties(pre+P_EFFECTKISS) );
		oProp.putAll( clsCreateSpeech.getDefaultProperties(pre+P_CREATESPEECH) ); // MW 	
				
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsProperties poProp, clsInternalSystem poInternalSystem, clsEntity poEntity) {
		String pre = clsProperties.addDot(poPrefix);

		moConsumeFood 		= new clsConsumeFood(pre+P_CONSUMEFOOD, poProp, poInternalSystem.getStomachSystem());
		moCreateExcrement 	= new clsCreateExcrement(pre+P_CREATEEXCREMENT, poProp, poInternalSystem.getStomachSystem(), poEntity);		
		moDamageBump 		= new clsDamageBump(pre+P_DAMAGEBUMP, poProp, poInternalSystem.getHealthSystem(), poInternalSystem.getFastMessengerSystem(), poInternalSystem.getSlowMessengerSystem());
		moDamageLightning 	= new clsDamageLightning(pre+P_DAMAGELIGHTNING, poProp, poInternalSystem.getHealthSystem(), poInternalSystem.getFastMessengerSystem(), poInternalSystem.getSlowMessengerSystem());
		moEffectKiss 		= new clsEffectKiss(pre+P_EFFECTKISS, poProp,  poInternalSystem.getFastMessengerSystem(),poInternalSystem.getSlowMessengerSystem());
		moCreateSpeech 		= new clsCreateSpeech(pre+P_CREATESPEECH, poProp, poInternalSystem.getSpeechSystem(), poEntity); // MW 
	}		
		
	public clsConsumeFood getConsumeFood() {
		return moConsumeFood;
	}
	
	public clsDamageBump getDamageBump() {
		return moDamageBump;
	}
	
	public clsDamageLightning getDamageLightning() {
		return moDamageLightning;
	}
	
	public clsCreateExcrement getCreateExcrement() {
		return moCreateExcrement;
	}
	
	public clsEffectKiss getEffectKiss() {
		return moEffectKiss;
	}
	
	
	// **  MW 
	public clsCreateSpeech getCreateSpeech() {
		return moCreateSpeech;
	}
	// MW **
	
	/* (non-Javadoc)
	 * @see bw.body.itfStep#step()
	 */
	@Override
	public void stepUpdateInternalState() {
		// TODO (deutsch) - Auto-generated method stub
		
	}

}
