/**
 * clsThingPresentationMesh.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 24.05.2010, 12:51:07
 */
package pa.memorymgmt.datatypes;

import java.util.ArrayList;

import pa.memorymgmt.enums.eDataType;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 24.05.2010, 12:51:07
 * 
 */
public class clsThingPresentationMesh extends clsPhysicalStructureComposition{
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilingere
	 * 24.05.2010, 12:51:22
	 *
	 * @param poWordPresentationAssociation
	 * @param poDriveMeshAssociation
	 */
	public clsThingPresentationMesh( ArrayList<clsAssociation> poAssociatedPhysicalRepresentations,
									 String poDataStructureName,
									 eDataType poDataStructureType) {
		
		super(poDataStructureName,poDataStructureType);
		applyAssociations(eDataType.ASSCOCIATIONATTRIBUTE, poAssociatedPhysicalRepresentations); 
	}
	
									 /**
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 22.06.2010, 17:46:07
	 * 
	 * @see pa.memorymgmt.datatypes.clsDataStructurePA#assignDataStructure(pa.memorymgmt.datatypes.clsDataStructurePA)
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
	* 13.07.2010, 20:59:01
	* 
	* @see java.lang.Comparable#compareTo(java.lang.Object)
	*/
	@Override
	public double compareTo(clsDataStructurePA poDataStructure) {
		clsThingPresentationMesh oDataStructure = (clsThingPresentationMesh)poDataStructure;
		double oMatchScore = 0;
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
