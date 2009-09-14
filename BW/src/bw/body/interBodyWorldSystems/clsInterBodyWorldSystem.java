/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.interBodyWorldSystems;

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
public class clsInterBodyWorldSystem implements itfStepUpdateInternalState {
	public static final String P_CONSUMEFOOD = "consumefood";	
	public static final String P_DAMAGEBUMP = "damagebump";	
	public static final String P_DAMAGELIGHTNING = "damagelightning";	
	public static final String P_EFFECTKISS = "effectkiss";
	public static final String P_CREATEEXCREMENT = "createexcrement";
	
	private clsConsumeFood moConsumeFood;
	private clsDamageBump moDamageBump;
	private clsDamageLightning moDamageLightning;
	private clsCreateExcrement moCreateExcrement;
	private clsEffectKiss moEffectKiss;
    
	public clsInterBodyWorldSystem(String poPrefix, clsBWProperties poProp, clsInternalSystem poInternalSystem, clsEntity poEntity) {
		applyProperties(poPrefix, poProp, poInternalSystem, poEntity);
	}

	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();

		oProp.putAll( clsConsumeFood.getDefaultProperties(pre+P_CONSUMEFOOD) );
		oProp.putAll( clsDamageBump.getDefaultProperties(pre+P_DAMAGEBUMP) );
		oProp.putAll( clsDamageLightning.getDefaultProperties(pre+P_DAMAGELIGHTNING) );
		oProp.putAll( clsCreateExcrement.getDefaultProperties(pre+P_CREATEEXCREMENT) );
		oProp.putAll( clsEffectKiss.getDefaultProperties(pre+P_EFFECTKISS) );
				
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp, clsInternalSystem poInternalSystem, clsEntity poEntity) {
		String pre = clsBWProperties.addDot(poPrefix);

		moConsumeFood 		= new clsConsumeFood(pre+P_CONSUMEFOOD, poProp, poInternalSystem.getStomachSystem());
		moCreateExcrement 		= new clsCreateExcrement(pre+P_CREATEEXCREMENT, poProp, poInternalSystem.getStomachSystem(), poEntity);		
		moDamageBump 		= new clsDamageBump(pre+P_DAMAGEBUMP, poProp, poInternalSystem.getHealthSystem(), poInternalSystem.getFastMessengerSystem());
		moDamageLightning 	= new clsDamageLightning(pre+P_DAMAGELIGHTNING, poProp, poInternalSystem.getHealthSystem(), poInternalSystem.getFastMessengerSystem());
		moEffectKiss 		= new clsEffectKiss(pre+P_EFFECTKISS, poProp,  poInternalSystem.getFastMessengerSystem(),poInternalSystem.getSlowMessengerSystem());
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
	
	/* (non-Javadoc)
	 * @see bw.body.itfStep#step()
	 */
	public void stepUpdateInternalState() {
		// TODO (deutsch) - Auto-generated method stub
		
	}

}
