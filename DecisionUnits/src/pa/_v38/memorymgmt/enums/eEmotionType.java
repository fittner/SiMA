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
	GRIEF,
	LOVE,
	FEAR,
	PLEASURE,
	SATURATION,
	ELATION;
	
	public static eEmotionType getEmotionType(String poEmotionType) {
	
		return eEmotionType.valueOf(poEmotionType);
		
	}
}


