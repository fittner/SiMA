/**
 * clsConnection.java: DecisionUnitMasonInspectors - inspectors.mind.pa.functionalmodel
 * 
 * @author deutsch
 * 22.10.2009, 12:13:05
 */
package inspectors.mind.pa._v38.functionalmodel;

import pa._v38.interfaces.eInterfaces;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 22.10.2009, 12:13:05
 * 
 */
public class clsConnection {
	public final eInterfaces mnInterface;
	public final clsNode moTarget;
		
	public clsConnection(eInterfaces pnInterface, clsNode poTarget) {
		mnInterface = pnInterface;
		moTarget = poTarget;
	}
	
	@Override
	public String toString() {
		return mnInterface.toString();
	}
	
	public clsNode getTarget() {
		return moTarget;
	}
}
