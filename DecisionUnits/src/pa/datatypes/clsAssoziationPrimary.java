/**
 * clsAssoziationPrimary.java: DecisionUnits - pa.datatypes
 * 
 * @author deutsch
 * 09.09.2009, 16:54:33
 */
package pa.datatypes;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 09.09.2009, 16:54:33
 * 
 */
public class clsAssoziationPrimary implements Cloneable {
	public clsPrimaryInformation A;
	public clsPrimaryInformation B;
	public double strength;
	
	@Override
	public Object clone() throws CloneNotSupportedException {
        try {
        	clsAssoziationPrimary oClone = (clsAssoziationPrimary)super.clone();
        	oClone.A = (clsPrimaryInformation)A.clone();
        	oClone.B = (clsPrimaryInformation)B.clone();        	
        	return oClone;
        } catch (CloneNotSupportedException e) {
            return e;
        }
		
	}	
}
