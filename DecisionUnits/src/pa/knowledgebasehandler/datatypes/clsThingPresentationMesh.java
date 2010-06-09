/**
 * clsThingPresentationMesh.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 24.05.2010, 12:51:07
 */
package pa.knowledgebasehandler.datatypes;

import java.util.ArrayList;
import java.util.List;

import pa.tools.clsPair;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 24.05.2010, 12:51:07
 * 
 */
public class clsThingPresentationMesh extends clsPhysicalStructureComposition{
	protected List <clsAssociationAttribute> moAttributeAssociations; 
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 24.05.2010, 12:51:22
	 *
	 * @param poWordPresentationAssociation
	 * @param poDriveMeshAssociation
	 */
	public clsThingPresentationMesh(
			clsPair<String,List<clsWordPresentation>> poAssociatedWordPresentations,
			clsPair<String,List<clsDriveSource>> poAssociatedDriveSources,
			clsPair<String,List<clsPhysicalRepresentation>> poAssociatedPhysicalRepresentations) {
		super(poAssociatedWordPresentations, poAssociatedDriveSources);
		
		moAttributeAssociations = new ArrayList<clsAssociationAttribute>(); 
		applyAssociations(poAssociatedPhysicalRepresentations); 
	}
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 24.05.2010, 14:29:44
	 *
	 * @param poAssociatedPhysicalRepresentations
	 */
	private void applyAssociations(
			clsPair<String, List<clsPhysicalRepresentation>> poAssociatedPhysicalRepresentations) {
		// TODO (zeilinger) - Auto-generated method stub
		
	}

}
