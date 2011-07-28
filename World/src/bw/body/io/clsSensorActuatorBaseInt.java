/**
 * @author zeilinger
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io;

import config.clsProperties;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * Changes:
 *   BD (21.6.) Moved moBaseIO and registerEnergyconsumption from clsSensorActuatorBase to here
 * 
 */
public abstract class clsSensorActuatorBaseInt extends clsSensorActuatorBase{
	public clsSensorActuatorBaseInt(String poPrefix, clsProperties poProp, clsBaseIO poBaseIO) {
		super(poPrefix, poProp, poBaseIO);
		applyProperties(poPrefix, poProp);
	}
	
	public clsSensorActuatorBaseInt(String poPrefix, clsProperties poProp) {
		super(poPrefix, poProp, null);
		applyProperties(poPrefix, poProp);
	}
	

	public static clsProperties getDefaultProperties(String poPrefix) {
//		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = clsSensorActuatorBase.getDefaultProperties(poPrefix);

		return oProp;
	}	

	private void applyProperties(String poPrefix, clsProperties poProp) {
		//String pre = clsProperties.addDot(poPrefix);
	}

}
