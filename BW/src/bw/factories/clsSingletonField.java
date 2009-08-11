/**
 * clsSingletonField.java: BW - bw.factories
 * 
 * @author deutsch
 * 11.08.2009, 11:36:28
 */
package bw.factories;

import sim.field.continuous.Continuous2D;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 11:36:28
 * 
 */
public class clsSingletonField {
    private static clsSingletonField instance = null;
    private Continuous2D moField = null;    
    private clsSingletonField() {}    
    private static clsSingletonField getInstance() {
       if (instance == null) {
            instance = new clsSingletonField();
       }
       return instance;
    }
	public static void setField(Continuous2D poField) {
		clsSingletonField.getInstance().moField = poField;
	}    
	public static Continuous2D getField() {
		return clsSingletonField.getInstance().moField;
	}    
}
