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
	private Object moContent = null; 
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
			clsPrimaryDataStructure poAssociationElementB,
			String poDataStructureName,
			eDataType peDataStructureType){
		
		super(poAssociationElementA, poAssociationElementB, poDataStructureName, peDataStructureType);		
		//HZ moContent defines the association weight => also defines the affect!
		//It has to be defined if an affect is required and how the affect's minus values
		//should be represented in case the affect is the same as the clsAssociation's weight 
		moContent = 1.0; 
	}

}
