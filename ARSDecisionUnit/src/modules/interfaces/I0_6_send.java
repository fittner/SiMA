/**
 * I0_6_send.java: DecisionUnits - pa.interfaces.send._v38
 * 
 * @author deutsch
 * 03.03.2011, 15:30:30
 */
package modules.interfaces;


import communication.datatypes.clsDataContainer;





/**
 * Electrophysical signals are transmitted from F32 to the actuators.
 * 
 * @author deutsch
 * 03.03.2011, 15:30:30
 * 
 */
public interface I0_6_send  {
	public void send_I0_6(clsDataContainer poActionCommandList);
}