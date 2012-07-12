/**
 * clsPhysicalRepresentation.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:42:44
 */
package pa._v38.memorymgmt.datatypes;

import pa._v38.tools.clsTriple;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;

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
	public clsPhysicalRepresentation(clsTriple<Integer, eDataType, eContentType> poDataStructureIdentifier) {
		super(poDataStructureIdentifier); 
	}
}
