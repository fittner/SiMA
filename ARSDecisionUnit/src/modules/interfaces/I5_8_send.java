/**
 * I2_8.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:18:09
 */
package modules.interfaces;

import base.datatypes.clsThingPresentationMesh;
import base.datatypes.clsWordPresentationMesh;

/**
 * From F35, thing presentations and quotas of affects are transported to F45.
 * 
 * @author deutsch
 * 18.05.2010, 14:18:09
 * 
 */
public interface I5_8_send {
	public 
    void send_I5_8(clsThingPresentationMesh poPerceptionalMesh, clsWordPresentationMesh moWordingToContext2);
}
