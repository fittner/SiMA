/**
 * clsDriveObject.java: DecisionUnits - pa.datatypes
 * 
 * @author langr
 * 28.09.2009, 17:46:01
 */
package pa._v19.datatypes;

import du.enums.eEntityType;
import du.enums.pa.eContext;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 28.09.2009, 17:46:01
 * 
 */
@Deprecated
public class clsDriveObject extends clsThingPresentationSingle implements Cloneable {

	public eEntityType meType;
	public eContext meContext;

	@Override
	public Object clone() throws CloneNotSupportedException {
        try {
        	clsDriveObject oClone = (clsDriveObject)super.clone();

        	return oClone;
        } catch (CloneNotSupportedException e) {
           return e;
        }
	}	
}
