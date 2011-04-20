/**
 * clsPrimaryDataStructure.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 31.05.2010, 16:32:42
 */
package pa._v30.memorymgmt.datatypes;

import pa._v30.tools.clsTripple;
import pa._v30.memorymgmt.enums.eDataType;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 31.05.2010, 16:32:42
 * 
 */
public abstract class clsPrimaryDataStructure extends clsDataStructurePA{
		
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 22.06.2010, 15:36:00
	 *
	 */
	public clsPrimaryDataStructure(clsTripple<Integer, eDataType, String> poDataStructureIdentifier) {
		super(poDataStructureIdentifier);  
	}
}
