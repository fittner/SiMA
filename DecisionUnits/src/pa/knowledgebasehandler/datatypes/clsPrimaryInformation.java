/**
 * clsPrimaryInformation.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:42:21
 */
package pa.knowledgebasehandler.datatypes;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:42:21
 * 
 */
public class clsPrimaryInformation extends clsDataStructureContainer{
	public clsPhysicalRepresentation moExternalRepresetnationDataStructure; 
	public clsHomeostaticRepresentation moInternalRepresentationDataStructure; 
	
	public clsPrimaryInformation(){
		moExternalRepresetnationDataStructure = null; 
		moInternalRepresentationDataStructure = null; 
	}
}
