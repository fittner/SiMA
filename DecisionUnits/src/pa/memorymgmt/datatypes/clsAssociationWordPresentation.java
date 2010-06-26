/**
 * clsAssociationWordPresentation.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:50:53
 */
package pa.memorymgmt.datatypes;

import pa.memorymgmt.enums.eDataType;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:50:53
 * 
 */
public class clsAssociationWordPresentation extends clsAssociation{

	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 24.05.2010, 13:32:59
	 *
	 * @param clsDataStructurePA
	 * @param poWordPresentation
	 */
	public clsAssociationWordPresentation(
			clsWordPresentation poAssociationElementA,
			clsDataStructurePA poAssociationElementB,
			String poAssociationID, 
			eDataType peAssociationType) {
			
			super(poAssociationElementA, poAssociationElementB, poAssociationID, peAssociationType); 
	}
}
