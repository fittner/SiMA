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
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;

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

		if(moSearchMethod.equals(eSearchMethod.GRAPHSEARCH.name())){ 
			
			return associativeSearch(poReturnType, poDataStructureUnknown);
		}
				
		if(moSearchMethod.equals(eSearchMethod.LISTSEARCH.name())){ return listSearch(poReturnType, poDataStructureUnknown);}
		/*TODO - HZ 	here, additional search algorithms have to be initialized*/
		/*TODO - actually the listSearch algorithm is defined in clsModuleBase - hence, there is 
		 * 		 no difference made between the different modules*/
		
		
		
		throw new IllegalArgumentException(" defined search method unknown " + moSearchMethod);
	}
	
	/**
	 * Start the list search for a container as input
	 *
	 * @since 14.07.2011 16:12:29
	 *
	 * @param poReturnType
	 * @param poDataContainerUnknown
	 * @return
	 */
	public ArrayList<clsPair<Double, clsDataStructureContainer>> searchDataContainer(int poReturnType, clsDataStructureContainer poDataContainerUnknown, double prThreshold){
		//Use Listsearch for containers
		
		if(moSearchMethod.equals(eSearchMethod.LISTSEARCH.name())){ return listSearchContainer(poReturnType, poDataContainerUnknown, prThreshold);}
		
		throw new IllegalArgumentException(" defined search method unknown " + moSearchMethod);
	}
	
	public clsDataStructureContainer getContainer(clsDataStructurePA poInput) {
		return clsDataStructureComparison.getCompleteContainer(poInput, moSearchSpaceHandler);
	}
	
	public clsThingPresentationMesh getMesh(clsThingPresentationMesh poInput, int pnLevel) {
		try {
			clsDataStructureComparison.getCompleteMesh(poInput, moSearchSpaceHandler, pnLevel);
		} catch (Exception e) {
			// TODO (wendt) - Auto-generated catch block
			e.printStackTrace();
		}
		return poInput;
	}
	
	public clsWordPresentationMesh getMesh(clsWordPresentationMesh poInput, int pnLevel) {
		return clsDataStructureComparison.getCompleteMesh(poInput, moSearchSpaceHandler, pnLevel);
	}
	
	
	
//	public clsDataStructureContainer getMesh(clsDataStructurePA poInput) {
//		return clsDataStructureComparison.getCompleteContainer(poInput, moSearchSpaceHandler);
//	}
	
	public abstract ArrayList<clsPair<Double,clsDataStructureContainer>> listSearch(int poReturnType, clsDataStructurePA poDataStructureUnknown);
	public abstract ArrayList<clsPair<Double, clsDataStructureContainer>> listSearchContainer(int poReturnType, clsDataStructureContainer poDataContainerUnknown, double prThreshold);
	public abstract ArrayList<clsPair<Double,clsDataStructureContainer>> associativeSearch(int poReturnType, clsDataStructurePA poDataStructureUnknown);
	
	/**
	 * Start the list search for a container as input
	 *
	 * @since 14.07.2011 16:12:29
	 *
	 * @param poReturnType
	 * @param poDataContainerUnknown
	 * @return
	 */
	public ArrayList<clsPair<Double, clsDataStructurePA>> searchDataMesh(int poReturnType, clsDataStructurePA poDataStructureUnknown, double prThreshold, int pnLevel){
		//Use Listsearch for containers
		
		if (moSearchMethod.equals(eSearchMethod.LISTSEARCH.name())){ 
			return listSearchMesh(poReturnType, poDataStructureUnknown, prThreshold, pnLevel);
		}
		
		//FIXME SSCH: workaround (graphsearch currently only available on entity-level, not image-level)
		if (moSearchMethod.equals(eSearchMethod.GRAPHSEARCH.name())){ 
			return listSearchMesh(poReturnType, poDataStructureUnknown, prThreshold, pnLevel);
		}
		
		throw new IllegalArgumentException(" defined search method unknown " + moSearchMethod);
	}

	
	public abstract ArrayList<clsPair<Double, clsDataStructurePA>> listSearchMesh(int poReturnType, clsDataStructurePA poDataStructureUnknown, double prThreshold, int pnLevel);
}
