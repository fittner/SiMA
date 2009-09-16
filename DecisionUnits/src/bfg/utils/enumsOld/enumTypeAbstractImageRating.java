/**
 * @author deutsch
 * 
 * Copied from the old bubble family game
 * 
 * CHKME (deutsch) - keep this file?
 * @deprecated taken from old BFG
 * 
 * $Rev:: 1825                 $: Revision of last commit
 * $Author:: deutsch           $: Author of last commit
 * $Date:: 2009-01-27 19:59:35#$: Date of last commit
 */

// File enumTypeAbstractImageRating.java
// July 11, 2006
//

// Belongs to package
package bfg.utils.enumsOld;

// Imports

/**
 *
 * Represents the match value of a template image with the currently perceived situation.
 *
 * @deprecated
 * 
 * $Revision: 1825 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2009-01-27 19:59:35 +0100 (Di, 27 Jän 2009) $: Date of last commit
 *
 */
public class enumTypeAbstractImageRating extends enumClass {

  public final static int TAIRATING_UNDEFINED = -1;

  public final static int TAIRATING_NULL      = 0;
  public final static int TAIRATING_LOW       = 1;
  public final static int TAIRATING_MEDIUM    = 2;
  public final static int TAIRATING_HIGH      = 3;
  public final static int TAIRATING_FULL      = 4;

  /** 
    * converts the enum value to a human readable string
    */
  public static String getString(int pnEnum) {
    String oResult = enumClass.getString(pnEnum);

    switch(pnEnum) {
      case  TAIRATING_UNDEFINED:oResult = "[undefined]";break;
      case  TAIRATING_NULL:oResult = "null";break;
      case  TAIRATING_LOW:oResult = "low";break;
      case  TAIRATING_MEDIUM:oResult = "medium";break;
      case  TAIRATING_HIGH:oResult = "high";break;
      case  TAIRATING_FULL:oResult = "full";break;
      default:oResult = "_unkown_"+pnEnum+"_";
    }

    return oResult;
  }
  /**
    * converts a string read from an XML-file into its corresponding enum value
    */
  public static int getInteger(String poValue)
  {
     int nValue = TAIRATING_UNDEFINED;

     if( poValue.toLowerCase().equals("null") ) {
       nValue = TAIRATING_NULL;
     } else if( poValue.toLowerCase().equals("low") ) {
       nValue = TAIRATING_LOW;
     } else if( poValue.toLowerCase().equals("medium") ) {
       nValue = TAIRATING_MEDIUM;
     } else if( poValue.toLowerCase().equals("high") ) {
       nValue = TAIRATING_HIGH;
     } else if( poValue.toLowerCase().equals("full") ) {
       nValue = TAIRATING_FULL;
     }

     return nValue;
  }

  /**
    * converts the symbolic enum value into a float value
    */  
  public static float get0to1(int pnEnumValue)
  {
    float oResult = 0.0f;

    switch(pnEnumValue) {
      case TAIRATING_UNDEFINED:oResult = 0.0f;break;
      case TAIRATING_NULL:oResult = 0.0f;break;
      case TAIRATING_LOW:oResult = 0.25f;break;
      case TAIRATING_MEDIUM:oResult = 0.5f;break;
      case TAIRATING_HIGH:oResult = 0.75f;break;
      case TAIRATING_FULL:oResult = 1.0f;break;
    }

    return oResult;


  }
}
