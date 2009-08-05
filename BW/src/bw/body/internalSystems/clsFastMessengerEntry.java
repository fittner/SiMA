/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.internalSystems;

import bw.utils.enums.eBodyParts;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsFastMessengerEntry {
	private eBodyParts moSource;
	private eBodyParts moTarget;
	private double mrIntensity;
	private int mnCountDown;
	
	/**
	 * @param poSource
	 * @param poTarget
	 * @param prIntensity
	 */
	public clsFastMessengerEntry(eBodyParts poSource, eBodyParts poTarget, double prIntensity, int pnStartTimerValue) {
		moSource = poSource;
		moTarget = poTarget;
		mrIntensity = prIntensity;
		mnCountDown = pnStartTimerValue;
	}
	
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @author deutsch
	 * 05.08.2009, 12:08:27
	 *
	 * @return
	 */
	public boolean timerExpired() {
		return (mnCountDown <= 0);
	}
	
	public void decTimer() {
		mnCountDown--;
	}
	
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @return
	 */
	public eBodyParts getTarget() {
		return moTarget;
	}
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @return
	 */
	public eBodyParts getSource() {
		return moSource;
	}	
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @return
	 */
	public double getIntensity() {
		return mrIntensity;
	}
	
}
