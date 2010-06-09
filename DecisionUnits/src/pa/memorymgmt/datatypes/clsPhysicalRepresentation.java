/**
 * clsPhysicalRepresentation.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:42:44
 */
package pa.memorymgmt.datatypes;

import java.util.ArrayList;
import java.util.List;

import pa.tools.clsPair;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:42:44
 * 
 */
public abstract class clsPhysicalRepresentation extends clsPrimaryDataStructure{
	protected List<clsAssociationWordPresentation> moWordPresentationAssociations; 
	
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 24.05.2010, 12:39:08
	 * @param poWordPresentationAssociation 
	 *
	 */
	public clsPhysicalRepresentation(clsPair<String, List<clsWordPresentation>> poAssociatedWordPresentations) {
		moWordPresentationAssociations = new ArrayList <clsAssociationWordPresentation>(); 
		applyAssociations(poAssociatedWordPresentations); 
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 24.05.2010, 14:40:45
	 *
	 * @param poAssociatedWordPresentations
	 */
	private void applyAssociations(
			clsPair<String, List<clsWordPresentation>> poAssociatedWordPresentations) {
		// TODO (zeilinger) - Auto-generated method stub
		
	}
}
