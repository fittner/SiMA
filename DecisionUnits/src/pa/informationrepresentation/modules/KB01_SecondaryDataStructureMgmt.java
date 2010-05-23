/**
 * clsSecondaryDataStructureMgmt.java: DecisionUnits - pa.informationrepresentation
 * 
 * @author zeilinger
 * 19.05.2010, 07:47:02
 */
package pa.informationrepresentation.modules;

import java.util.ArrayList;

import pa.informationrepresentation.datatypes.clsDataStructureComposition;
import pa.informationrepresentation.datatypes.clsDatastructure;

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
	 */
	public KB01_SecondaryDataStructureMgmt(
			clsInformationRepresentationModuleContainer poInformationRepresentationModulesContainer) {
		super(poInformationRepresentationModulesContainer);
		// TODO (zeilinger) - Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 23.05.2010, 23:33:09
	 * 
	 * @see pa.informationrepresentation.modules.clsInformationRepresentationModuleBase#searchDataStructure(java.util.ArrayList)
	 */
	@Override
	public ArrayList<clsDataStructureComposition> searchDataStructure(
			ArrayList<clsDatastructure> poSearchPattern) {
		// TODO (zeilinger) - Auto-generated method stub
		return null;
	}

}
