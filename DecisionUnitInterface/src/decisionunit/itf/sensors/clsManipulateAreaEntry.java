/**
 * clsManipulateAreaEntry.java: DecisionUnitInterface - decisionunit.itf.sensors
 * 
 * @author deutsch
 * 20.10.2009, 15:16:00
 */
package decisionunit.itf.sensors;

import java.lang.reflect.Field;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 20.10.2009, 15:16:00
 * 
 */
public class clsManipulateAreaEntry extends clsVisionEntry {
	public clsManipulateAreaEntry() {
		super();
	}
	
	public clsManipulateAreaEntry(clsVisionEntry poEntry) {
		super();
		
		Field[] oFields = clsVisionEntry.class.getDeclaredFields();
		
		for (Field oF:oFields) {
			Object value;
			try {
				value = oF.get(poEntry);
				oF.set(this, value);
			} catch (IllegalArgumentException e) {
				// TODO (deutsch) - Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO (deutsch) - Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
