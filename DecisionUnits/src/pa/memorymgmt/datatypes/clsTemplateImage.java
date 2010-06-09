/**
 * clsTemplateImage.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:49:45
 */
package pa.memorymgmt.datatypes;

import java.util.ArrayList;
import java.util.List;

import pa.tools.clsPair;


/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:49:45
 * 
 */
public class clsTemplateImage extends clsPhysicalStructureComposition{
	protected List<clsAssociationTime> moTimeAssociations;
	
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 24.05.2010, 12:41:23
	 *
	 */
	public clsTemplateImage(clsPair<String,List<clsWordPresentation>> poAssociatedWordPresentations,
							clsPair<String,List<clsDriveSource>> poAssociatedDriveSources,
							clsPair<String,List<clsPhysicalStructureComposition>> poAssociatedTemporalStructures) {
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
			clsPair<String, List<clsPhysicalStructureComposition>> poAssociatedTemporalStructures) {
		// TODO (zeilinger) - Auto-generated method stub
		
	}
	
}
