/**
 * CHANGELOG
 *
 * Jun 13, 2012 schaat - File created
 *
 */
package base.datatypes;

import java.util.ArrayList;

/**
 * DOCUMENT (schaat) - insert description 
 * 
 * @author schaat
 * Jun 13, 2012, 11:42:57 AM
 * 
 */
public interface itfExternalAssociatedDataStructure {

	//public ArrayList<clsAssociation> moExternalAssociatedContent = null;
	
	public ArrayList<clsAssociation> getExternalAssociatedContent() ;
	public void setExternalAssociatedContent(ArrayList<clsAssociation> moAssociatedContent);
	public double getNumbExternalAssociations() ;
	public void addExternalAssociations(ArrayList<clsAssociation> poAssociatedDataStructures);
	public void addExternalAssociation(clsAssociation poAssociatedDataStructure);
	
	// public sortByWeights();
}
