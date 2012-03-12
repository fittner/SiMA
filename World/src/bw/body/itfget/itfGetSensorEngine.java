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

import bw.body.io.sensors.external.clsSensorEngine;
import bw.physicalObjects.sensors.clsEntitySensorEngine;;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 15.07.2009, 21:35:04
 * 
 */

public interface itfGetSensorEngine {
	public TreeMap<Double, clsEntitySensorEngine> getSensorEngineAreas();
	public clsSensorEngine getSensorEngine();
}
