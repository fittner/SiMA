/**
 * clsAct.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:43:47
 */
package pa.memorymgmt.datatypes;

import java.util.ArrayList;

import pa.memorymgmt.enums.eDataType;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:43:47
 * 
 */
public class clsAct extends clsSecondaryDataStructure {

	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 22.06.2010, 20:01:13
	 *
	 * @param poDataStructureName
	 * @param poDataStructureType
	 */
	public clsAct(ArrayList<clsAssociation> poAssociatedWordPresentations,String poAssociationID, 
											eDataType peAssociationType) {
		super(poAssociatedWordPresentations, poAssociationID, peAssociationType);
		// TODO (zeilinger) - Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 23.06.2010, 22:03:23
	 * 
	 * @see pa.memorymgmt.datatypes.clsSecondaryDataStructure#assignDataStructure(pa.memorymgmt.datatypes.clsAssociation)
	 */
	@Override
	public void assignDataStructure(clsAssociation dataStructureAssociation) {
		// TODO (zeilinger) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 13.07.2010, 20:57:48
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public double compareTo(clsDataStructurePA poDataStructure) {
		clsAct oDataStructure = (clsAct)poDataStructure;
		
		//This if statement proofs if the compared datastructure does already have an ID =>
		//the ID sepcifies that the data structure has been already compared with a stored
		//data structure and replaced by it. Hence they can be compared by their IDs. 
		if(oDataStructure.oDataStructureID != null){
			if(compareDataStructureID(oDataStructure))return 9999; 
			else return 0; 
		}
		return 0;
	}
}

