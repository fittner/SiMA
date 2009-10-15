/**
 * clsAssoziationSecondary.java: DecisionUnits - pa.datatypes
 * 
 * @author deutsch
 * 09.09.2009, 16:59:46
 */
package pa.datatypes;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 09.09.2009, 16:59:46
 * 
 */

public class clsAssoziationSecondary implements Cloneable {

	public clsWordPresentation A;
	public clsWordPresentation B;
	public double strength;
	
	@Override
	public Object clone() throws CloneNotSupportedException{
        try {
        	clsAssoziationSecondary oClone = (clsAssoziationSecondary)super.clone();
        	oClone.A = (clsWordPresentation)A.clone();
        	oClone.B = (clsWordPresentation)B.clone();        	
        	return oClone;
        } catch (CloneNotSupportedException e) {
            return e;
        }
		
	}	
}
