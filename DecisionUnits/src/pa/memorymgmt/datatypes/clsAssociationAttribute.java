/**
 * clsAssociationAttribute.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:53:16
 */
package pa.memorymgmt.datatypes;

import pa.memorymgmt.enums.eDataType;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:53:16
 * 
 */
public class clsAssociationAttribute extends clsAssociation{
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 24.05.2010, 15:53:46
	 *
	 */
	public clsAssociationAttribute(clsThingPresentationMesh poAssociationElementA, 
								   clsPhysicalRepresentation poAssociationElementB,
								   String poAssociationID,
								   eDataType peAssociationType) {
		super(poAssociationElementA, poAssociationElementB, poAssociationID, peAssociationType);		
	}
}
