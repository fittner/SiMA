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
import bw.utils.container.clsConfigMap;
import bw.utils.enums.eConfigEntries;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsInterBodyWorldSystem implements itfStepUpdateInternalState {
	private clsConsumeFood moConsumeFood;
	private clsDamageBump moDamageBump;
	private clsDamageLightning moDamageLightning;
    
    private clsConfigMap moConfig;
    
	public clsInterBodyWorldSystem(clsInternalSystem poInternalSystem, clsConfigMap poConfig) {
		moConfig = getFinalConfig(poConfig);		
		applyConfig();
		
		moConsumeFood = new clsConsumeFood(poInternalSystem.getStomachSystem(), (clsConfigMap) moConfig.get(eConfigEntries.INTER_CONSUME_FOOD));
		moDamageBump = new clsDamageBump(poInternalSystem, (clsConfigMap) moConfig.get(eConfigEntries.INTER_DAMAGE_BUMP));
		moDamageLightning = new clsDamageLightning(poInternalSystem, (clsConfigMap) moConfig.get(eConfigEntries.INTER_DAMAGE_LIGHTNING));
	}
	
	private void applyConfig() {
		
		//TODO add custom code
	}
	
	private static clsConfigMap getFinalConfig(clsConfigMap poConfig) {
		clsConfigMap oDefault = getDefaultConfig();
		oDefault.overwritewith(poConfig);
		return oDefault;
	}
	
	private static clsConfigMap getDefaultConfig() {
		clsConfigMap oDefault = new clsConfigMap();
		
		oDefault.add(eConfigEntries.INTER_DAMAGE_BUMP, null);
		oDefault.add(eConfigEntries.INTER_DAMAGE_LIGHTNING, null);
		oDefault.add(eConfigEntries.INTER_CONSUME_FOOD, null);
		
		return oDefault;
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
