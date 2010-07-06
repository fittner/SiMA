/**
 * clsDriveMesh.java: DecisionUnits - pa.memorymgmt.datatypes
 * 
 * @author zeilinger
 * 23.06.2010, 20:36:25
 */
package pa.memorymgmt.datatypes;

import java.util.ArrayList;

import pa.memorymgmt.enums.eDataType;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.06.2010, 20:36:25
 * 
 */
public class clsDriveMesh extends clsHomeostaticRepresentation{
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 23.06.2010, 20:37:07
	 *
	 * @param poHomeostaticSource
	 * @param poAssociationID
	 * @param peAssociationType
	 */
	public clsDriveMesh(ArrayList<clsAssociation> poAssociatedDriveSource,
			String poAssociationID, eDataType peAssociationType) {
		super(poAssociationID, peAssociationType); 
		
		applyAssociations(eDataType.ASSCOCIATIONATTRIBUTE, poAssociatedDriveSource);
	}
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 23.06.2010, 22:00:22
	 * 
	 * @see pa.memorymgmt.datatypes.clsPrimaryDataStructure#assignDataStructure(pa.memorymgmt.datatypes.clsAssociation)
	 */
	@Override
	public void assignDataStructure(clsAssociation poDataStructurePA) {
		ArrayList <clsAssociation> oDataStructureList = new ArrayList<clsAssociation>();
		oDataStructureList.add(poDataStructurePA); 
		
		applyAssociations(poDataStructurePA.oDataStructureType, oDataStructureList);
	}

}