/**
 * @author deutsch
 * 23.04.2009, 11:27:40
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package complexbody.io.sensors.datatypes.enums;

/**
 * enum of all the sensor that detect internal values of the body 
 * 
 * @author deutsch
 * 23.04.2009, 11:27:40
 * 
 */
public enum eSensorIntType {
   UNDEFINED,
   //internal system (not homeostasis)
   
	//homeostasis
	ENERGY_CONSUMPTION,
	HEALTH,
	STAMINA,
	STOMACH,
	TEMPERATURE,
	FASTMESSENGER,
	ENERGY,
	STOMACHTENSION,
	SLOWMESSENGER,
	HEARTBEAT,
	INTESTINEPRESSURE,
}
