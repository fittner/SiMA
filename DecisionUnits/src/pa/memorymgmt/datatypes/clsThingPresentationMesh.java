/**
 * clsThingPresentationMesh.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 24.05.2010, 12:51:07
 */
package pa.memorymgmt.datatypes;

import java.util.ArrayList;
import java.util.List;

import pa.memorymgmt.enums.eDataType;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 24.05.2010, 12:51:07
 * 
 */
public class clsThingPresentationMesh extends clsPhysicalStructureComposition{
	protected List <clsAssociationAttribute> moAttributeAssociations; 
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 24.05.2010, 12:51:22
	 *
	 * @param poWordPresentationAssociation
	 * @param poDriveMeshAssociation
	 */
	public clsThingPresentationMesh( ArrayList<clsAssociation> poAssociatedWordPresentations,
									 ArrayList<clsAssociation> poAssociatedDriveSources,
									 ArrayList<clsAssociation> poAssociatedPhysicalRepresentations,
									 String poDataStructureName,
									 eDataType poDataStructureType) {
		
		super(poAssociatedWordPresentations, poAssociatedDriveSources, poDataStructureName, poDataStructureType);
		
		//applyAssociations(poAssociatedPhysicalRepresentations); 
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

}
