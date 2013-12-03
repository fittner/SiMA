/**
 * clsPhysicalStructureComposition.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:49:15
 */
package pa._v38.memorymgmt.datatypes;

import java.util.ArrayList;

import datatypes.helpstructures.clsTriple;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;


/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:49:15
 * @deprecated
 */
public abstract class clsPhysicalStructureComposition extends clsPhysicalRepresentation implements itfInternalAssociatedDataStructure, itfExternalAssociatedDataStructure{
	/** External associations to location, DMs or other TPMs (Images) are placed here; @since 29.11.2011 11:13:42 */
	protected ArrayList<clsAssociation> moExternalAssociatedContent;
	/** Internal defining associations are put here, i.e. shape, color or other defining objects; @since 29.11.2011 11:13:05 */
	protected ArrayList<clsAssociation> moInternalAssociatedContent;
	/**
	 * @author zeilinger
	 * 19.03.2011, 08:57:27
	 * 
	 * @return the moAssociatedContent
	 */
	@Override
	public ArrayList<clsAssociation> getInternalAssociatedContent() {
		return moInternalAssociatedContent;
	}

	/**
	 * @author zeilinger
	 * 19.03.2011, 08:57:27
	 * 
	 * @param moInternalAssociatedContent the moAssociatedContent to set
	 */
	@Override
	public void setInternalAssociatedContent(ArrayList<clsAssociation> poAssociatedContent) {
		this.moInternalAssociatedContent = poAssociatedContent;
	}
	
	/**
	 * Get external associated content
	 * (wendt)
	 *
	 * @since 29.11.2011 11:16:49
	 *
	 * @return
	 */
	@Override
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
	@Override
	public void setExternalAssociatedContent(ArrayList<clsAssociation> poExternalAssociatedContent) {
		this.moExternalAssociatedContent = poExternalAssociatedContent;
	}
	
	/**
	 * Set remove internal associated content
	 * 
	 * (schaat)
	 *
	 * @since 14.08.2012 11:16:52
	 *
	 * @param poInternalAssociation
	 */
	public void removeInternalAssociation(clsAssociation poInternalAssociation) {
		this.moInternalAssociatedContent.remove(poInternalAssociation);
	}
	
	/**
	 * Set external associated content
	 * 
	 * (schaat)
	 *
	 * @since 14.08.2012 11:16:52
	 *
	 * @param poInternalAssociation
	 */
	@Override
	public void addExternalAssociation (clsAssociation poAssociation) {
		this.moExternalAssociatedContent.add(poAssociation);
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
	public clsPhysicalStructureComposition(clsTriple<Integer, eDataType, eContentType> poDataStructureIdentifier) {
		super(poDataStructureIdentifier);
		moInternalAssociatedContent = new ArrayList<clsAssociation>(); 
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
	@Override
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
	@Override
	public void addInternalAssociations(ArrayList<clsAssociation> poAssociatedDataStructures) {
		moInternalAssociatedContent.addAll(poAssociatedDataStructures);  
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
	@Override
	public void addExternalAssociations(ArrayList<clsAssociation> poAssociatedDataStructures) {
		moExternalAssociatedContent.addAll(poAssociatedDataStructures);  
	}
}
