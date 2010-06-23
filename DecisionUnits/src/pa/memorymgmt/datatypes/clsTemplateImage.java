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
	public clsTemplateImage(ArrayList<clsAssociation> poAssociatedWordPresentations,
							ArrayList<clsAssociation> poAssociatedDriveSources,
							ArrayList<clsAssociation> poAssociatedTemporalStructures,
							String poDataStructureName,
							eDataType poDataStructureType) {
		super(poAssociatedWordPresentations, poAssociatedDriveSources, poDataStructureName, poDataStructureType); 
		 
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
	public void assignDataStructure(clsDataStructurePA poDataStructurePA) {
		ArrayList <clsAssociation> oDataStructureList = new ArrayList<clsAssociation>();
		oDataStructureList.add((clsAssociation)poDataStructurePA); 
		
		applyAssociations(poDataStructurePA.oDataStructureType, oDataStructureList);
	}
}
