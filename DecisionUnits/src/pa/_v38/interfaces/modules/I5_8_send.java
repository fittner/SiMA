/**
 * I2_8.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:18:09
 */
package pa._v38.interfaces.modules;

import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;

/**
 * From F35, thing presentations and quotas of affects are transported to F45.
 * 
 * @author deutsch
 * 18.05.2010, 14:18:09
 * 
 */
public interface I5_8_send {
	public void send_I5_8(clsThingPresentationMesh poPerceptionalMesh);
}
