/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package complexbody.internalSystems;

import entities.enums.eBodyParts;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsFastMessengerEntry {
	private clsFastMessengerKeyTuple moFromTo;
	private double mrIntensity;
	private int mnCountDown;
	
	/**
	 * @param poSource
	 * @param poTarget
	 * @param prIntensity
	 */
	public clsFastMessengerEntry(eBodyParts poSource, eBodyParts poTarget, double prIntensity, int pnStartTimerValue) {
		moFromTo = new clsFastMessengerKeyTuple(poSource, poTarget);
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
		return moFromTo.meTarget;
	}
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @return
	 */
	public eBodyParts getSource() {
		return moFromTo.meSource;
	}	
	
	public clsFastMessengerKeyTuple getFromTo() {
		return moFromTo;
	}
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @return
	 */
	public double getIntensity() {
		return mrIntensity;
	}
	
	@Override
	public String toString() {
		return moFromTo+": "+mrIntensity+" ("+mnCountDown+")";
	}
	
}
