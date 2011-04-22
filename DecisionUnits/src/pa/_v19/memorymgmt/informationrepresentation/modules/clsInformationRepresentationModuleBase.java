/**
 * clsInformationRepresentationModuleBase.java: DecisionUnits - pa.informationrepresentation.modules
 * 
 * @author zeilinger
 * 23.05.2010, 21:36:06
 */
package pa._v19.memorymgmt.informationrepresentation.modules;

import java.util.ArrayList;

import pa._v19.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v19.memorymgmt.datatypes.clsDataStructurePA;
import pa._v19.memorymgmt.informationrepresentation.clsSearchSpaceHandler;
import pa._v19.memorymgmt.informationrepresentation.enums.eSearchMethod;
import pa._v19.tools.clsPair;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:36:06
 * 
 */
public abstract class clsInformationRepresentationModuleBase {
	protected String moSearchMethod; 
	protected clsInformationRepresentationModuleContainer moInformationRepresentationModulesContainer; 
	protected clsSearchSpaceHandler moSearchSpaceHandler;
	
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 23.05.2010, 22:45:28
	 * @param poSearchSpaceHandler 
	 *
	 */
	public clsInformationRepresentationModuleBase(clsInformationRepresentationModuleContainer poInformationRepresentationModulesContainer,
														clsSearchSpaceHandler poSearchSpaceHandler, String poSearchMethod) {
		moSearchMethod = poSearchMethod; 
		moInformationRepresentationModulesContainer = poInformationRepresentationModulesContainer;
		moSearchSpaceHandler = poSearchSpaceHandler; 
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 24.05.2010, 09:40:10
	 *
	 * @param poSearchPattern
	 * @return
	 */
	public ArrayList<clsPair<Double,clsDataStructureContainer>> searchDataStructure(int poReturnType, clsDataStructurePA poDataStructureUnknown){
		if(moSearchMethod.equals(eSearchMethod.LISTSEARCH.name())){ return listSearch(poReturnType, poDataStructureUnknown);}
		/*TODO - HZ 	here, additional search algorithms have to be initialized*/
		/*TODO - actually the listSearch algorithm is defined in clsModuleBase - hence, there is 
		 * 		 no difference made between the different modules*/
		throw new IllegalArgumentException(" defined search method unknown " + moSearchMethod);
	}
	
	public abstract ArrayList<clsPair<Double,clsDataStructureContainer>> listSearch(int poReturnType, clsDataStructurePA poDataStructureUnknown);

}