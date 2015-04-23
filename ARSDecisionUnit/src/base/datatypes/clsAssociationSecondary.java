/**
 * clsAssociationWordPresentation.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:50:53
 */
package base.datatypes;

import memorymgmt.enums.eContentType;
import memorymgmt.enums.eDataType;
import memorymgmt.enums.ePredicate;
import base.datatypes.helpstructures.clsTriple;

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
	
	public ePredicate getPredicate() {
		return moPredicate;
	}
	
	public void setMrPredicate(ePredicate oPredicate) {
		this.moPredicate = oPredicate;
	}
	
    /**
     * Check if two instances, which are not the same instance are the same
     *
     * @author wendt
     * @since 08.10.2013 10:14:28
     *
     * @param ds
     * @return
     */
    public boolean isEquivalentDataStructure(clsAssociationSecondary ds) {
        boolean isEqual = false;
        
        if (ds.getClass().getName().equals(this.getClass().getName()) &&
            ds.getDS_ID()==this.moDS_ID &&
            ds.getContentType()==this.getContentType() &&
            ds.getPredicate().equals(this.getPredicate()) &&
            ds.getLeafElement().isEquivalentDataStructure(this.getLeafElement()) &&
            ds.getRootElement().isEquivalentDataStructure(this.getRootElement())) {
            isEqual=true;
        }
        
        return isEqual;
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
