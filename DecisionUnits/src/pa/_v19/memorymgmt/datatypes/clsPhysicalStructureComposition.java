/**
 * clsPhysicalStructureComposition.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:49:15
 */
package pa._v19.memorymgmt.datatypes;

import java.util.ArrayList;

import pa._v19.memorymgmt.enums.eDataType;
import pa._v19.tools.clsTripple;


/**
 * 
 * @author zeilinger
 * 23.05.2010, 21:49:15
 * 
 */
public abstract class clsPhysicalStructureComposition extends clsPhysicalRepresentation {
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
	public void setMoAssociatedContent(ArrayList<clsAssociation> moAssociatedContent) {
		this.moAssociatedContent = moAssociatedContent;
	}

	/**
	 * 
	 * @author zeilinger
	 * 24.05.2010, 12:40:36
	 * @param object2 
	 * @param object 
	 *
	 */
	public clsPhysicalStructureComposition(clsTripple<Integer, eDataType, String> poDataStructureIdentifier) {
		super(poDataStructureIdentifier);
		moAssociatedContent = new ArrayList<clsAssociation>(); 
	}
	
	/**
	 *
	 * @author zeilinger
	 * 17.08.2010, 21:59:35
	 *
	 * @param poDataStructure
	 * @return
	 */
	public abstract boolean contain(clsDataStructurePA poDataStructure); 
	
	/**
	 *
	 * @author zeilinger
	 * 01.07.2010, 22:49:49
	 *
	 * @param poDataStructurePA
	 */
	public abstract void assignDataStructure(clsAssociation poDataStructurePA); 
	
	/**
	 *
	 * @author zeilinger
	 * 24.05.2010, 14:40:45
	 *
	 * @param poAssociatedWordPresentations
	 */
		
	protected void applyAssociations(ArrayList<clsAssociation> poAssociatedDataStructures) {
		moAssociatedContent.addAll(poAssociatedDataStructures);  
	}
}
