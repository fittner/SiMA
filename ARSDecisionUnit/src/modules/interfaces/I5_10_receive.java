/**
 * I2_9.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:04:46
 */
package modules.interfaces;

import base.datatypes.clsThingPresentationMesh;

/**
 * Thing presentations of the perception (enriched with data from memory and feed back thing presentations) and their attached quota of affects are forwarded from F18 to F7.
 * 
 * @author deutsch
 * 11.08.2009, 14:04:46
 * 
 */
public interface I5_10_receive {
	public void receive_I5_10(clsThingPresentationMesh poPerceptionalMesh);
}
