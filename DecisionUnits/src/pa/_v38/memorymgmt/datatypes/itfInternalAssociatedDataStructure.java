/**
 * CHANGELOG
 *
 * Jun 13, 2012 schaat - File created
 *
 */
package pa._v38.memorymgmt.datatypes;

import java.util.ArrayList;

/**
 * DOCUMENT (schaat) - insert description 
 * 
 * @author schaat
 * Jun 13, 2012, 11:17:57 AM
 * 
 */
public interface itfInternalAssociatedDataStructure {
	public ArrayList<clsAssociation> moAssociatedContent = null;
	
	public ArrayList<clsAssociation> getMoInternalAssociatedContent() ;
	public void setMoInternalAssociatedContent(ArrayList<clsAssociation> moInternalAssociatedContent);
	public double getNumbInternalAssociations() ;
	public void addInternalAssociations(ArrayList<clsAssociation> poInternalAssociatedDataStructures);
	public void assignDataStructure(clsAssociation poDataStructurePA);
	
	// public sortByWeights();
}
