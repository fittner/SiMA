/**
 * KB03_ExternalPerceptionMgmt.java: DecisionUnits - pa.informationrepresentation
 * 
 * @author zeilinger
 * 19.05.2010, 07:50:02
 */
package pa._v38.memorymgmt.informationrepresentation.modules;

import java.util.ArrayList;

import pa._v38.tools.clsPair;
import pa._v38.tools.clsTripple;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsHomeostaticRepresentation;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v38.memorymgmt.informationrepresentation.clsSearchSpaceHandler;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 19.05.2010, 07:50:02
 * 
 */
public class KB03_ExternalPerceptionMgmt extends clsInformationRepresentationModuleBase{

	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 23.05.2010, 22:59:07
	 *
	 * @param poInformationRepresentationModulesContainer
	 * @param poSearchSpaceHandler 
	 */
	public KB03_ExternalPerceptionMgmt(
			clsInformationRepresentationModuleContainer poInformationRepresentationModulesContainer, 
											clsSearchSpaceHandler poSearchSpaceHandler, String poSearchMethod) {
		super(poInformationRepresentationModulesContainer, poSearchSpaceHandler, poSearchMethod);
		// TODO (zeilinger) - Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 31.05.2010, 16:08:29
	 * 
	 * @see pa.informationrepresentation.ARSi10.modules.clsInformationRepresentationModuleBase#listSearch(java.lang.String, pa.informationrepresentation.datatypes.clsDataStructureContainer)
	 */
	@Override
	public ArrayList<clsPair<Double,clsDataStructureContainer>> listSearch(int poReturnType,
			clsDataStructurePA poDataStructureUnknown) {
		ArrayList<clsPair<Double,clsDataStructureContainer>> oDataStructureContainerList = new ArrayList<clsPair<Double,clsDataStructureContainer>>(); 
		ArrayList<clsPair<Double,clsDataStructurePA>> oMatchedDataStructures = compareElements(poDataStructureUnknown); 
	
		for(clsPair<Double, clsDataStructurePA> oPatternElement : oMatchedDataStructures){
			clsDataStructureContainer oDataStructureContainer = getDataContainer(poReturnType, (clsHomeostaticRepresentation)oPatternElement.b);
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
	private clsDataStructureContainer getDataContainer(int poReturnType, clsHomeostaticRepresentation poDataStructure) {
		
		clsPrimaryDataStructureContainer oDataStructureContainer = new clsPrimaryDataStructureContainer(null, null);
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
	private ArrayList<clsAssociation> readOutSearchSpace(int poReturnType, clsHomeostaticRepresentation poDataStructure) {
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
	public ArrayList<clsTripple<Double, clsDataStructureContainer, ArrayList<clsAssociationDriveMesh>>> listSearch(int poReturnType,clsDataStructureContainer poDataStructureUnknown) {
		//TODO: AW Add something here, or else the function will not be used
		return null;
	}
}
