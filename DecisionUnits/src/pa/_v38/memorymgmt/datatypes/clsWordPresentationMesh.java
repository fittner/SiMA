/**
 * CHANGELOG
 *
 * 27.07.2011 wendt - File created
 *
 */
package pa._v38.memorymgmt.datatypes;

import java.util.ArrayList;

import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.tools.clsTriple;

/**
 * A mesh of >=1 word presentations. If a word presentation is a word, then the word presentation is a sentence 
 * 
 * @author wendt
 * 27.07.2011, 13:41:04
 * 
 */
public class clsWordPresentationMesh extends clsLogicalStructureComposition {

	private String moContent = "UNDEFINED";
	
	/**
	 * DOCUMENT (wendt) - insert description 
	 *
	 * @since 27.07.2011 20:59:03
	 *
	 * @param poDataStructureIdentifier
	 */
	public clsWordPresentationMesh(
			clsTriple<Integer, eDataType, String> poDataStructureIdentifier, 
			ArrayList<clsAssociation> poAssociatedStructures, Object poContent) {
		super(poDataStructureIdentifier);
		
		moContent = (String)poContent;
		setAssociations(poAssociatedStructures);
		
		// TODO (wendt) - Auto-generated constructor stub
	}

	public String getMoContent() {
		return moContent;
	}

	/**
	 * @author zeilinger
	 * 17.03.2011, 00:52:49
	 * 
	 * @param moContent the moContent to set
	 */
	public void setMoContent(String moContent) {
		this.moContent = moContent;
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 16.08.2010, 22:12:06
	 *
	 * @param poAssociatedTemporalStructures
	 */
	private void setAssociations(
			ArrayList<clsAssociation> poAssociatedStructures) {
		moAssociatedContent = poAssociatedStructures;
	}
	
	/* (non-Javadoc)
	 *
	 * @since 27.07.2011 20:59:16
	 * 
	 * @see pa._v38.memorymgmt.datatypes.itfComparable#compareTo(pa._v38.memorymgmt.datatypes.clsDataStructurePA)
	 */
	@Override
	public double compareTo(clsDataStructurePA poDataStructure) {
		// TODO (wendt) - Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 *
	 * @since 27.07.2011 20:59:16
	 * 
	 * @see pa._v38.memorymgmt.datatypes.clsLogicalStructureComposition#contain(pa._v38.memorymgmt.datatypes.clsDataStructurePA)
	 */
	@Override
	public boolean contain(clsDataStructurePA poDataStructure) {
		// TODO (wendt) - Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 *
	 * @since 27.07.2011 20:59:16
	 * 
	 * @see pa._v38.memorymgmt.datatypes.clsLogicalStructureComposition#assignDataStructure(pa._v38.memorymgmt.datatypes.clsAssociation)
	 */
	@Override
	public void assignDataStructure(clsAssociation poDataStructurePA) {
		// TODO (wendt) - Auto-generated method stub
		
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
        try {
        	clsWordPresentationMesh oClone = (clsWordPresentationMesh)super.clone();
           	return oClone;
        } catch (CloneNotSupportedException e) {
           return e;
        }
	}		
	
	@Override
	public String toString(){
			String oResult = "::"+this.moDataStructureType+"::";  
			oResult += this.moDS_ID + ":" + this.moContentType + ":" + this.moContent;
			
			return oResult; 
	}

}
