/**
 * clsPrimaryInformation.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:42:21
 */
package pa.informationrepresentation.datatypes;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:42:21
 * 
 */
public class clsPrimaryInformation extends clsDataStructureContainer{
	public clsExternalRepresentation moExternalRepresetnationDataStructure; 
	public clsInternalRepresentation moInternalRepresentationDataStructure; 
	
	public clsPrimaryInformation(){
		moExternalRepresetnationDataStructure = null; 
		moInternalRepresentationDataStructure = null; 
	}
}
