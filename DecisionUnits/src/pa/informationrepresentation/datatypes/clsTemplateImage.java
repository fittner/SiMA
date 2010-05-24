/**
 * clsTemplateImage.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:49:45
 */
package pa.informationrepresentation.datatypes;

import java.util.ArrayList;

import pa.tools.clsPair;


/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:49:45
 * 
 */
public class clsTemplateImage extends clsPhysicalStructureComposition{
	protected ArrayList<clsAssociationTime> moTimeAssociations;
	
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 24.05.2010, 12:41:23
	 *
	 */
	public clsTemplateImage(clsPair<String, ArrayList<clsWordPresentation>> poAssociatedWordPresentations,
							clsPair<String,ArrayList<clsDriveSource>> poAssociatedDriveSources,
							clsPair<String,ArrayList<clsPhysicalStructureComposition>> poAssociatedTemporalStructures) {
		super(poAssociatedWordPresentations, poAssociatedDriveSources); 
		moTimeAssociations = new ArrayList<clsAssociationTime>(); 
		
		applyAssociations(poAssociatedTemporalStructures); 
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 24.05.2010, 14:31:50
	 *
	 * @param poAssociatedTemporalStructures
	 */
	private void applyAssociations(
			clsPair<String, ArrayList<clsPhysicalStructureComposition>> poAssociatedTemporalStructures) {
		// TODO (zeilinger) - Auto-generated method stub
		
	}
	
}
