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
	
	/**
	 * @param poSource
	 * @param poTarget
	 * @param prIntensity
	 */
	public clsFastMessengerEntry(eBodyParts poSource, eBodyParts poTarget, double prIntensity) {
		moSource = poSource;
		moTarget = poTarget;
		mrIntensity = prIntensity;
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
