/**
 * clsAssociationPrimaryDM.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author schaat
 * 10.07.2012, 21:52:50
 */
package pa._v38.memorymgmt.datatypes;

import pa._v38.tools.clsTriple;
import pa._v38.memorymgmt.enums.eDataType;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:52:50
 * 
 */
public class clsAssociationPrimaryDM extends clsAssociation{
	//AW 20110602: This type of association will connect TI with each other
	//This association only has connection A, connection B and an association weight

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
	public clsAssociationPrimaryDM(clsTriple<Integer, eDataType, String> poDataStructureIdentifier,
			clsDriveMesh poAssociationElementA,
			clsDriveMesh poAssociationElementB) {
		
		super(poDataStructureIdentifier, poAssociationElementA, poAssociationElementB);
		// TODO (zeilinger) - Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 13.07.2010, 20:58:17
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public double compareTo(clsDataStructurePA o) {
		// TODO (zeilinger) - Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 17.08.2010, 21:07:47
	 * 
	 * @see pa._v38.memorymgmt.datatypes.clsAssociation#getLeafElement(pa._v38.memorymgmt.datatypes.clsDataStructurePA)
	 */
	@Override
	public clsDataStructurePA getLeafElement() {
		//TIs have one element that form the root for associated Time Associations
		//This element is always moAssociationElementA; Hence the B element is returned
		return moAssociationElementB;
	}
	
	@Override
	public clsDataStructurePA getRootElement() {
		return moAssociationElementA;
	}
	
	/**
	 * This association has no direction, therefore, it has to be checked if a structure is contained in this association
	 * and return the other element, if the structure has been found, i. e. if the found element is the Element A, then the 
	 * Element B is returned. If the structure is not found, null is returned
	 * (wendt)
	 *
	 * @since 24.07.2011 13:33:00
	 *
	 * @param poDS
	 * @return
	 */
	public clsDataStructurePA containsInstanceID(clsDataStructurePA poDS) {
		if ((moAssociationElementB.moDSInstance_ID == poDS.getMoDSInstance_ID())) {
			return moAssociationElementA;
		}
		if ((moAssociationElementA.moDSInstance_ID == poDS.getMoDSInstance_ID())) {
			return moAssociationElementB;
		} else {
			return null;
		}
	}

	/* (non-Javadoc)
	 *
	 * @since 03.01.2012 20:02:03
	 * 
	 * @see pa._v38.memorymgmt.datatypes.clsAssociation#setLeafElement(pa._v38.memorymgmt.datatypes.clsDataStructurePA)
	 */
	@Override
	public void setLeafElement(clsDataStructurePA poDS) {
		moAssociationElementB = poDS;
		// TODO (wendt) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @since 03.01.2012 20:02:03
	 * 
	 * @see pa._v38.memorymgmt.datatypes.clsAssociation#setRootElement(pa._v38.memorymgmt.datatypes.clsDataStructurePA)
	 */
	@Override
	public void setRootElement(clsDataStructurePA poDS) {
		moAssociationElementA = poDS;
		// TODO (wendt) - Auto-generated method stub
		
	}
	
}
