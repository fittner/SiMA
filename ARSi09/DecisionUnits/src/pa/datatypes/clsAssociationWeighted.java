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
	
	public clsAssociationWeighted() {
		super();
	}

	public clsAssociationWeighted(TYPE a, TYPE b) {
		super(a, b);
	}
	
	@Override
	public clsAssociation<clsSecondaryInformation> convertToSecondary(
			clsSecondaryInformationMesh poA) {
		
		clsAssociationWeighted<clsSecondaryInformation> oRetVal = null;
		
		if(moElementB instanceof clsPrimaryInformationMesh) {
			oRetVal = new clsAssociationWeighted<clsSecondaryInformation>(poA, new clsSecondaryInformationMesh( (clsPrimaryInformationMesh)moElementB ) );
		}
		else if(moElementB instanceof clsPrimaryInformation) {
			oRetVal = new clsAssociationWeighted<clsSecondaryInformation>(poA, new clsSecondaryInformationMesh( (clsPrimaryInformation)moElementB ) );
		}
		if(oRetVal != null) {
			oRetVal.moWeight = moWeight;
		}
		
		return oRetVal;
	}	

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
	
	/**
	 * special clone method. prevents infinite loops while cloning object A which has association X with object B.
	 * A.clone() calls X.clone() which - consecutively - creates a new clone from A ... Thus, references to A and its clone A' 
	 * are passed to the clone method from X. If X.moA refers to A, it is redirected to A'; if X.moB refers to B, it is 
	 * redirected to A'.
	 *
	 * @author deutsch
	 * 19.10.2009, 16:17:12
	 *
	 * @param obj_orig
	 * @param obj_clon
	 * @return clone
	 * @throws CloneNotSupportedException
	 */	
	@Override
	@SuppressWarnings("unchecked")
	public Object clone(Object obj_orig, Object obj_clon) throws CloneNotSupportedException {	
        try {
        	clsAssociationWeighted<TYPE> oClone = (clsAssociationWeighted<TYPE>)super.clone(obj_orig, obj_clon);

        	return oClone;
        } catch (CloneNotSupportedException e) {
           return e;
        }
	}
}
