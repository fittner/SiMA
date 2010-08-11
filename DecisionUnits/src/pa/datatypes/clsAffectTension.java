/**
 * clsAffect.java: DecisionUnits - pa.datatypes
 * 
 * @author langr
 * 11.08.2009, 11:16:41
 */
package pa.datatypes;

import pa.bfg.tools.cls0to1;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 11.08.2009, 11:16:41
 * 
 */
@Deprecated
public class clsAffectTension extends clsAffect implements Cloneable {

	public cls0to1 moValue;
	
	public clsAffectTension() {
		moValue = new cls0to1();
	}
	
	public clsAffectTension(double poValue) {
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
	public clsAffectTension(clsAffectCandidate poAffectCandidate) {
		moValue = new cls0to1(poAffectCandidate.getTensionValue());
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException{
        try {
        	clsAffectTension oClone = (clsAffectTension)super.clone();
        	oClone.moValue = (cls0to1)moValue.clone();
        	return oClone;
        } catch (CloneNotSupportedException e) {
            return e;
        }
		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 17.10.2009, 20:04:16
	 * 
	 * @see pa.datatypes.clsAffect#getValue()
	 */
	@Override
	public double getValue() {
		return moValue.get();
	}
	
}
