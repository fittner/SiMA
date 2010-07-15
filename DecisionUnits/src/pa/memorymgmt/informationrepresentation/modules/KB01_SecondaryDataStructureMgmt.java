/**
 * clsSecondaryDataStructureMgmt.java: DecisionUnits - pa.informationrepresentation
 * 
 * @author zeilinger
 * 19.05.2010, 07:47:02
 */
package pa.memorymgmt.informationrepresentation.modules;

import java.util.ArrayList;
import java.util.Collection;

import pa.memorymgmt.datatypes.clsAssociation;
import pa.memorymgmt.datatypes.clsDataStructureContainer;
import pa.memorymgmt.datatypes.clsDataStructurePA;
import pa.memorymgmt.datatypes.clsSecondaryDataStructure;
import pa.memorymgmt.datatypes.clsSecondaryInformation;
import pa.memorymgmt.informationrepresentation.clsSearchSpaceHandler;
import pa.tools.clsPair;

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
	public ArrayList<clsDataStructureContainer> listSearch(int poReturnType,
			clsDataStructureContainer poSearchPatternContainer) {
		
			ArrayList<clsDataStructureContainer> oDataStructureContainerList = new ArrayList<clsDataStructureContainer>(); 
			ArrayList<clsPair<Double,clsDataStructurePA>> oMatchedDataStructures = compareELements(poSearchPatternContainer); 
			
			for(clsPair<Double, clsDataStructurePA> oPatternElement : oMatchedDataStructures){
				oDataStructureContainerList.add(getDataContainer(poReturnType, (clsSecondaryDataStructure)oPatternElement.b));
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
		
			clsSecondaryInformation oDataStructureContainer = new clsSecondaryInformation();
			oDataStructureContainer.moSecondaryDataStructure = poDataStructure; 
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
	private Collection<? extends clsAssociation> readOutSearchSpace(int poReturnType, clsSecondaryDataStructure poDataStructure) {
		
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
		clsDataStructurePA oDataStructureSearchPattern = ((clsSecondaryInformation)poSearchPatternContainer).moSecondaryDataStructure;
				
		return clsDataStructureComparison.compareDataStructures(oDataStructureSearchPattern, moSearchSpaceHandler.returnSearchSpace());
	}
}
