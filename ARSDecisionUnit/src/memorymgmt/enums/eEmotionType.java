/**
 * CHANGELOG
 *
 * Jun 27, 2012 schaat - File created
 *
 */
package memorymgmt.enums;

/**
 * DOCUMENT (schaat) - insert description 
 * 
 * @author schaat
 * Jun 27, 2012, 10:21:22 AM
 * 
 */
public enum eEmotionType {
	
	// Basic Emotions	
	ANGER,
	MOURNING,
	ANXIETY,
	JOY,
	SATURATION,
	ELATION,
	
	// Complex Emotions	
	GUILT,
	MELANCHOLIA,
	SHAME,
	PITY,
	DISGUST,
	HATE,
	LOVE,
	ENVY,	
	
	//Emotions from FG through F20 -- Replace these emotions someday with the ones above
	CONFLICT,
	//ANXIETY, 
	WORRIEDNESS,
	PRICKLE;
	
	
	public static eEmotionType getEmotionType(String poEmotionType) {
	
		return eEmotionType.valueOf(poEmotionType);
		
	}
}


