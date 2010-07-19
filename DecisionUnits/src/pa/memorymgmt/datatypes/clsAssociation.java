/**
 * clsAssociation.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:44:04
 */
package pa.memorymgmt.datatypes;

import pa.memorymgmt.enums.eDataType;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:44:04
 * 
 */
public abstract class clsAssociation extends clsDataStructurePA{
	public double mrImperativeFactor; 
	public double mrWeight; 
	public clsDataStructurePA moAssociationElementA;
	public clsDataStructurePA moAssociationElementB;

	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 24.05.2010, 15:50:41
	 *
	 */
	public clsAssociation(String poAssociationID, eDataType poAssociationType, clsDataStructurePA poAssociationElementA, clsDataStructurePA poAssociationElementB) {
		super(poAssociationID, poAssociationType);
		mrImperativeFactor = 1.0; 
		mrWeight = 1.0; 
		moAssociationElementA = poAssociationElementA; 
		moAssociationElementB = poAssociationElementB; 
	}
	
	@Override
	public String toString(){
		String oResult = "::"+this.oDataStructureType+"::";  
		if(this.oDataStructureID != null) oResult += this.oDataStructureID + ":";
			
		oResult += "elementB " + moAssociationElementB.toString()+" / "; 
			
		if (oResult.length() > 4) {
			oResult = oResult.substring(0, oResult.length()-3);
		}
		return oResult; 
	}
}
