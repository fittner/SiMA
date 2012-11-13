/**
 * CHANGELOG
 *
 * Jun 27, 2012 schaat - File created
 *
 */
package pa._v38.memorymgmt.enums;

/**
 * DOCUMENT (schaat) - insert description 
 * 
 * @author schaat
 * Jun 27, 2012, 10:21:22 AM
 * 
 */
public enum eEmotionType {
	ANGER,
	MOURNING,
	ANXIETY,
	JOY,
	SATURATION,
	ELATION,
	
	//Emotions from FG through F20 -- Replace these emotions someday with the ones above
	CONFLICT,
	//ANXIETY, 
	WORRIEDNESS,
	PRICKLE;
	
	
	public static eEmotionType getEmotionType(String poEmotionType) {
	
		return eEmotionType.valueOf(poEmotionType);
		
	}
}


