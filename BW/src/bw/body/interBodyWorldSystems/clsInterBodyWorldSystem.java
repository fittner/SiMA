/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.interBodyWorldSystems;

import bw.body.itfStepUpdateInternalState;
import bw.body.internalSystems.clsInternalSystem;
import bw.utils.config.clsBWProperties;

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
	
	private clsConsumeFood moConsumeFood;
	private clsDamageBump moDamageBump;
	private clsDamageLightning moDamageLightning;
    
	public clsInterBodyWorldSystem(String poPrefix, clsBWProperties poProp, clsInternalSystem poInternalSystem) {
		applyProperties(poPrefix, poProp, poInternalSystem);
	}

	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();

		oProp.putAll( clsConsumeFood.getDefaultProperties(pre+P_CONSUMEFOOD) );
		oProp.putAll( clsDamageBump.getDefaultProperties(pre+P_DAMAGEBUMP) );
		oProp.putAll( clsDamageLightning.getDefaultProperties(pre+P_DAMAGELIGHTNING) );
				
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp, clsInternalSystem poInternalSystem) {
		String pre = clsBWProperties.addDot(poPrefix);

		moConsumeFood 		= new clsConsumeFood(pre+P_CONSUMEFOOD, poProp, poInternalSystem.getStomachSystem());
		moDamageBump 		= new clsDamageBump(pre+P_DAMAGEBUMP, poProp, poInternalSystem.getHealthSystem(), poInternalSystem.getFastMessengerSystem());
		moDamageLightning 	= new clsDamageLightning(pre+P_DAMAGELIGHTNING, poProp, poInternalSystem.getHealthSystem(), poInternalSystem.getFastMessengerSystem());
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
	
	/* (non-Javadoc)
	 * @see bw.body.itfStep#step()
	 */
	public void stepUpdateInternalState() {
		// TODO Auto-generated method stub
		
	}

}
