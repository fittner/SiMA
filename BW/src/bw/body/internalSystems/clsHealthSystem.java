/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.internalSystems;

import bw.utils.tools.clsFillLevel;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsHealthSystem {
	private clsFillLevel moHealth;
	
	public clsHealthSystem() {
		moHealth = new clsFillLevel(1.0f, 1.0f, 0.05f);
	}
	
	public void hurt(float prHealthRemoved) {
		moHealth.decrease(prHealthRemoved);
	}
	
	public void heal(float prHealthRegained) {
		moHealth.increase(prHealthRegained);
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
	
	public void step() {
		moHealth.update();
	}
}
