/**
 * clsInformationRepresentationModuleBase.java: DecisionUnits - pa.informationrepresentation.modules
 * 
 * @author zeilinger
 * 23.05.2010, 21:36:06
 */
package pa._v38.memorymgmt.informationrepresentation.modules;

import java.util.ArrayList;

import pa._v38.tools.clsPair;
import pa._v38.tools.toText;
import pa._v38.interfaces.itfInspectorInternalState;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.informationrepresentation.clsSearchSpaceHandler;
import pa._v38.memorymgmt.informationrepresentation.enums.eSearchMethod;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:36:06
 * 
 */
public abstract class clsInformationRepresentationModuleBase implements itfInspectorInternalState {
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

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 21.04.2011, 16:31:05
	 * 
	 * @see pa._v38.interfaces.itfInspectorInternalState#stateToHTML()
	 */
	@Override
	public String stateToTEXT() {
		String html = "";
		
		html  = toText.h1("clsInformationRepresentationModuleBase");
		html += toText.valueToTEXT("moSearchMethod", moSearchMethod);
		html += toText.newline+moSearchSpaceHandler.stateToTEXT();
		html += toText.newline+moInformationRepresentationModulesContainer.stateToTEXT();
		
		return html;
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
	
	
	
	public ArrayList<clsPair<Double, clsDataStructureContainer>> searchDataContainer(int poReturnType, clsDataStructureContainer poDataContainerUnknown){
		//Use Listsearch for containers
		
		if(moSearchMethod.equals(eSearchMethod.LISTSEARCH.name())){ return listSearch(poReturnType, poDataContainerUnknown);}
		
		throw new IllegalArgumentException(" defined search method unknown " + moSearchMethod);
	}
	
	public abstract ArrayList<clsPair<Double,clsDataStructureContainer>> listSearch(int poReturnType, clsDataStructurePA poDataStructureUnknown);
	public abstract ArrayList<clsPair<Double, clsDataStructureContainer>> listSearch(int poReturnType, clsDataStructureContainer poDataContainerUnknown);
}
