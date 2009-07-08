/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.internalSystems;

import bw.utils.enums.partclass.clsBasePart;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsFastMessengerEntry {
	private clsBasePart moSource;
	private clsBasePart moTarget;
	private double mrIntensity;
	
	/**
	 * @param poSource
	 * @param poTarget
	 * @param prIntensity
	 */
	public clsFastMessengerEntry(clsBasePart poSource, clsBasePart poTarget, double prIntensity) {
		moSource = poSource;
		moTarget = poTarget;
		mrIntensity = prIntensity;
	}

	/**
	 * TODO (deutsch) - insert description
	 *
	 * @return
	 */
	public clsBasePart getTarget() {
		return moTarget;
	}
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @return
	 */
	public clsBasePart getSource() {
		return moSource;
	}	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @return
	 */
	public double getIntensity() {
		return mrIntensity;
	}
	
}
