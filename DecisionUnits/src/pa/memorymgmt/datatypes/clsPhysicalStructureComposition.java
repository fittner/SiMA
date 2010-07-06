/**
 * clsPhysicalStructureComposition.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:49:15
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
 * 23.05.2010, 21:49:15
 * 
 */
public abstract class clsPhysicalStructureComposition extends clsPhysicalRepresentation {
	public Map<eDataType, ArrayList<clsAssociation>> moContent; 	
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 24.05.2010, 12:40:36
	 * @param object2 
	 * @param object 
	 *
	 */
	public clsPhysicalStructureComposition(String poDataStructureName,
										   eDataType poDataStructureType) {
		super(poDataStructureName, poDataStructureType);
		moContent = new Hashtable<eDataType, ArrayList<clsAssociation>>(); 
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 01.07.2010, 22:49:49
	 *
	 * @param poDataStructurePA
	 */
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
		ArrayList <clsAssociation> oStructureList = ((Hashtable<eDataType, ArrayList<clsAssociation>>)moContent).get(poDataType); 
		if(oStructureList == null) {moContent.put(poDataType, poAssociatedDataStructures);} 
		else {oStructureList.addAll(poAssociatedDataStructures);} 
	}
}
