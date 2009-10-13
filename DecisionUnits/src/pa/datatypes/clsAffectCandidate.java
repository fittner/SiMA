/**
 * clsAffectCandidate.java: DecisionUnits - pa.datatypes
 * 
 * @author langr
 * 08.10.2009, 10:01:36
 */
package pa.datatypes;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 08.10.2009, 10:01:36
 * 
 */
public class clsAffectCandidate {

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
	
}
