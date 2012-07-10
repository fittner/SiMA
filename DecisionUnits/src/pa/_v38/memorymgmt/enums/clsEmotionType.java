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
public enum clsEmotionType {
	ANGER,
	GRIEF,
	LOVE,
	FEAR;
	
	public static clsEmotionType getEmotionType(String poEmotionType) {
		if (poEmotionType.equals("ANGER")){
			return clsEmotionType.ANGER;
		}
		else if(poEmotionType.equals("GRIEF")){
			return clsEmotionType.GRIEF;
		}	
		else if(poEmotionType.equals("LOVE")){
			return clsEmotionType.LOVE;
		}
		else if(poEmotionType.equals("FEAR")){
			return clsEmotionType.FEAR;
		}
		return null;
		
	}
}


