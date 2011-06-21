/**
 * clsAssociationTime.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:52:50
 */
package pa._v30.memorymgmt.datatypes;

import pa._v30.tools.clsTripple;
import pa._v30.memorymgmt.enums.eDataType;

/**
 *
 * 
 * @author zeilinger
 * 23.05.2010, 21:52:50
 * 
 */
public class clsAssociationTime extends clsAssociation{

	/**
	 *
	 * 
	 * @author zeilinger
	 * 24.05.2010, 16:05:04
	 *
	 * @param poAssociationElementA
	 * @param poAssociationElementB
	 * @param poAssociationID
	 * @param poAssociationType
	 */
	public clsAssociationTime(clsTripple<Integer, eDataType, String> poDataStructureIdentifier,
			clsPrimaryDataStructure poAssociationElementA,
			clsPrimaryDataStructure poAssociationElementB) {
		
		super(poDataStructureIdentifier, poAssociationElementA, poAssociationElementB);
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 13.07.2010, 20:58:17
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
	 * 17.08.2010, 21:07:47
	 * 
	 * @see pa._v30.memorymgmt.datatypes.clsAssociation#getLeafElement(pa._v30.memorymgmt.datatypes.clsDataStructurePA)
	 */
	@Override
	public clsDataStructurePA getLeafElement() {
		//TIs have one element that form the root for associated Time Associations
		//This element is always moAssociationElementA; Hence the B element is returned
		return moAssociationElementB;
	}
}
