/**
 * clsPhysicalStructureComposition.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:49:15
 */
package pa.informationrepresentation.datatypes;

import java.util.ArrayList;
import java.util.List;

import pa.tools.clsPair;


/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:49:15
 * 
 */
public abstract class clsPhysicalStructureComposition extends clsPhysicalRepresentation {
	protected List<clsAssociationDriveMesh> moDriveMeshAssociations; 
	
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 24.05.2010, 12:40:36
	 * @param object2 
	 * @param object 
	 *
	 */
	public clsPhysicalStructureComposition(clsPair<String, List<clsWordPresentation>> poAssociatedWordPresentations,
											clsPair<String, List<clsDriveSource>> poAssociatedDriveSources) {
		super(poAssociatedWordPresentations);
		moDriveMeshAssociations = new ArrayList<clsAssociationDriveMesh>();
		applyAssociations(poAssociatedDriveSources); 
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 24.05.2010, 14:35:20
	 *
	 * @param poAssociatedDriveSources
	 */
	private void applyAssociations(
			clsPair<String, List<clsDriveSource>> poAssociatedDriveSources) {
		// TODO (zeilinger) - Auto-generated method stub
		
	}
}
