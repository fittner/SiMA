/**
 * clsAssociationAttribute.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:53:16
 */
package pa.memorymgmt.datatypes;

import pa.memorymgmt.enums.eDataType;
import pa.tools.clsTripple;

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
	public clsAssociationAttribute(clsTripple<String, eDataType, String> poDataStructureIdentifier,
									clsPrimaryDataStructure poAssociationElementA, 
								    clsPrimaryDataStructure poAssociationElementB) {
		super(poDataStructureIdentifier,poAssociationElementA, poAssociationElementB);
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

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 17.08.2010, 21:10:35
	 * 
	 * @see pa.memorymgmt.datatypes.clsAssociation#getLeafElement(pa.memorymgmt.datatypes.clsDataStructurePA)
	 */
	@Override
	public clsDataStructurePA getLeafElement(clsDataStructurePA poRootElement) {
		//TPMs have one element that form the root for associated Attribute Associations
		//This element is always moAssociationElementA; Hence the B element is returned
		return moAssociationElementB;
	}
}
