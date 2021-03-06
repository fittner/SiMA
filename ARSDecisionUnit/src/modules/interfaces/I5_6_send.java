/**
 * I2_20_send.java: DecisionUnits - pa.interfaces.send._v38
 * 
 * @author deutsch
 * 03.03.2011, 16:19:48
 */
package modules.interfaces;

import base.datatypes.clsThingPresentationMesh;
import base.datatypes.clsWordPresentationMesh;

/**
 * Similarly to I2.6, thing presentations are transported from F46 to F37. If quota of affects were retrieved from memory, these values are transported too.
 * 
 * @author deutsch
 * 03.03.2011, 16:19:48
 * 
 */
public interface I5_6_send {
	public void send_I5_6(clsThingPresentationMesh poPerceptionalMesh, clsWordPresentationMesh moWordingToContext2);
}
