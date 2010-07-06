/**
 * clsSecondaryDataStructureMgmt.java: DecisionUnits - pa.informationrepresentation
 * 
 * @author zeilinger
 * 19.05.2010, 07:47:02
 */
package pa.memorymgmt.informationrepresentation.modules;

import pa.memorymgmt.datatypes.clsDataStructureContainer;
import pa.memorymgmt.datatypes.clsSecondaryInformation;
import pa.memorymgmt.enums.eDataType;
import pa.memorymgmt.informationrepresentation.clsSearchSpaceHandler;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 19.05.2010, 07:47:02
 * 
 */
public class KB01_SecondaryDataStructureMgmt extends clsInformationRepresentationModuleBase{

	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 23.05.2010, 22:47:52
	 *
	 * @param poInformationRepresentationModulesContainer
	 * @param poSearchSpaceHandler 
	 */
	public KB01_SecondaryDataStructureMgmt(
			clsInformationRepresentationModuleContainer poInformationRepresentationModulesContainer, 
												clsSearchSpaceHandler poSearchSpaceHandler, String poSearchMethod) {
		super(poInformationRepresentationModulesContainer, poSearchSpaceHandler, poSearchMethod);
		// TODO (zeilinger) - Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 31.05.2010, 16:06:40
	 * 
	 * @see pa.informationrepresentation.ARSi10.modules.clsInformationRepresentationModuleBase#listSearch(java.lang.String, pa.informationrepresentation.datatypes.clsDataStructureContainer)
	 */
	@Override
	public clsDataStructureContainer listSearch(int poReturnType,
			clsDataStructureContainer poSearchPatternContainer) {
		
		//HZ Find another initialization value; 
		clsDataStructureContainer oDataStructureContainer = null; 
		
		for(eDataType element : eDataType.values()){
			if((poReturnType & element.nBinaryValue) != 0x0){
				//getSearchElement();  
				//loadSearchBuffer(element, (clsSecondaryInformation)poSearchPatternContainer); 
			} 
		}
		
		//Hashtable
		//getDataStructureType
		//compare?
		return oDataStructureContainer;
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 28.06.2010, 22:34:34
	 *
	 * @param element
	 * @param poSearchPatternContainer
	 */
	private void loadSearchBuffer(eDataType element,
			clsSecondaryInformation poSearchPatternContainer) {
		//moSearchSpaceHandler.returnSearchSpace(		
	}
}
