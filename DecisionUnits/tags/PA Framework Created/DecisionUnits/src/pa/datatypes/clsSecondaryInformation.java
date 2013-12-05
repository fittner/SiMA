/**
 * clsSecondaryInformation.java: DecisionUnits - pa.datatypes
 * 
 * @author langr
 * 11.08.2009, 11:48:35
 */
package pa.datatypes;

import pa.interfaces.itfSecondaryProcessComparable;
import pa.interfaces.itfSecondaryProcessComparableWP;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 11.08.2009, 11:48:35
 * 
 */
public class clsSecondaryInformation extends clsPsychicRepresentative implements itfSecondaryProcessComparable, itfSecondaryProcessComparableWP {

	public clsWordPresentation moWP;
	public clsThingPresentation moTP;
	public clsAffect moAffect;
	
	public clsSecondaryInformation(clsWordPresentation poWP, clsThingPresentation poTP, clsAffect poAffect) {
		moWP = poWP;
		moTP = poTP;
		moAffect = poAffect;
	}
	
	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 12:27:47
	 * 
	 * @see pa.interfaces.itfSecondaryProcessComparable#compareTo(pa.datatypes.clsSecondaryInformation)
	 */
	@Override
	public int compareTo(clsSecondaryInformation poSecondaryInformation) {
		// TODO (langr) - Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 12:44:36
	 * 
	 * @see pa.interfaces.itfSecondaryProcessComparableWP#compareTo(pa.datatypes.clsWordPresentation)
	 */
	@Override
	public int compareTo(clsWordPresentation poWordPresentation) {
		// TODO (langr) - Auto-generated method stub
		return 0;
	}

}
