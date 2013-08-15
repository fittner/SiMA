
package du.itf.sensors;


import java.lang.reflect.Field;
import du.enums.eOdor;
import du.enums.eSaliency;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 20.10.2009, 15:16:00
 * 
 */
public class clsOlfactoricEntry extends clsVisionEntry {
	
	protected eSaliency moIntensity = eSaliency.UNDEFINED;
	protected eOdor moOdor = eOdor.UNDEFINED; 
	
	public clsOlfactoricEntry() {
		super();
	}
	
	public clsOlfactoricEntry(clsVisionEntry poEntry) {
		super();
		
		//ATTENTION: getDeclaredFields ONLY returns the member of the given class - NOT THE SUPERCLASSES!!!!
		Field[] oFields1 = clsVisionEntry.class.getDeclaredFields();
		Field[] oFields2 = clsSensorRingSegmentEntry.class.getDeclaredFields();
		
		Field[] oFields = new Field[oFields1.length+oFields2.length];
		for(int i=0; i<oFields1.length; i++) { oFields[i]=oFields1[i]; }
		for(int i=0; i<oFields2.length; i++) { oFields[i+oFields1.length]=oFields2[i]; }
		
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
	
	public eOdor getOdor() {
		return moOdor;
	}
	public void setOdor(eOdor poOdor) {
		moOdor = poOdor;
	}
	
	public eSaliency getIntensity() {
		return moIntensity;
	}
	public void setIntensity(eSaliency poIntensity) {
		moIntensity = poIntensity;
	}
}
