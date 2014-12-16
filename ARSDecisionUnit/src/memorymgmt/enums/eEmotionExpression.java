/**
 * CHANGELOG
 *
 * 13.10.2014 schaat - File created
 *
 */
package memorymgmt.enums;

/**
 * DOCUMENT (schaat) - insert description 
 * 
 * @author schaat
 * 13.10.2014, 12:19:32
 * 
 */
public enum eEmotionExpression {
    CHEEKS_REDNING,
    EYE_BROW_CENTER,
    EYE_BROW_CORNERS,
    MOUTH_OPEN,
    MOUTH_SIDES,
    MOUTH_STRECHINESS,
    SHAKE_INTENSITY,
    EYES_CRYING_INTENSITY,
    GENERAL_SWEAT,
    PARTIAL_SWEAT;
    
    public static eEmotionExpression getEmotionExpression(String poEmotionExpression) {
        
        return eEmotionExpression.valueOf(poEmotionExpression);
        
    }

}
