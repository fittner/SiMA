/**
 * clsPrimaryInformation.java: DecisionUnits - pa.datatypes
 * 
 * @author langr
 * 11.08.2009, 11:48:23
 */
package pa.datatypes;

import pa.interfaces.itfPrimaryProcessComparabelTP;
import pa.interfaces.itfPrimaryProcessComparable;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 11.08.2009, 11:48:23
 * 
 */
public class clsPrimaryInformation extends clsPsychicRepresentative implements itfPrimaryProcessComparable, itfPrimaryProcessComparabelTP, Cloneable {

	public clsThingPresentation moTP;
	public clsAffect moAffect;
	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 12:15:46
	 * 
	 * @see pa.interfaces.itfPrimaryProcessComparable#compareTo(pa.datatypes.clsPrimaryInformation)
	 */
	@Override
	public int compareTo(clsPrimaryInformation poPrimaryInformation) {
		// TODO (langr) - Auto-generated method stub
		return 0;
	}
	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 12:15:46
	 * 
	 * @see pa.interfaces.itfPrimaryProcessComparabelTP#compareTo(pa.datatypes.clsThingPresentation)
	 */
	@Override
	public int compareTo(clsThingPresentation poPrimaryInformation) {
		// TODO (langr) - Auto-generated method stub
		return 0;
	}

	@Override
	public Object clone() throws CloneNotSupportedException{
        try {
        	clsPrimaryInformation oClone = (clsPrimaryInformation)super.clone();
        	oClone.moTP = (clsThingPresentation)moTP.clone();
        	oClone.moAffect = (clsAffect)moAffect.clone();        	
        	return oClone;
        } catch (CloneNotSupportedException e) {
            return e;
        }
		
	}
	
}
