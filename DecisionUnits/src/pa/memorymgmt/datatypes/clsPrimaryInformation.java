/**
 * clsPrimaryInformation.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:42:21
 */
package pa.memorymgmt.datatypes;

import java.util.ArrayList;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:42:21
 * 
 */
public class clsPrimaryInformation extends clsDataStructureContainer{
	public clsPrimaryDataStructure moDataStructure; 
	public ArrayList<clsAssociation> moAssociatedDataStructures; 
//	public clsPhysicalRepresentation moExternalRepresetnationDataStructure; 
//	public clsHomeostaticRepresentation moInternalRepresentationDataStructure; 
	
	public clsPrimaryInformation(){
		moDataStructure = null; 
		moAssociatedDataStructures = null; 
	}
}
