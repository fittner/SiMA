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
public class clsAffect extends clsPsychicRepresentative implements Cloneable {

	public cls0to1 moValue;
	
	public clsAffect() {
		moValue = new cls0to1();
	}
	
	public clsAffect(double poValue) {
		moValue = new cls0to1(poValue);
	}

	/**
	 * DOCUMENT (langr) - insert description 
	 * 
	 * @author langr
	 * 13.10.2009, 17:30:35
	 *
	 * @param affectCandidate
	 */
	public clsAffect(clsAffectCandidate poAffectCandidate) {
		moValue = new cls0to1(poAffectCandidate.getTensionValue());
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException{
        try {
        	clsAffect oClone = (clsAffect)super.clone();
        	oClone.moValue = (cls0to1)moValue.clone();
        	return oClone;
        } catch (CloneNotSupportedException e) {
            return e;
        }
		
	}
	
}
