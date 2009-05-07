/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.internalSystems;

import bw.body.itfStepUpdateInternalState;
import bw.exceptions.exContentColumnMaxContentExceeded;
import bw.exceptions.exContentColumnMinContentUnderrun;
import bw.utils.container.clsConfigMap;
import bw.utils.container.clsConfigFloat;
import bw.utils.enums.eConfigEntries;
import bw.utils.tools.clsFillLevel;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsHealthSystem implements itfStepUpdateInternalState {
    private clsConfigMap moConfig;
    
	private clsFillLevel moHealth;
	boolean mnIsAlive;
	private float mrIsDeadThreshold = 0.001f;
	
	public clsHealthSystem(clsConfigMap poConfig) {
		applyConfig(poConfig);		
		
		moHealth = null;
		
		try {
			moHealth = new clsFillLevel(
					((clsConfigFloat)moConfig.get(eConfigEntries.CONTENT)).get(), 
					((clsConfigFloat)moConfig.get(eConfigEntries.MAXCONTENT)).get(), 
					((clsConfigFloat)moConfig.get(eConfigEntries.SELFHEALINGRATE)).get()
					);
		} catch (exContentColumnMaxContentExceeded e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (exContentColumnMinContentUnderrun e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mnIsAlive = true;
	}
	
	private void applyConfig(clsConfigMap poConfig) {
		moConfig = getDefaultConfig();
		moConfig.overwritewith(poConfig);	
		
		mrIsDeadThreshold = ((clsConfigFloat)moConfig.get(eConfigEntries.ISDEADTHRESHOLD)).get();
	}

	private clsConfigMap getDefaultConfig() {
		clsConfigMap oDefault = new clsConfigMap();
		
		oDefault.add(eConfigEntries.ISDEADTHRESHOLD, new clsConfigFloat(0.001f));
		oDefault.add(eConfigEntries.CONTENT, new clsConfigFloat(10.0f));
		oDefault.add(eConfigEntries.MAXCONTENT, new clsConfigFloat(10.0f));
		oDefault.add(eConfigEntries.SELFHEALINGRATE, new clsConfigFloat(0.05f));
		
		return oDefault;
	}	
	
	public void hurt(float prHealthRemoved) {
		try {
			moHealth.decrease(prHealthRemoved);
		} catch (exContentColumnMaxContentExceeded e) {
		} catch (exContentColumnMinContentUnderrun e) {
		}
		updateIsAlive();
	}
	
	public void heal(float prHealthRegained) {
		try {
			moHealth.increase(prHealthRegained);
		} catch (exContentColumnMaxContentExceeded e) {
		} catch (exContentColumnMinContentUnderrun e) {
		}
		updateIsAlive();
	}
	
	public float getRecoveryRate() {
		return moHealth.getChange();
	}
	
	public void setRecoveryRate(float prRecoveryRate) {
		if (prRecoveryRate < 0.0f) {
			prRecoveryRate = 0.0f;
		}
		
		moHealth.setChange(prRecoveryRate);
	}
	
	private void updateIsAlive() {
		if (moHealth.getContent()<mrIsDeadThreshold) {
			mnIsAlive = false;
		}
	}
	
	public void stepUpdateInternalState() {
		try {
			moHealth.update();
		} catch (exContentColumnMaxContentExceeded e) {
		} catch (exContentColumnMinContentUnderrun e) {
		}
		
		updateIsAlive();
	}
}
