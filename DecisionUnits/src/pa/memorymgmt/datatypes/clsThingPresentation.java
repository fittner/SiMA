/**
 * clsThingPresentation.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:48:16
 */
package pa.memorymgmt.datatypes;

import pa.memorymgmt.enums.eDataType;


/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:48:16
 * 
 */
public class clsThingPresentation extends clsPhysicalRepresentation{
	
	public String moContentName = "";
	public Object moContent = null;
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 24.05.2010, 12:43:50
	 *
	 * @param poWordPresentationAssociation
	 */
	public clsThingPresentation(String poDataStructureName,
								eDataType poDataStructureType, 
								String poContentName,
								Object poContent) {
		
		super(poDataStructureName, poDataStructureType);
		
		moContentName = poContentName; 
		moContent = poContent;
	}
}
