/**
 * clsAssociationDriveMesh.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:54:11
 */
package pa.memorymgmt.datatypes;

import pa.memorymgmt.enums.eDataType;


/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:54:11
 * 
 */
public class clsAssociationDriveMesh extends clsAssociation{

	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 24.05.2010, 13:36:37
	 *
	 * @param clsDataStructurePA
	 * @param poDriveMesh
	 */
	public clsAssociationDriveMesh(clsDriveMesh poAssociationElementA, 
			clsDataStructurePA poAssociationElementB,
			String poAssociationID,
			eDataType peAssociationType){
		
		super(poAssociationElementA, poAssociationElementB, poAssociationID, peAssociationType);		
		// TODO (zeilinger) - Auto-generated constructor stub
	}

}
