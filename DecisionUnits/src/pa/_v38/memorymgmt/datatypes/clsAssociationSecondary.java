/**
 * clsAssociationWordPresentation.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:50:53
 */
package pa._v38.memorymgmt.datatypes;

import pa._v38.tools.clsTripple;
import pa._v38.memorymgmt.enums.eDataType;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:50:53
 * 
 */
public class clsAssociationSecondary extends clsAssociation{
	//AW 20110602: This type of association will connect Word Presentations with each other
	//This association has connection A, connection B, where the direction of the association always is 
	//from A to B, an association attribute like "hierarchical" or "temporal" and an association weight
	//This is the basic association in the secondary process for creating acts
	
	//Association attribute
	private String moPredicate;	//This attribute is the predicate in an ontology

	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 24.05.2010, 13:32:59
	 *
	 * @param clsDataStructurePA
	 * @param poWordPresentation
	 */
	public clsAssociationSecondary(
			clsTripple<Integer, eDataType, String> poDataStructureIdentifier,
			clsWordPresentation poAssociationElementA,
			clsDataStructurePA poAssociationElementB, String oPredicate) {
			
			super(poDataStructureIdentifier, poAssociationElementA, poAssociationElementB);
			moPredicate = oPredicate;
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 13.07.2010, 20:58:24
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
	 * 17.08.2010, 21:07:20
	 * 
	 * @see pa._v38.memorymgmt.datatypes.clsAssociation#getLeafElement(pa._v38.memorymgmt.datatypes.clsDataStructurePA)
	 */
	@Override
	public clsDataStructurePA getLeafElement() {
		return moAssociationElementB; 
	}
	
	@Override
	public clsDataStructurePA getRootElement() {
		return moAssociationElementA;
	}
	
	public String getMoPredicate() {
		return moPredicate;
	}
	
	public void setMrPredicate(String oPredicate) {
		this.moPredicate = oPredicate;
	}
}
