/**
 * clsHomeostaticRepresentation.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:42:59
 */
package pa.memorymgmt.datatypes;

import java.util.ArrayList;

import pa.memorymgmt.enums.eDataType;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:42:59
 * 
 */
public abstract class clsHomeostaticRepresentation extends clsPrimaryDataStructure{
	protected eDataType moHomeostaticType; 	
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 24.05.2010, 12:57:01
	 *
	 */
	public clsHomeostaticRepresentation(ArrayList<clsAssociation> poAssociatedDriveSource,
										String poAssociationID, 
										eDataType peAssociationType) {
		
		super(poAssociationID, peAssociationType); 
		applyAssociations(eDataType.ASSCOCIATIONATTRIBUTE, poAssociatedDriveSource);  
	}
	
}
