/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package complexbody.internalSystems;

import properties.clsProperties;
import utils.exceptions.exContentColumnMaxContentExceeded;
import utils.exceptions.exContentColumnMinContentUnderrun;
import body.itfStepUpdateInternalState;
import body.utils.clsContentColumn;
import body.utils.clsFillLevel;

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

	private boolean mnIsAlive;
	private static final double mrIsDeadThreshold = 0.001;
	
	public clsHealthSystem(String poPrefix, clsProperties poProp) {
		applyProperties(poPrefix, poProp);
		mnIsAlive = true;
		updateIsAlive();
	}

	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
		oProp.setProperty(pre+clsContentColumn.P_CONTENT, 100);
		oProp.setProperty(pre+clsContentColumn.P_MAXCONTENT, 100);
		oProp.setProperty(pre+clsFillLevel.P_CHANGE, 0.05);
		oProp.setProperty(pre+clsFillLevel.P_LOWERBOUND, 10);
		oProp.setProperty(pre+clsFillLevel.P_UPPERBOUND, 90);	
	
		
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
		
		moHealth = new clsFillLevel(pre, poProp);
	}
	
	public boolean getIsAlive() {
		return mnIsAlive;
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
	
	@Override
	public void stepUpdateInternalState() {
		try {
			moHealth.update();
		} catch (exContentColumnMaxContentExceeded e) {
		} catch (exContentColumnMinContentUnderrun e) {
		}
		
		updateIsAlive();
	}

	

}
