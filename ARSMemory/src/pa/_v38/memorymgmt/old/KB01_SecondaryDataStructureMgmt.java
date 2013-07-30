/**
 * clsSecondaryDataStructureMgmt.java: DecisionUnits - pa.informationrepresentation
 * 
 * @author zeilinger
 * 19.05.2010, 07:47:02
 */
package pa._v38.memorymgmt.old;

import java.util.ArrayList;

import pa._v38.tools.clsPair;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa._v38.memorymgmt.framessearchspace.clsSearchSpaceHandler;
import pa._v38.memorymgmt.framessearchspace.tools.clsDataStructureComparisonTools;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 19.05.2010, 07:47:02
 * 
 * @deprecated
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
		
			clsSecondaryDataStructureContainer oDataStructureContainer = new clsSecondaryDataStructureContainer("", "");
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
		return clsDataStructureComparisonTools.compareDataStructures(poDataStructureUnknown, moSearchSpaceHandler.returnSearchSpace());
	}
	
	/* (non-Javadoc)
	 *
	 * @since 05.12.2011 16:35:18
	 * 
	 * @see pa._v38.memorymgmt.informationrepresentation.modules.clsInformationRepresentationModuleBase#listSearchContainer(int, pa._v38.memorymgmt.datatypes.clsDataStructureContainer, double)
	 */
	@Override
	public ArrayList<clsPair<Double, clsDataStructureContainer>> listSearchContainer(int poReturnType,clsDataStructureContainer poDataStructureUnknown, double prThreshold) {
		//TODO: AW Add something here, or else the function will not be used
		return null;
	}

	/* (non-Javadoc)
	 *
	 * @since 05.12.2011 16:34:58
	 * 
	 * @see pa._v38.memorymgmt.informationrepresentation.modules.clsInformationRepresentationModuleBase#listSearchMesh(int, pa._v38.memorymgmt.datatypes.clsDataStructurePA, double, int)
	 */
	@Override
	public ArrayList<clsPair<Double, clsDataStructurePA>> listSearchMesh(int poReturnType, clsDataStructurePA poDataStructureUnknown, double prThreshold, int pnLevel) {
		// TODO (wendt) - Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 *
	 * @since Aug 9, 2012 12:57:10 PM
	 * 
	 * @see pa._v38.memorymgmt.informationrepresentation.modules.clsInformationRepresentationModuleBase#graphSearch(int, pa._v38.memorymgmt.datatypes.clsDataStructurePA)
	 */
	@Override
	public ArrayList<clsPair<Double, clsDataStructureContainer>> associativeSearch(
			int poReturnType, clsDataStructurePA poDataStructureUnknown) {
		// TODO (schaat) - Auto-generated method stub
		return null;
	}
	
	
}
