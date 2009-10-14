/**
 * clsPsychicRepresentative.java: DecisionUnits - pa.datatypes
 * 
 * @author langr
 * 11.08.2009, 12:54:29
 */
package pa.datatypes;

import java.util.ArrayList;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 11.08.2009, 12:54:29
 * 
 */
public class clsPsychicRepresentative implements Cloneable {

	public ArrayList<Integer> meDriveContentCathegory = new ArrayList<Integer>(); //oral, anal, phallic or genital --> has to be predefined
	
	@Override
	public Object clone() throws CloneNotSupportedException {
        try {
        	clsPsychicRepresentative oClone = (clsPsychicRepresentative)super.clone();
        	
        	oClone.meDriveContentCathegory = new ArrayList<Integer>();   	
        	for (Integer oValue:meDriveContentCathegory) {
        		oClone.meDriveContentCathegory.add(oValue);
        	}
        	
        	return oClone;
        } catch (CloneNotSupportedException e) {
           return e;
        }
	}	
	
}
