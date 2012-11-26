/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.utils.enums;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public enum eBodyParts {
	//internal systems
	INTSYS,
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
	INTRA,
	INTRA_DAMAGE_NUTRITION,
	INTRA_DAMAGE_TEMPERATURE,
	INTRA_BODYCOLOR,
	INTRA_GROWTH,
	
	//interbodyworldsystems
	INTER,
	INTER_DAMAGE_BUMP,
	INTER_DAMAGE_LIGHTNING,
	INTER_CONSUME_FOOD,
	INTER_PAIN_STOMACHTENSION,
	INTER_ORIFICE_ORAL_MUCOSA,
	INTER_ORIFICE_RECTAL_MUCOSA,
	
	EXTERNAL_IO,
	INTERNAL_IO,
	
	//sensors
	SENSOR_EXT,
	SENSOR_INT,
	SENSOR_EXT_ACOUSTIC, 
	SENSOR_EXT_VISION,
	SENSOR_EXT_RADIATION,
	SENSOR_EXT_OLFACTORIC,
	SENSOR_EXT_TACTITLE,
	SENSOR_EXT_TACTILE_BUMP,
	SENSOR_EXT_ACCELERATION,
	SENSOR_EXT_VISION_EATABLE_AREA,
	SENSOR_EXT_VISION_MANIPULATE_AREA,
	SENSOR_EXT_POSITIONCHANGE,
	
	SENSOR_INT_HEALTH,
	SENSOR_INT_TEMPERATURE,
	SENSOR_INT_ENERGYCONSUMPTION,
	SENSOR_INT_STAMINA,
	SENSOR_INT_STOMACH,
	SENSOR_INT_ENERGY,
	SENSOR_INT_STOMACHTENSION,
	SENSOR_INT_FASTMESSENGER,
	SENSOR_INT_SLOWMESSENGER,
	SENSOR_INT_INTESTINEPRESSURE,
	

	//actions
	ACTIONEX_TURN,
	ACTIONEX_MOVE,
	ACTIONEX_EAT,
	ACTIONEX_PICKUP,
	ACTIONEX_DROP,
	ACTIONEX_FROMINVENTORY,
	ACTIONEX_TOINVENTORY,
	ACTIONEX_BODYCOLOR,
	ACTIONEX_FACIALEXPRESSIONS,
	ACTIONEX_CULTIVATE,
	ACTIONEX_KISS,
	ACTIONEX_ATTACKBITE,
	ACTIONEX_ATTACKLIGHTNING,
	ACTIONEX_MOVETOAREA,
	ACTIONEX_EXCREMENT,
	ACTIONEX_SLEEP,
	ACTIONEX_BINDING,
	ACTIONEX_TURNVISION,
	ACTIONEX_INNERSPEECH,
	
	ACTIONEX_INTERNAL,
	
	//limbs
	TENTACLE,
	TENTACLE_LEFT,
	TENTACLE_RIGHT,
	HAND,
	HAND_LEFT,
	HAND_RIGHT,

	//brain
	BRAIN,
}
