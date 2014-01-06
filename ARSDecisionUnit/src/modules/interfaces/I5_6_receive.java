/**
 * I2_20_receive.java: DecisionUnits - pa.interfaces.receive._v38
 * 
 * @author deutsch
 * 03.03.2011, 16:19:32
 */
package modules.interfaces;

import base.datatypes.clsThingPresentationMesh;
import base.datatypes.clsWordPresentationMesh;

/**
 * Similarly to I2.6, thing presentations are transported from F46 to F37. If quota of affects were retrieved from memory, these values are transported too.
 * 
 * @author deutsch
 * 03.03.2011, 16:19:32
 * 
 */
public interface I5_6_receive {
	public void receive_I5_6(clsThingPresentationMesh moPerceptionalMesh, clsWordPresentationMesh moWordingToContext2);
}
