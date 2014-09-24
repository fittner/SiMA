/**
 * I0_3_receive.java: DecisionUnits - pa.interfaces.receive._v38
 * 
 * @author deutsch
 * 03.03.2011, 15:28:47
 */
package modules.interfaces;


import communication.datatypes.clsDataContainer;



/**
 * This input connected to Module F1 represents the outputs of the sensors which measure the homeostasis.
 * 
 * @author deutsch
 * 03.03.2011, 15:28:47
 * 
 */
public interface I0_3_receive  {
	public void receive_I0_3(clsDataContainer poData);
}