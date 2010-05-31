/**
 * clsAssociationDriveMesh.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:54:11
 */
package pa.informationrepresentation.datatypes;

import pa.informationrepresentation.enums.eAssociationType;


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
	public clsAssociationDriveMesh(clsDataStructurePA poAssociationElementA,
			clsDriveSource poAssociationElementB,
			String poAssociationID) {
		
		super(poAssociationElementA, poAssociationElementB, poAssociationID, eAssociationType.DRIVEMESH.toString());		
		// TODO (zeilinger) - Auto-generated constructor stub
	}

}
