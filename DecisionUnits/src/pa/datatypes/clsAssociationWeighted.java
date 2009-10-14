/**
 * clsAssociationWeighted.java: DecisionUnits - pa.memory
 * 
 * @author langr
 * 11.08.2009, 15:03:53
 */
package pa.datatypes;

/**
 * 
 *                       moWeight
 *    moElementA  <---------------------> moElementB
 * 
 * @author langr
 * 11.08.2009, 15:03:53
 * 
 */
public class clsAssociationWeighted<TYPE> extends clsAssociation<TYPE> implements Cloneable {

	public double moWeight;
	
	@SuppressWarnings("unchecked")
	@Override
	public Object clone() throws CloneNotSupportedException {
        try {
        	clsAssociationWeighted<TYPE> oClone = (clsAssociationWeighted<TYPE>)super.clone();

        	return oClone;
        } catch (CloneNotSupportedException e) {
           return e;
        }
	}		
	
}
