/**
 * I2_14_send.java: DecisionUnits - pa.interfaces.send._v38
 * 
 * @author deutsch
 * 03.03.2011, 16:21:37
 */
package modules.interfaces;

import base.datatypes.clsThingPresentationMesh;
import base.datatypes.clsWordPresentationMesh;

/**
 * Thing presentations and their quota of affects are transported from F37 to F35 and F57.
 * 
 * @author deutsch
 * 03.03.2011, 16:21:37
 * 
 */
public interface I5_7_send {
	public 
    void send_I5_7(clsThingPresentationMesh poPerceptionalMesh, clsWordPresentationMesh moWordingToContext2);
}
