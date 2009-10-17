/**
 * clsPsychicRepresentative.java: DecisionUnits - pa.datatypes
 * 
 * @author langr
 * 11.08.2009, 12:54:29
 */
package pa.datatypes;

import java.util.HashMap;
import java.util.Map;

import enums.pa.eContext;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 11.08.2009, 12:54:29
 * 
 */
public class clsPsychicRepresentative implements Cloneable {

	public HashMap<eContext, clsDriveContentCathegories> meDriveContentCathegory = new HashMap<eContext, clsDriveContentCathegories>();
	
	@Override
	public Object clone() throws CloneNotSupportedException {
        try {
        	clsPsychicRepresentative oClone = (clsPsychicRepresentative)super.clone();
        	
        	oClone.meDriveContentCathegory = new HashMap<eContext, clsDriveContentCathegories>();   	
        	for (Map.Entry<eContext, clsDriveContentCathegories> oValue:meDriveContentCathegory.entrySet()) {
        		oClone.meDriveContentCathegory.put(oValue.getKey(), oValue.getValue());
        	}
        	
        	return oClone;
        } catch (CloneNotSupportedException e) {
           return e;
        }
	}	
	
}
