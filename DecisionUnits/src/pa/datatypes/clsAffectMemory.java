/**
 * clsAffectMemory.java: DecisionUnits - pa.datatypes
 * 
 * @author langr
 * 17.10.2009, 19:19:18
 */
package pa.datatypes;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 17.10.2009, 19:19:18
 * 
 */
public class clsAffectMemory extends clsAffect implements Cloneable {

	public clsMinu1to1 moValue;
	
	public clsAffectMemory() {
		moValue = new clsMinu1to1();
	}
	
	public clsAffectMemory(double poValue) {
		moValue = new clsMinu1to1(poValue);
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException{
        try {
        	clsAffectMemory oClone = (clsAffectMemory)super.clone();
        	oClone.moValue = (clsMinu1to1)moValue.clone();
        	return oClone;
        } catch (CloneNotSupportedException e) {
            return e;
        }
		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 17.10.2009, 20:04:44
	 * 
	 * @see pa.datatypes.clsAffect#getValue()
	 */
	@Override
	public double getValue() {
		return moValue.get();
	}
}
