/**
 * clsPhysicalRepresentation.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:42:44
 */
package pa._v19.memorymgmt.datatypes;

import pa._v19.memorymgmt.enums.eDataType;
import pa._v19.tools.clsTripple;

/**
 * 
 * @author zeilinger
 * 23.05.2010, 21:42:44
 * 
 */
public abstract class clsPhysicalRepresentation extends clsPrimaryDataStructure{
		
	/**
	 * 
	 * @author zeilinger
	 * 24.05.2010, 12:39:08
	 * @param poWordPresentationAssociation 
	 *
	 */
	public clsPhysicalRepresentation(clsTripple<Integer, eDataType, String> poDataStructureIdentifier) {
		super(poDataStructureIdentifier); 
	}
}
