/**
 * clsPrimaryInformation.java: DecisionUnits - pa.datatypes
 * 
 * @author langr
 * 11.08.2009, 11:48:23
 */
package pa._v19.datatypes;

import pa._v19.interfaces.itfPrimaryProcessComparabelTP;
import pa._v19.interfaces.itfPrimaryProcessComparable;

/**
 * 
 * @author langr
 * 11.08.2009, 11:48:23
 * 
 */
@Deprecated
public class clsPrimaryInformation extends clsPsychicRepresentative implements itfPrimaryProcessComparable, itfPrimaryProcessComparabelTP, Cloneable {

	public clsThingPresentationSingle moTP;
	public clsAffect moAffect;

	public clsPrimaryInformation() {

		moTP = new clsThingPresentationSingle();
		moAffect = new clsAffectTension();

	}

	
	public clsPrimaryInformation(clsThingPresentationSingle poSingle) {
		moTP = poSingle;
		moAffect = null;
	}
	public clsPrimaryInformation(clsThingPresentationSingle poSingle, clsAffect poAffect) {
		moTP = poSingle;
		moAffect = poAffect;
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 12:15:46
	 * 
	 * @see pa.interfaces.itfPrimaryProcessComparable#compareTo(pa.datatypes.clsPrimaryInformation)
	 */
	@Override
	public int compareTo(clsPrimaryInformation poPrimaryInformation) {
		
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
		
		return 0;
	}

	@Override
	public Object clone() throws CloneNotSupportedException{
        try {
        	clsPrimaryInformation oClone = (clsPrimaryInformation)super.clone();
        	if(moTP!=null) {
        		oClone.moTP = (clsThingPresentationSingle)moTP.clone();
        	}
        	if(moAffect!=null) {
        		oClone.moAffect = (clsAffect)moAffect.clone();	
        	}
        	return oClone;
        } catch (CloneNotSupportedException e) {
            return e;
        }
		
	}
	/**
	 *
	 * @author langr
	 * 16.10.2009, 19:35:07
	 *
	 * @return
	 */
	public String toGraphDisplayString() {
		String oRetVal = "";
		
		if ( moTP != null ) {
			oRetVal = moTP.toStringGraphDisplay();
		}
		return oRetVal;
	}
	
	@Override
	public String toString() {
		String oResult = "";
		
		oResult = "PIs :: "+moTP+" | "+moAffect;
		
		return oResult;
	}	
}
