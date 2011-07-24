/**
 * clsSecondaryDataStructureMgmt.java: DecisionUnits - pa.informationrepresentation
 * 
 * @author zeilinger
 * 19.05.2010, 07:47:02
 */
package pa._v38.memorymgmt.informationrepresentation.modules;

import java.util.ArrayList;

import pa._v38.tools.clsPair;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa._v38.memorymgmt.informationrepresentation.clsSearchSpaceHandler;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 19.05.2010, 07:47:02
 * 
 */
public class KB01_SecondaryDataStructureMgmt extends clsInformationRepresentationModuleBase {

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
	public ArrayList<clsPair<Double,clsDataStructureContainer>> listSearch(int poReturnType,
			clsDataStructurePA poDataStructureUnknown) {
		
			ArrayList<clsPair<Double,clsDataStructureContainer>> oDataStructureContainerList = new ArrayList<clsPair<Double,clsDataStructureContainer>>(); 
			ArrayList<clsPair<Double,clsDataStructurePA>> oMatchedDataStructures = compareElements(poDataStructureUnknown); 
			
			for(clsPair<Double, clsDataStructurePA> oPatternElement : oMatchedDataStructures){
				clsDataStructureContainer oDataStructureContainer = getDataContainer(poReturnType, (clsSecondaryDataStructure)oPatternElement.b);
				oDataStructureContainerList.add(new clsPair<Double, clsDataStructureContainer>(oPatternElement.a, oDataStructureContainer));
			}
			return oDataStructureContainerList;
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 12.07.2010, 12:58:02
	 *
	 * @param poReturnType
	 * @param oPatternElement
	 * @return
	 */
	private clsDataStructureContainer getDataContainer(int poReturnType, clsSecondaryDataStructure poDataStructure) {
		
			clsSecondaryDataStructureContainer oDataStructureContainer = new clsSecondaryDataStructureContainer(null, null);
			oDataStructureContainer.setMoDataStructure(poDataStructure); 
			oDataStructureContainer.setMoAssociatedDataStructures(readOutSearchSpace(poReturnType, poDataStructure)); 
			
		return oDataStructureContainer;
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 12.07.2010, 13:52:24
	 *
	 * @param poReturnType
	 * @param poDataStructure
	 * @return
	 */
	private ArrayList<clsAssociation> readOutSearchSpace(int poReturnType, clsSecondaryDataStructure poDataStructure) {
		return moSearchSpaceHandler.readOutSearchSpace(poReturnType, poDataStructure);
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
	private ArrayList<clsPair<Double,clsDataStructurePA>> compareElements(clsDataStructurePA poDataStructureUnknown) {
		return clsDataStructureComparison.compareDataStructures(poDataStructureUnknown, moSearchSpaceHandler.returnSearchSpace());
	}
	
	@Override
	public ArrayList<clsPair<Double, clsDataStructureContainer>> listSearchContainer(int poReturnType,clsDataStructureContainer poDataStructureUnknown, double prThreshold) {
		//TODO: AW Add something here, or else the function will not be used
		return null;
	}
	
	
}
