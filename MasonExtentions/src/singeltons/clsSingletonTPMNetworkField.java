/**
 * CHANGELOG
 *
 * 27.09.2012 muchitsch - File created
 *
 */
package singeltons;

import sim.field.network.Network;

/**
 * DOCUMENT (muchitsch) - insert description 
 * 
 * @author muchitsch
 * 27.09.2012, 11:52:50
 * 
 */
public class clsSingletonTPMNetworkField {
	
	private static clsSingletonTPMNetworkField instance = null;
    private Network moNetworkField = null;    
    
    private clsSingletonTPMNetworkField() {}  
    
    private static clsSingletonTPMNetworkField getInstance() {
       if (instance == null) {
            instance = new clsSingletonTPMNetworkField();
       }
       return instance;
    }
    
	public static void setField(Network poField) {
		clsSingletonTPMNetworkField.getInstance().moNetworkField = poField;
	} 
	
	public static Network getField() {
		return clsSingletonTPMNetworkField.getInstance().moNetworkField;
	}    

}
