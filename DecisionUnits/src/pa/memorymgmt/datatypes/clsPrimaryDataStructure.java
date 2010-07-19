/**
 * clsPrimaryDataStructure.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 31.05.2010, 16:32:42
 */
package pa.memorymgmt.datatypes;

import pa.memorymgmt.enums.eDataType;

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
	public clsPrimaryDataStructure(String poDataStructureID, eDataType poDataStructureType) {
		super(poDataStructureID, poDataStructureType);  
	}
}
