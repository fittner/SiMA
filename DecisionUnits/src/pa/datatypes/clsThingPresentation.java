/**
 * clsThingPresentation.java: DecisionUnits - pa.datatypes
 * 
 * @author langr
 * 11.08.2009, 11:16:10
 */
package pa.datatypes;

import java.util.HashMap;
import java.util.Map;

import enums.pa.eContext;

import pa.interfaces.itfPrimaryProcessComparabelTP;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 11.08.2009, 11:16:10
 * 
 */
public class clsThingPresentation extends clsPsychicRepresentative implements itfPrimaryProcessComparabelTP, Cloneable {

	public HashMap<eContext, clsDriveContentCathegories> moDriveContentCathegory = new HashMap<eContext, clsDriveContentCathegories>();

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
	
	@Override
	public Object clone() throws CloneNotSupportedException {
        try {
        	clsThingPresentation oClone = (clsThingPresentation)super.clone();
        	
        	oClone.moDriveContentCathegory = new HashMap<eContext, clsDriveContentCathegories>();   	
        	for (Map.Entry<eContext, clsDriveContentCathegories> oValue:moDriveContentCathegory.entrySet()) {
        		oClone.moDriveContentCathegory.put(oValue.getKey(), oValue.getValue());
        	}
        	
        	return oClone;
        } catch (CloneNotSupportedException e) {
           return e;
        }
	}		
	
	@Override
	public String toString() {
		String oResult = "::TP:: LIC: ";
	
		for (Map.Entry<eContext, clsDriveContentCathegories> entry:moDriveContentCathegory.entrySet()) {
			oResult += entry.getKey().name()+" ("+entry.getValue()+") / ";
		}
		
		if (oResult.length() > 4) {
			oResult = oResult.substring(0, oResult.length()-3);
		}
		
		return oResult;
	}
	
	public String toStringGraphDisplay() {
		return "";
	}

}