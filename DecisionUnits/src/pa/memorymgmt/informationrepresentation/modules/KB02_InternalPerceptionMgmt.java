/**
 * KB02_InternalPerceptionMgmt.java: DecisionUnits - pa.informationrepresentation
 * 
 * @author zeilinger
 * 19.05.2010, 07:49:28
 */
package pa.memorymgmt.informationrepresentation.modules;

import java.util.ArrayList;

import pa.memorymgmt.datatypes.clsDataStructureContainer;
import pa.memorymgmt.datatypes.clsDataStructurePA;
import pa.memorymgmt.datatypes.clsPrimaryInformation;
import pa.memorymgmt.enums.eDataType;
import pa.memorymgmt.informationrepresentation.clsSearchSpaceHandler;
import pa.tools.clsPair;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 19.05.2010, 07:49:28
 * 
 */
public class KB02_InternalPerceptionMgmt extends clsInformationRepresentationModuleBase{

	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 23.05.2010, 22:59:15
	 *
	 * @param poInformationRepresentationModulesContainer
	 * @param poSearchSpaceHandler 
	 */
	public KB02_InternalPerceptionMgmt(
			clsInformationRepresentationModuleContainer poInformationRepresentationModulesContainer, 
											clsSearchSpaceHandler poSearchSpaceHandler, String poSearchMethod) {
		super(poInformationRepresentationModulesContainer, poSearchSpaceHandler, poSearchMethod);
		// TODO (zeilinger) - Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 31.05.2010, 16:06:58
	 * 
	 * @see pa.informationrepresentation.ARSi10.modules.clsInformationRepresentationModuleBase#listSearch(java.lang.String, pa.informationrepresentation.datatypes.clsDataStructureContainer)
	 */
	@Override
	public clsDataStructureContainer listSearch(int poReturnType,clsDataStructureContainer poSearchPatternContainer) {

		clsDataStructureContainer oDataStructureContainer = null; 
		ArrayList<clsPair<Double,clsDataStructurePA>> oMatchedDataStructures = compareELements(poSearchPatternContainer); 
		
		for(eDataType element : eDataType.values()){
			if((poReturnType & element.nBinaryValue) != 0x0){
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
	 * 02.07.2010, 07:12:23
	 *
	 * @param moDataStructure
	 * @return
	 */
	private ArrayList<clsPair<Double,clsDataStructurePA>> compareELements(clsDataStructureContainer poSearchPatternContainer) {
		clsDataStructurePA oDataStructureSearchPattern = ((clsPrimaryInformation)poSearchPatternContainer).moDataStructure;
				
		return clsDataStructureComparison.compareDataStructures(oDataStructureSearchPattern, moSearchSpaceHandler.returnSearchSpace());
	}
}
