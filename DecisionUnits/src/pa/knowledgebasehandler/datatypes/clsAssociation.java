/**
 * clsAssociation.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:44:04
 */
package pa.knowledgebasehandler.datatypes;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:44:04
 * 
 */
public abstract class clsAssociation extends clsDataStructurePA{
	public String moAssociationID; 
	public String moAssociationType;  
	
	public clsDataStructurePA moAssociationElementA;
	public clsDataStructurePA moAssociationElementB;

	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 24.05.2010, 15:50:41
	 *
	 */
	public clsAssociation(clsDataStructurePA poAssociationElementA, clsDataStructurePA poAssociationElementB, 
						  String poAssociationID, String poAssociationType) {
		moAssociationElementA = poAssociationElementA; 
		moAssociationElementB = poAssociationElementB; 
		moAssociationID = poAssociationID; 
		moAssociationType = poAssociationType; 
	}
}
