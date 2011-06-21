/**
 * clsAffectMemory.java: DecisionUnits - pa.datatypes
 * 
 * @author langr
 * 17.10.2009, 19:19:18
 */
package pa._v19.datatypes;

/**
 * 
 * @author langr
 * 17.10.2009, 19:19:18
 * 
 */
@Deprecated
public class clsAffectMemory extends clsAffect implements Cloneable {

	public clsMinus1to1 moValue;
	
	public clsAffectMemory() {
		moValue = new clsMinus1to1();
	}
	
	public clsAffectMemory(double poValue) {
		moValue = new clsMinus1to1(poValue);
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException{
        try {
        	clsAffectMemory oClone = (clsAffectMemory)super.clone();
        	oClone.moValue = (clsMinus1to1)moValue.clone();
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
