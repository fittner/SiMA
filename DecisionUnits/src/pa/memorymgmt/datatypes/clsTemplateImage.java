/**
 * clsTemplateImage.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:49:45
 */
package pa.memorymgmt.datatypes;

import java.util.ArrayList;

import pa.memorymgmt.enums.eDataType;


/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:49:45
 * 
 */
public class clsTemplateImage extends clsPhysicalStructureComposition{
		
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 24.05.2010, 12:41:23
	 *
	 */
	public clsTemplateImage(ArrayList<clsAssociation> poAssociatedTemporalStructures,
							String poDataStructureName,
							eDataType poDataStructureType) {
		super(poDataStructureName, poDataStructureType); 
		 
		applyAssociations(eDataType.ASSOCIATIONTEMP, poAssociatedTemporalStructures);
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 22.06.2010, 16:19:32
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
	 * 13.07.2010, 20:58:48
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public double compareTo(clsDataStructurePA poDataStructure) {
		clsTemplateImage oDataStructure = (clsTemplateImage)poDataStructure;
		
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
			//TI content is represented by a list of temporal associations	
			ArrayList <clsAssociation> oContentListTemplate = this.moContent.get(eDataType.ASSOCIATIONTEMP); 
			ArrayList <clsAssociation> oContentListUnknown = oDataStructure.moContent.get(eDataType.ASSOCIATIONTEMP);
			
			return getCompareScore(oContentListTemplate, oContentListUnknown); 
		}
	}
}
