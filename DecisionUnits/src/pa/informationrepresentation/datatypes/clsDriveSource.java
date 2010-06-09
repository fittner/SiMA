/**
 * clsDriveSource.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:50:12
 */
package pa.informationrepresentation.datatypes;

import java.util.ArrayList;
import java.util.List;

import pa.informationrepresentation.enums.eHomeostaticSources;
import pa.tools.clsPair;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:50:12
 * 
 */
public class clsDriveSource extends clsHomeostaticRepresentation{
	protected List<clsAssociationWordPresentation> moWordPresentationAssociations; 
	protected List<clsAssociationDriveMesh> moDriveMeshAssociations; 
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 24.05.2010, 12:58:02
	 *
	 * @param poAttributeAssociations
	 */
	public clsDriveSource(
			eHomeostaticSources poHomeoStaticSource, 
			clsPair<String,List<clsWordPresentation>> poAssociatedWordPresentations,
			clsPair<String,List<clsDataStructurePA>> poAssociatedDataStructures) {
		super(poHomeoStaticSource);
		moWordPresentationAssociations = new ArrayList<clsAssociationWordPresentation>(); 
		moDriveMeshAssociations = new ArrayList<clsAssociationDriveMesh>(); 
		
		applyAssociations(poAssociatedWordPresentations, poAssociatedDataStructures); 
	}
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 24.05.2010, 14:47:42
	 *
	 * @param poAssociatedWordPresentations
	 * @param poAssociatedDataStructures
	 */
	private void applyAssociations(
			clsPair<String, List<clsWordPresentation>> poAssociatedWordPresentations,
			clsPair<String, List<clsDataStructurePA>> poAssociatedDataStructures) {
		// TODO (zeilinger) - Auto-generated method stub
		/*Be aware that poAssociatedDataStrucutres must not contain SecondaryDataStructures*/
		
	}
	
}
