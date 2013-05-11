/**
 * I2_20_send.java: DecisionUnits - pa.interfaces.send._v38
 * 
 * @author deutsch
 * 03.03.2011, 16:19:48
 */
package pa._v38.interfaces.modules;

import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;

/**
 * Similarly to I2.6, thing presentations are transported from F46 to F37. If quota of affects were retrieved from memory, these values are transported too.
 * 
 * @author deutsch
 * 03.03.2011, 16:19:48
 * 
 */
public interface I5_6_send {
	public void send_I5_6(clsThingPresentationMesh moPerceptionalMesh);
}
