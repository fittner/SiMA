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
import bw.utils.config.clsBWProperties;
import bw.utils.tools.clsContentColumn;
import bw.utils.tools.clsFillLevel;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsHealthSystem implements itfStepUpdateInternalState {
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
	private static final double mrIsDeadThreshold = 0.001;
	
	public clsHealthSystem(String poPrefix, clsBWProperties poProp) {
		applyProperties(poPrefix, poProp);
		updateIsAlive();
	}

	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.setProperty(pre+clsContentColumn.P_CONTENT, 100);
		oProp.setProperty(pre+clsContentColumn.P_MAXCONTENT, 100);
		oProp.setProperty(pre+clsFillLevel.P_CHANGE, 0.05);
		oProp.setProperty(pre+clsFillLevel.P_LOWERBOUND, 10);
		oProp.setProperty(pre+clsFillLevel.P_UPPERBOUND, 90);	
	
		
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		moHealth = new clsFillLevel(pre, poProp);
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
