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
	
	@Override
	public Object clone() throws CloneNotSupportedException {
        try {
        	clsThingPresentation oClone = (clsThingPresentation)super.clone();
        	
        	if (moLifeInstinctCathegories != null) { 
        		oClone.moLifeInstinctCathegories = new HashMap<eContext, clsLifeInstinctCathegories>();   	
        		for (Map.Entry<eContext, clsLifeInstinctCathegories> oValue:moLifeInstinctCathegories.entrySet()) {
        			oClone.moLifeInstinctCathegories.put(oValue.getKey(), (clsLifeInstinctCathegories) (oValue.getValue()).clone());
        		}
        	}
        	
        	return oClone;
        } catch (CloneNotSupportedException e) {
           return e;
        }
	}		

}