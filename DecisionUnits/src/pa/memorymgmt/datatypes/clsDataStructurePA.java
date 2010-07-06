/**
 * clsDatastructure.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:40:06
 */
package pa.memorymgmt.datatypes;

import pa.memorymgmt.enums.eDataType;


/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:40:06
 * 
 */
public abstract class clsDataStructurePA implements Cloneable{
	public String oDataStructureID;
	public eDataType oDataStructureType;
	
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 22.06.2010, 15:50:02
	 *
	 */
	public clsDataStructurePA(String poDataStructureName, eDataType poDataStructureType) {
		oDataStructureID = poDataStructureName; 
		oDataStructureType = poDataStructureType;
	}
}