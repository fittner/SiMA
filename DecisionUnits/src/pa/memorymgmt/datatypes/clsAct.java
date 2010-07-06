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
}

