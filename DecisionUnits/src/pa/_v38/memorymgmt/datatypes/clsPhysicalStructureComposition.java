/**
 * clsPhysicalStructureComposition.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:49:15
 */
package pa._v38.memorymgmt.datatypes;

import java.util.ArrayList;

import pa._v38.tools.clsTriple;
import pa._v38.memorymgmt.enums.eDataType;


/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:49:15
 * 
 */
public abstract class clsPhysicalStructureComposition extends clsPhysicalRepresentation {
	/** External associations to location, DMs or other TPMs (Images) are placed here; @since 29.11.2011 11:13:42 */
	protected ArrayList<clsAssociation> moExternalAssociatedContent;
	/** Internal defining associations are put here, i.e. shape, color or other defining objects; @since 29.11.2011 11:13:05 */
	protected ArrayList<clsAssociation> moAssociatedContent;
	/**
	 * @author zeilinger
	 * 19.03.2011, 08:57:27
	 * 
	 * @return the moAssociatedContent
	 */
	public ArrayList<clsAssociation> getMoAssociatedContent() {
		return moAssociatedContent;
	}

	/**
	 * @author zeilinger
	 * 19.03.2011, 08:57:27
	 * 
	 * @param moAssociatedContent the moAssociatedContent to set
	 */
	public void setMoAssociatedContent(ArrayList<clsAssociation> poAssociatedContent) {
		this.moAssociatedContent = poAssociatedContent;
	}
	
	/**
	 * Get external associated content
	 * (wendt)
	 *
	 * @since 29.11.2011 11:16:49
	 *
	 * @return
	 */
	public ArrayList<clsAssociation> getExternalMoAssociatedContent() {
		return moExternalAssociatedContent;
	}
	
	/**
	 * Set external associated content
	 * 
	 * (wendt)
	 *
	 * @since 29.11.2011 11:16:52
	 *
	 * @param poExternalAssociatedContent
	 */
	public void setMoExternalAssociatedContent(ArrayList<clsAssociation> poExternalAssociatedContent) {
		this.moExternalAssociatedContent = poExternalAssociatedContent;
	}
	

	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 24.05.2010, 12:40:36
	 * @param object2 
	 * @param object 
	 *
	 */
	public clsPhysicalStructureComposition(clsTriple<Integer, eDataType, String> poDataStructureIdentifier) {
		super(poDataStructureIdentifier);
		moAssociatedContent = new ArrayList<clsAssociation>(); 
		moExternalAssociatedContent = new ArrayList<clsAssociation>(); 
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 17.08.2010, 21:59:35
	 *
	 * @param poDataStructure
	 * @return
	 */
	public abstract boolean contain(clsDataStructurePA poDataStructure); 
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 01.07.2010, 22:49:49
	 *
	 * @param poDataStructurePA
	 */
	public abstract void assignDataStructure(clsAssociation poDataStructurePA); 
	
	/**
	 * Add internal associations
	 * 
	 * (wendt)
	 *
	 * @author zeilinger
	 * 24.05.2010, 14:40:45
	 *
	 * @param poAssociatedWordPresentations
	 */	
	protected void addAssociations(ArrayList<clsAssociation> poAssociatedDataStructures) {
		moAssociatedContent.addAll(poAssociatedDataStructures);  
	}
	
	/**
	 * Add external associations
	 * 
	 * (wendt)
	 *
	 * @since 29.11.2011 11:21:45
	 *
	 * @param poAssociatedDataStructures
	 */
	protected void addExternalAssociations(ArrayList<clsAssociation> poAssociatedDataStructures) {
		moExternalAssociatedContent.addAll(poAssociatedDataStructures);  
	}
}
