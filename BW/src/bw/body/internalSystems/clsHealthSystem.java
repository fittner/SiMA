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
import bw.utils.container.clsConfigDouble;
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
	/**
	 * @author langr
	 * 12.05.2009, 18:25:12
	 * 
	 * @return the moHealth
	 */
	public clsFillLevel getHealth() {
		return moHealth;
	}

	boolean mnIsAlive;
	private double mrIsDeadThreshold = 0.001;
	
	public clsHealthSystem(clsConfigMap poConfig) {
		moConfig = getFinalConfig(poConfig);
		applyConfig();		
		
		moHealth = null;
		
		try {
			moHealth = new clsFillLevel(
					((clsConfigDouble)moConfig.get(eConfigEntries.CONTENT)).get(), 
					((clsConfigDouble)moConfig.get(eConfigEntries.MAXCONTENT)).get(), 
					((clsConfigDouble)moConfig.get(eConfigEntries.SELFHEALINGRATE)).get()
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
	
	private void applyConfig() {
		
		mrIsDeadThreshold = ((clsConfigDouble)moConfig.get(eConfigEntries.ISDEADTHRESHOLD)).get();
	}

	private static clsConfigMap getFinalConfig(clsConfigMap poConfig) {
		clsConfigMap oDefault = getDefaultConfig();
		oDefault.overwritewith(poConfig);
		return oDefault;
	}
	
	private static clsConfigMap getDefaultConfig() {
		clsConfigMap oDefault = new clsConfigMap();
		
		oDefault.add(eConfigEntries.ISDEADTHRESHOLD, new clsConfigDouble(0.001f));
		oDefault.add(eConfigEntries.CONTENT, new clsConfigDouble(10.0f));
		oDefault.add(eConfigEntries.MAXCONTENT, new clsConfigDouble(10.0f));
		oDefault.add(eConfigEntries.SELFHEALINGRATE, new clsConfigDouble(0.05f));
		
		return oDefault;
	}	
	
	public void hurt(double prHealthRemoved) {
		try {
			moHealth.decrease(prHealthRemoved);
		} catch (exContentColumnMaxContentExceeded e) {
		} catch (exContentColumnMinContentUnderrun e) {
		}
		updateIsAlive();
	}
	
	public void heal(double prHealthRegained) {
		try {
			moHealth.increase(prHealthRegained);
		} catch (exContentColumnMaxContentExceeded e) {
		} catch (exContentColumnMinContentUnderrun e) {
		}
		updateIsAlive();
	}
	
	public double getRecoveryRate() {
		return moHealth.getChange();
	}
	
	public void setRecoveryRate(double prRecoveryRate) {
		if (prRecoveryRate < 0.0f) {
			prRecoveryRate = 0.0;
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
