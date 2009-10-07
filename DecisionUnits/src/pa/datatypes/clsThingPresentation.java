/**
 * clsThingPresentation.java: DecisionUnits - pa.datatypes
 * 
 * @author langr
 * 11.08.2009, 11:16:10
 */
package pa.datatypes;

import java.util.HashMap;

import enums.pa.eContext;

import pa.interfaces.itfPrimaryProcessComparabelTP;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 11.08.2009, 11:16:10
 * 
 */
public class clsThingPresentation extends clsPsychicRepresentative implements itfPrimaryProcessComparabelTP {

	public HashMap<eContext, clsLifeInstinctCathegories> moLifeInstinctCathegories = null;
	
	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 12:21:03
	 * 
	 * @see pa.interfaces.itfPrimaryProcessComparabelTP#compareTo(pa.datatypes.clsThingPresentation)
	 */
	@Override
	public int compareTo(clsThingPresentation poPrimaryInformation) {
		// TODO (langr) - Auto-generated method stub
		return 0;
	}

}