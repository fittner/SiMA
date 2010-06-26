/**
 * clsPhysicalStructureComposition.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:49:15
 */
package pa.memorymgmt.datatypes;

import java.util.ArrayList;

import pa.memorymgmt.enums.eDataType;


/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:49:15
 * 
 */
public abstract class clsPhysicalStructureComposition extends clsPhysicalRepresentation {
		
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 24.05.2010, 12:40:36
	 * @param object2 
	 * @param object 
	 *
	 */
	public clsPhysicalStructureComposition(ArrayList<clsAssociation> poAssociatedDriveSources,
										   String poDataStructureName,
										   eDataType poDataStructureType) {
		super(poDataStructureName, poDataStructureType);
		applyAssociations(eDataType.ASSOCIATIONDM, poAssociatedDriveSources);  
	}
}
