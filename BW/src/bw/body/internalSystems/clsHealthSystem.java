/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.internalSystems;

import bw.body.itfStep;
import bw.exceptions.ContentColumnMaxContentExceeded;
import bw.exceptions.ContentColumnMinContentUnderrun;
import bw.utils.tools.clsFillLevel;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsHealthSystem implements itfStep {
	private clsFillLevel moHealth;
	boolean mnIsAlive;
	private float mrDefaultIsDeadThreshold = 0.001f;
	
	private float mrDefaultContent = 10.0f;
	private float mrDefaultMaxContent = 10.0f;
	private float mrDefaultSelfHealingRate = 0.05f;
	
	public clsHealthSystem() {
		moHealth = null;
		
		try {
			moHealth = new clsFillLevel(mrDefaultContent, mrDefaultMaxContent, mrDefaultSelfHealingRate);
		} catch (ContentColumnMaxContentExceeded e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ContentColumnMinContentUnderrun e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mnIsAlive = true;
	}
	
	public void hurt(float prHealthRemoved) {
		try {
			moHealth.decrease(prHealthRemoved);
		} catch (ContentColumnMaxContentExceeded e) {
		} catch (ContentColumnMinContentUnderrun e) {
		}
		updateIsAlive();
	}
	
	public void heal(float prHealthRegained) {
		try {
			moHealth.increase(prHealthRegained);
		} catch (ContentColumnMaxContentExceeded e) {
		} catch (ContentColumnMinContentUnderrun e) {
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
		if (moHealth.getContent()<mrDefaultIsDeadThreshold) {
			mnIsAlive = false;
		}
	}
	
	public void step() {
		try {
			moHealth.update();
		} catch (ContentColumnMaxContentExceeded e) {
		} catch (ContentColumnMinContentUnderrun e) {
		}
		
		updateIsAlive();
	}
}
