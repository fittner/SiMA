/**
 * clsAssociationWordPresentation.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:50:53
 */
package pa._v30.memorymgmt.datatypes;

import pa._v30.tools.clsTripple;
import pa._v30.memorymgmt.enums.eDataType;

/**
 *
 * 
 * @author zeilinger
 * 23.05.2010, 21:50:53
 * 
 */
public class clsAssociationWordPresentation extends clsAssociation{

	/**
	 *
	 * 
	 * @author zeilinger
	 * 24.05.2010, 13:32:59
	 *
	 * @param clsDataStructurePA
	 * @param poWordPresentation
	 */
	public clsAssociationWordPresentation(
			clsTripple<Integer, eDataType, String> poDataStructureIdentifier,
			clsWordPresentation poAssociationElementA,
			clsDataStructurePA poAssociationElementB) {
			
			super(poDataStructureIdentifier, poAssociationElementA, poAssociationElementB); 
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 13.07.2010, 20:58:24
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public double compareTo(clsDataStructurePA o) {
		
		return 0;
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 17.08.2010, 21:07:20
	 * 
	 * @see pa._v30.memorymgmt.datatypes.clsAssociation#getLeafElement(pa._v30.memorymgmt.datatypes.clsDataStructurePA)
	 */
	@Override
	public clsDataStructurePA getLeafElement() {
		return moAssociationElementA; 
	}
}
