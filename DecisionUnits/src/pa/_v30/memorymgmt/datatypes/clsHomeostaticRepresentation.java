/**
 * clsHomeostaticRepresentation.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:42:59
 */
package pa._v30.memorymgmt.datatypes;

import pa._v30.tools.clsTripple;
import pa._v30.memorymgmt.enums.eDataType;

/**
 *
 * 
 * @author zeilinger
 * 23.05.2010, 21:42:59
 * 
 */
public abstract class clsHomeostaticRepresentation extends clsPrimaryDataStructure{
	/**
	 *
	 * 
	 * @author zeilinger
	 * 24.05.2010, 12:57:01
	 *
	 */
	public clsHomeostaticRepresentation(clsTripple<Integer, eDataType, String> poDataStructureIdentifier) {
		super(poDataStructureIdentifier); 
	}
	
	/**
	 *
	 *
	 * @author zeilinger
	 * 01.07.2010, 22:49:49
	 *
	 * @param poDataStructurePA
	 */
	public abstract void assignDataStructure(clsAssociation poDataStructurePA); 
	
}
