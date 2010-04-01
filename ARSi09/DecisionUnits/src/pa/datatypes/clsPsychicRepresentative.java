/**
 * clsPsychicRepresentative.java: DecisionUnits - pa.datatypes
 * 
 * @author langr
 * 11.08.2009, 12:54:29
 */
package pa.datatypes;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 11.08.2009, 12:54:29
 * 
 */
public class clsPsychicRepresentative implements Cloneable {

	@Override
	public Object clone() throws CloneNotSupportedException {
        try {
        	clsPsychicRepresentative oClone = (clsPsychicRepresentative)super.clone();
        	return oClone;
        } catch (CloneNotSupportedException e) {
           return e;
        }
	}	
	
}
