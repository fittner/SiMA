/**
 * clsPhysicalRepresentation.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:42:44
 */
package pa.memorymgmt.datatypes;

import pa.memorymgmt.enums.eDataType;
import pa.tools.clsTripple;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:42:44
 * 
 */
public abstract class clsPhysicalRepresentation extends clsPrimaryDataStructure{
		
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 24.05.2010, 12:39:08
	 * @param poWordPresentationAssociation 
	 *
	 */
	public clsPhysicalRepresentation(clsTripple<String, eDataType, String> poDataStructureIdentifier) {
		super(poDataStructureIdentifier); 
	}
}
