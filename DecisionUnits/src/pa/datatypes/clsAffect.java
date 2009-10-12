/**
 * clsAffect.java: DecisionUnits - pa.datatypes
 * 
 * @author langr
 * 11.08.2009, 11:16:41
 */
package pa.datatypes;

import bfg.tools.cls0to1;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 11.08.2009, 11:16:41
 * 
 */
public class clsAffect extends clsPsychicRepresentative {

	public cls0to1 moValue;
	
	public clsAffect() {
		moValue = new cls0to1();
	}
	
	public clsAffect(double poValue) {
		moValue = new cls0to1(poValue);
	}
	
}
