/**
 * clsAffectCandidate.java: DecisionUnits - pa.datatypes
 * 
 * @author langr
 * 08.10.2009, 10:01:36
 */
package pa._v19.datatypes;

/**
 * 
 * @author langr
 * 08.10.2009, 10:01:36
 * 
 */
@Deprecated
public class clsAffectCandidate implements Cloneable{

	public double mrTensionValue = 0;

	/**
	 * @author langr
	 * 13.10.2009, 16:32:29
	 * 
	 * @param mrTensionValue the mrTensionValue to set
	 */
	public void setTensionValue(double mrTensionValue) {
		this.mrTensionValue = mrTensionValue;
	}

	/**
	 * @author langr
	 * 13.10.2009, 16:32:29
	 * 
	 * @return the mrTensionValue
	 */
	public double getTensionValue() {
		return mrTensionValue;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
        try {
        	clsAffectCandidate oClone = (clsAffectCandidate)super.clone();

        	return oClone;
        } catch (CloneNotSupportedException e) {
           return e;
        }
	}	
	
}
