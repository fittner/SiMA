/**
 * clsSecondaryDataStructure.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 24.05.2010, 10:42:44
 */
package pa.memorymgmt.datatypes;

import pa.memorymgmt.enums.eDataType;
import pa.tools.clsTripple;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 24.05.2010, 10:42:44
 * 
 */
public abstract class clsSecondaryDataStructure extends clsDataStructurePA{
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 22.06.2010, 19:59:46
	 *
	 * @param poDataStructureName
	 * @param poDataStructureType
	 */
	public clsSecondaryDataStructure(clsTripple<Integer, eDataType, String> poDataStructureIdentifier) {
		super(poDataStructureIdentifier);
	}
	
	public abstract void assignDataStructure(clsAssociation poDataStructurePA);
}
