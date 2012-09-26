/**
 * CHANGELOG
 *
 * 18.09.2012 muchitsch - File created
 *
 */
package bw.factories;

import sim.field.grid.DoubleGrid2D;

/**
 * this static is used for heat map data holding 
 * 
 * @author muchitsch
 * 18.09.2012, 10:53:04
 * 
 */
public class clsSingletonGridField {

	private static clsSingletonGridField instance = null;
    private DoubleGrid2D moGridField = null;    
    
    private clsSingletonGridField() {}  
    
    private static clsSingletonGridField getInstance() {
       if (instance == null) {
            instance = new clsSingletonGridField();
       }
       return instance;
    }
    
	public static void setField(DoubleGrid2D poField) {
		clsSingletonGridField.getInstance().moGridField = poField;
	} 
	
	public static DoubleGrid2D getField() {
		return clsSingletonGridField.getInstance().moGridField;
	}    
}