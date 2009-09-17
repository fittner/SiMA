/**
 * clsFastMessengerKeyTuple.java: BW - bw.body.internalSystems
 * 
 * @author deutsch
 * 17.09.2009, 11:44:55
 */
package bw.body.internalSystems;

import bw.utils.enums.eBodyParts;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 17.09.2009, 11:44:55
 * 
 */
public class clsFastMessengerKeyTuple {
	public eBodyParts meSource;
	public eBodyParts meTarget;
	
	public clsFastMessengerKeyTuple(eBodyParts peSource, eBodyParts peTarget) {
		meSource = peSource;
		meTarget = peTarget;
	}
	
	@Override
	public String toString() {
		return meSource.name()+" -> "+meTarget.name();
	}
}
