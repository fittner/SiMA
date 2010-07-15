/**
 * KB03_ExternalPerceptionMgmt.java: DecisionUnits - pa.informationrepresentation
 * 
 * @author zeilinger
 * 19.05.2010, 07:50:02
 */
package pa.memorymgmt.informationrepresentation.modules;

import java.util.ArrayList;
import java.util.Collection;

import pa.memorymgmt.datatypes.clsAssociation;
import pa.memorymgmt.datatypes.clsDataStructureContainer;
import pa.memorymgmt.datatypes.clsDataStructurePA;
import pa.memorymgmt.datatypes.clsHomeostaticRepresentation;
import pa.memorymgmt.datatypes.clsPrimaryInformation;
import pa.memorymgmt.informationrepresentation.clsSearchSpaceHandler;
import pa.tools.clsPair;

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
	public ArrayList<clsDataStructureContainer> listSearch(int poReturnType, clsDataStructureContainer poSearchPatternContainer) {
		ArrayList<clsDataStructureContainer> oDataStructureContainerList = new ArrayList<clsDataStructureContainer>(); 
		ArrayList<clsPair<Double,clsDataStructurePA>> oMatchedDataStructures = compareELements(poSearchPatternContainer); 
		
		for(clsPair<Double, clsDataStructurePA> oPatternElement : oMatchedDataStructures){
			oDataStructureContainerList.add(getDataContainer(poReturnType, (clsHomeostaticRepresentation)oPatternElement.b));
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
		
			clsPrimaryInformation oDataStructureContainer = new clsPrimaryInformation();
			oDataStructureContainer.moDataStructure = poDataStructure; 
			oDataStructureContainer.moAssociatedDataStructures.addAll(readOutSearchSpace(poReturnType, poDataStructure)); 
			
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
	private Collection<? extends clsAssociation> readOutSearchSpace(int poReturnType, clsHomeostaticRepresentation poDataStructure) {
		
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
	private ArrayList<clsPair<Double,clsDataStructurePA>> compareELements(clsDataStructureContainer poSearchPatternContainer) {
		clsDataStructurePA oDataStructureSearchPattern = ((clsPrimaryInformation)poSearchPatternContainer).moDataStructure;
				
		return clsDataStructureComparison.compareDataStructures(oDataStructureSearchPattern, moSearchSpaceHandler.returnSearchSpace());
	}
}
