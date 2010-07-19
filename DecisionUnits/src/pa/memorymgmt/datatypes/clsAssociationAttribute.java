/**
 * clsAssociationAttribute.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:53:16
 */
package pa.memorymgmt.datatypes;

import pa.memorymgmt.enums.eDataType;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:53:16
 * 
 */
public class clsAssociationAttribute extends clsAssociation{
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 24.05.2010, 15:53:46
	 *
	 */
	public clsAssociationAttribute(String poAssociationID,
			   					    eDataType peAssociationType,
									clsPrimaryDataStructure poAssociationElementA, 
								    clsPrimaryDataStructure poAssociationElementB) {
		super(poAssociationID, peAssociationType,poAssociationElementA, poAssociationElementB);
	}
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 13.07.2010, 20:58:01
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public double compareTo(clsDataStructurePA poDataStructure) {
		// TODO (zeilinger) - Auto-generated method stub
		return 0;
	}
}
