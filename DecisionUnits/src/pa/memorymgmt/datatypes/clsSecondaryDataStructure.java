/**
 * clsSecondaryDataStructure.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 24.05.2010, 10:42:44
 */
package pa.memorymgmt.datatypes;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import pa.memorymgmt.enums.eDataType;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 24.05.2010, 10:42:44
 * 
 */
public abstract class clsSecondaryDataStructure extends clsDataStructurePA{
	public Map<eDataType, ArrayList<clsAssociation>> moAssociations;
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 22.06.2010, 19:59:46
	 *
	 * @param poDataStructureName
	 * @param poDataStructureType
	 */
	public clsSecondaryDataStructure(ArrayList<clsAssociation> poAssociatedWordPresentations, 
										String poDataStructureName, 
										eDataType poDataStructureType) {
		super(poDataStructureName, poDataStructureType);
		moAssociations = new Hashtable<eDataType, ArrayList<clsAssociation>>(); 
	}
	
	public abstract void assignDataStructure(clsAssociation poDataStructurePA);

}
