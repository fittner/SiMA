/**
 * clsAssociationWordPresentation.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:50:53
 */
package pa.knowledgebasehandler.datatypes;

import pa.knowledgebasehandler.enums.eAssociationType;

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
			clsDataStructurePA poAssociationElementA,
			clsWordPresentation poAssociationElementB,
			String poAssociationID) {
			super(poAssociationElementA, poAssociationElementB, poAssociationID, 
								eAssociationType.WORDPRESENTATION.toString()); 
		// TODO (zeilinger) - Auto-generated constructor stub
	}
}
