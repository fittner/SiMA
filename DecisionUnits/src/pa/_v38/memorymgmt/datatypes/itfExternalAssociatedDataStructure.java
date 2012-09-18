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
 * Jun 13, 2012, 11:42:57 AM
 * 
 */
public interface itfExternalAssociatedDataStructure {

	public ArrayList<clsAssociation> moExternalAssociatedContent = null;
	
	public ArrayList<clsAssociation> getExternalMoAssociatedContent() ;
	public void setMoExternalAssociatedContent(ArrayList<clsAssociation> moAssociatedContent);
	public double getNumbExternalAssociations() ;
	public void addExternalAssociations(ArrayList<clsAssociation> poAssociatedDataStructures);
	public void addExternalAssociation(clsAssociation poAssociatedDataStructure);
	
	// public sortByWeights();
}
