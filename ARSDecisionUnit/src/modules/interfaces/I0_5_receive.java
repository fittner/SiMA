/**
 * I0_5_receive.java: DecisionUnits - pa.interfaces.receive._v38
 * 
 * @author deutsch
 * 03.03.2011, 15:30:03
 */
package modules.interfaces;


import communication.datatypes.clsDataContainer;




/**
 * The body sensors (see above) provide their information to F12 via this interface.
 * 
 * @author deutsch
 * 03.03.2011, 15:30:03
 * 
 */
public interface I0_5_receive  {
	public void receive_I0_5(clsDataContainer poData);
}
