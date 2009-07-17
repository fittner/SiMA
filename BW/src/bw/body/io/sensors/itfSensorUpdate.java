/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.sensors;

import java.util.ArrayList;

import sim.physics2D.physicalObject.PhysicalObject2D;


/**
 * Guarantees the implementation of updateSensorData for all
 * subclasses of clsSensorExt and clsSensorInt.
 * After that, the clsEntity-ies logic can access the valid data.
 * 
 * @author langr
 * 
 */
public interface itfSensorUpdate {

	void updateSensorData();
	
}
