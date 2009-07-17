/**
 * @author zeilinger
 * 15.07.2009, 21:35:04
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.itfget;

import java.util.TreeMap;
import bw.physicalObjects.sensors.clsEntitySensorEngine;;

/**
 * TODO (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 15.07.2009, 21:35:04
 * 
 */
public interface itfGetSensorEngine {
	public TreeMap<Double, clsEntitySensorEngine> getSensorEngine();
}
