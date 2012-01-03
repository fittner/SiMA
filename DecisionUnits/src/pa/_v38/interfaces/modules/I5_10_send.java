/**
 * I2_9.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:04:46
 */
package pa._v38.interfaces.modules;

import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;

/**
 * Thing presentations of the perception (enriched with data from memory and feed back thing presentations) and their attached quota of affects are forwarded from F18 to F7.
 * 
 * @author deutsch
 * 18.05.2010, 14:04:46
 * 
 */
public interface I5_10_send {
	public void send_I5_10(clsThingPresentationMesh poPerceptionalMesh);
}
