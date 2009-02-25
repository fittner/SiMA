/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.sensors;

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
