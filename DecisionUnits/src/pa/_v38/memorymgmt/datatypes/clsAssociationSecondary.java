/**
 * clsAssociationWordPresentation.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:50:53
 */
package pa._v38.memorymgmt.datatypes;

import pa._v38.tools.clsTriple;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.memorymgmt.enums.ePredicate;

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
	private ePredicate moPredicate;	//This attribute is the predicate in an ontology

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
		clsTriple<Integer, eDataType, eContentType> poDataStructureIdentifier,
		clsSecondaryDataStructure poAssociationElementA,
		clsSecondaryDataStructure poAssociationElementB, ePredicate oPredicate) {
			
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
	
	public ePredicate getMoPredicate() {
		return moPredicate;
	}
	
	public void setMrPredicate(ePredicate oPredicate) {
		this.moPredicate = oPredicate;
	}
	
	@Override
	public String toString(){
		String oResult = "::"+this.moDataStructureType+"::";  
		oResult += this.moDS_ID + ":" + this.moPredicate + "|";
		
		oResult += associationToString("elementA:", moAssociationElementA);
		oResult += ":"; 
		oResult += associationToString("elementB:", moAssociationElementB);
		
		return oResult; 
	}
}
