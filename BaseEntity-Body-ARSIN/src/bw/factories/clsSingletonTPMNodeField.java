/**
 * CHANGELOG
 *
 * 27.09.2012 muchitsch - File created
 *
 */
package bw.factories;

import sim.field.continuous.Continuous2D;

/**
 * DOCUMENT (muchitsch) - insert description 
 * 
 * @author muchitsch
 * 27.09.2012, 15:08:44
 * 
 */
public class clsSingletonTPMNodeField {

	private static clsSingletonTPMNodeField instance = null;
    private Continuous2D moNodeField = null;    
    
    private clsSingletonTPMNodeField() {}  
    
    private static clsSingletonTPMNodeField getInstance() {
       if (instance == null) {
            instance = new clsSingletonTPMNodeField();
       }
       return instance;
    }
    
	public static void setField(Continuous2D poField) {
		clsSingletonTPMNodeField.getInstance().moNodeField = poField;
	} 
	
	public static Continuous2D getField() {
		return clsSingletonTPMNodeField.getInstance().moNodeField;
	}    
	
}
