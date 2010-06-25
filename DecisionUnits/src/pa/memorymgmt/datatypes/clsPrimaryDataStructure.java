/**
 * clsPrimaryDataStructure.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 31.05.2010, 16:32:42
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
 * 31.05.2010, 16:32:42
 * 
 */
public abstract class clsPrimaryDataStructure extends clsDataStructurePA{
		
	public Map<eDataType, ArrayList<clsAssociation>> moAssociations;
	
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 22.06.2010, 15:36:00
	 *
	 */
	public clsPrimaryDataStructure(String poDataStructureName, eDataType poDataStructureType) {
		super(poDataStructureName, poDataStructureType);  
		
		moAssociations = new Hashtable <eDataType, ArrayList<clsAssociation>>();
	}
	
	public abstract void assignDataStructure(clsAssociation poDataStructurePA);
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 24.05.2010, 14:40:45
	 *
	 * @param poAssociatedWordPresentations
	 */
		
	protected void applyAssociations(eDataType poDataType, ArrayList<clsAssociation> poAssociatedDataStructures) {
		ArrayList <clsAssociation> oStructureList = moAssociations.get(poDataType); 
		if(oStructureList == null) {moAssociations.put(poDataType, poAssociatedDataStructures);} 
		else {oStructureList.addAll(poAssociatedDataStructures);} 
	}
}
