/**
 * clsAffect.java: DecisionUnits - pa.datatypes
 * 
 * @author langr
 * 17.10.2009, 19:33:26
 */
package pa._v19.datatypes;


/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 17.10.2009, 19:33:26
 * 
 */
@Deprecated
public abstract class clsAffect extends clsPsychicRepresentative implements Cloneable { 
	public abstract double getValue();
	
	@Override
	public Object clone() throws CloneNotSupportedException {
        try {
        	clsAffect oClone = (clsAffect)super.clone();

        	return oClone;
        } catch (CloneNotSupportedException e) {
           return e;
        }
	}	
}
