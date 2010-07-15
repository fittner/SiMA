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

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 13.07.2010, 20:58:39
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public double compareTo(clsDataStructurePA poDataStructure) {
		clsDriveMesh oDataStructure = (clsDriveMesh)poDataStructure;
		
		//This if statement proofs if the compared datastructure does already have an ID =>
		//the ID sepcifies that the data structure has been already compared with a stored
		//data structure and replaced by it. Hence they can be compared by their IDs. 
		if(oDataStructure.oDataStructureID != null){
			if(compareDataStructureID(oDataStructure))return 9999; 
			else return 0; 
		}
		else{
			//In case the data structure does not have an ID, it has to be compared to a stored 
			//data structure and replaced by it (the processes base on information that is already
			//defined
			//TPM content is represented by a list of attribute associations	
			ArrayList <clsAssociation> oContentListTemplate = this.moContent.get(eDataType.ASSCOCIATIONATTRIBUTE); 
			ArrayList <clsAssociation> oContentListUnknown = oDataStructure.moContent.get(eDataType.ASSCOCIATIONATTRIBUTE);
			
			return getCompareScore(oContentListTemplate, oContentListUnknown);
	    }
	}

}
