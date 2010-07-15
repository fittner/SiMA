/**
 * clsAssociationTime.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:52:50
 */
package pa.memorymgmt.datatypes;

import pa.memorymgmt.enums.eDataType;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:52:50
 * 
 */
public class clsAssociationTime extends clsAssociation{

	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 24.05.2010, 16:05:04
	 *
	 * @param poAssociationElementA
	 * @param poAssociationElementB
	 * @param poAssociationID
	 * @param poAssociationType
	 */
	public clsAssociationTime(clsPrimaryDataStructure poAssociationElementA,
			clsPrimaryDataStructure poAssociationElementB, String poAssociationID, eDataType peAssociationType) {
		
		super(poAssociationElementA, poAssociationElementB, 
				poAssociationID, peAssociationType);
		// TODO (zeilinger) - Auto-generated constructor stub
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
		// TODO (zeilinger) - Auto-generated method stub
		return 0;
	}
	
	
}
