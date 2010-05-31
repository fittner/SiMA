/**
 * clsAssociationTime.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:52:50
 */
package pa.informationrepresentation.datatypes;

import pa.informationrepresentation.enums.eAssociationType;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:52:50
 * 
 */
public class clsAssociationTime extends clsAssociation{

	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 24.05.2010, 16:05:04
	 *
	 * @param poAssociationElementA
	 * @param poAssociationElementB
	 * @param poAssociationID
	 * @param poAssociationType
	 */
	public clsAssociationTime(clsDataStructurePA poAssociationElementA,
			clsDataStructurePA poAssociationElementB, String poAssociationID) {
		
		super(poAssociationElementA, poAssociationElementB, 
				poAssociationID, eAssociationType.TEMPORAL.toString());
		// TODO (zeilinger) - Auto-generated constructor stub
	}
	
	
}
