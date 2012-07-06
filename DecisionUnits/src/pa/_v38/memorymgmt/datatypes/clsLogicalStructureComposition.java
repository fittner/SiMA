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
public abstract class clsLogicalStructureComposition extends clsSecondaryDataStructure implements itfAssociatedDataStructure, itfExternalAssociatedDataStructure{
	/** Add internal associations to WP or other WPM, which are defining this structure; @since 29.11.2011 11:26:20 */
	protected ArrayList<clsAssociation> moInternalAssociatedContent;
	
	/**Add external associations to other WPM; @since 29.11.2011 11:29:16 */
	protected ArrayList<clsAssociation> moExternalAssociatedContent;
	
	/**
	 * Get all external associations
	 * 
	 * @since 29.11.2011 11:30:33
	 * 
	 * @return the moExternalAssociatedContent
	 */
	public ArrayList<clsAssociation> getExternalAssociatedContent() {
		return moExternalAssociatedContent;
	}

	/**
	 * Set the external associations
	 * 
	 * @since 29.11.2011 11:30:33
	 * 
	 * @param moExternalAssociatedContent the moExternalAssociatedContent to set
	 */
	@Override
	public void setMoExternalAssociatedContent(ArrayList<clsAssociation> poExternalAssociatedContent) {
		this.moExternalAssociatedContent = poExternalAssociatedContent;
	}

	/**
	 * @author wendt
	 * 19.03.2011, 08:57:27
	 * 
	 * @return the moAssociatedContent
	 */
	@Override
	public ArrayList<clsAssociation> getMoAssociatedContent() {
		return moInternalAssociatedContent;
	}
	
	/**
	 * @author schaat
	 * 19.06.2012, 08:57:27
	 * 
	 * @return the moExternalAssociatedContent
	 */
	@Override
	public ArrayList<clsAssociation> getExternalMoAssociatedContent() {
		return moExternalAssociatedContent;
	}

	/**
	 * @author wendt
	 * 19.03.2011, 08:57:27
	 * 
	 * @param moAssociatedContent the moAssociatedContent to set
	 */
	@Override
	public void setMoAssociatedContent(ArrayList<clsAssociation> poAssociatedContent) {
		this.moInternalAssociatedContent = poAssociatedContent;
	}

	/**
	 * DOCUMENT (wendt) - insert description 
	 * 
	 * @author wendt
	 * 24.05.2010, 12:40:36
	 * @param object2 
	 * @param object 
	 *
	 */
	public clsLogicalStructureComposition(clsTriple<Integer, eDataType, String> poDataStructureIdentifier) {
		super(poDataStructureIdentifier);
		moInternalAssociatedContent = new ArrayList<clsAssociation>(); 
		moExternalAssociatedContent = new ArrayList<clsAssociation>(); 
	}
	
	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @author wendt
	 * 17.08.2010, 21:59:35
	 *
	 * @param poDataStructure
	 * @return
	 */
	public abstract boolean contain(clsDataStructurePA poDataStructure); 
	
	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @author wendt
	 * 01.07.2010, 22:49:49
	 *
	 * @param poDataStructurePA
	 */
	@Override
	public abstract void assignDataStructure(clsAssociation poDataStructurePA); 
	
	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @author wendt
	 * 24.05.2010, 14:40:45
	 *
	 * @param poAssociatedWordPresentations
	 */
	@Override
	public void addAssociations(ArrayList<clsAssociation> poAssociatedDataStructures) {
		moInternalAssociatedContent.addAll(poAssociatedDataStructures);  
	}
	
	/**
	 * DOCUMENT (schaat) - insert description
	 *
	 * @author schaat
	 * 24.06.2012, 14:40:45
	 *
	 * @param poAssociatedWordPresentations
	 */
	@Override
	public void addExternalAssociations(ArrayList<clsAssociation> poAssociatedDataStructures) {
		moExternalAssociatedContent.addAll(poAssociatedDataStructures);
	}
}
