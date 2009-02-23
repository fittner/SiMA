/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.utils.enums;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public enum eBodyParts {
	//internal systems
	INTSYS_FAST_MESSENGER_SYSTEM,
	INTSYS_FLESH,
	INTSYS_HEALTH_SYSTEM,
	INTSYS_INTERNAL_ENERGY_CONSUMPTION,
	INTSYS_INTERNAL_SYSTEM,
	INTSYS_SLOW_MESSENGER_SYSTEM,
	INTSYS_STAMINA_SYSTEM,
	INTSYS_STOMACH_SYSTEM,
	INTSYS_TEMPERATURE_SYSTEM,
	
	//intrabodysysems
	INTRA_DAMAGE_NUTRITION,
	INTRA_DAMAGE_TEMPERATURE,
	
	//interbodyworldsystems
	INTER_DAMAGE_BUMP,
	INTER_DAMAGE_LIGHTNING,
	
	//sensors
	SENSOR_EXT,
	SENSOR_INT,
	SENSOR_EXT_ACOUSTIC, 
	SENSOR_EXT_VISION,
	SENSOR_EXT_OLFACTORIC,
	SENSOR_EXT_TACTITLE,
	SENSOR_EXT_TACTILE_BUMP,
	SENSOR_EXT_ACCELERATION,
	SENSOR_EXT_EATVISION,
	
	
	//actuators
	ACTUATOR_EXT,
	ACTUATOR_INT,
	ACTUATOR_EXT_EAT,
	
	//limbs
	TENTACLE,
	TENTACLE_LEFT,

	//brain
	BRAIN,
}
