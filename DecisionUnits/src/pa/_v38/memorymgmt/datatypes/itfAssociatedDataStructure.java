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
public interface itfAssociatedDataStructure {
	public ArrayList<clsAssociation> moAssociatedContent = null;
	
	public ArrayList<clsAssociation> getMoAssociatedContent() ;
	public void setMoAssociatedContent(ArrayList<clsAssociation> moAssociatedContent);
	public double getNumbAssociations() ;
	public void addAssociations(ArrayList<clsAssociation> poAssociatedDataStructures);
	public void assignDataStructure(clsAssociation poDataStructurePA);
	
	// public sortByWeights();
}
