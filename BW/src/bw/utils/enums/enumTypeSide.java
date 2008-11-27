/**
 * @author deutsch
 * 
 * Copied from the old bubble family game
 * 
 * CHKME keep this file?
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */

// File enumTypeSide.java
// May 09, 2006
//

// Belongs to package
package bw.utils.enums;

// Imports

/**
 *
 * This is the class description ...
 *
 * $Revision$:  Revision of last commit
 * $Author$: Author of last commit
 * $Date$: Date of last commit
 *
 */
public class enumTypeSide extends enumClass {
  public final static int TSIDE_UNDEFINED  = -1;
  public final static int TSIDE_EVERYWHERE = 0;
  public final static int TSIDE_LEFT       = 1;
  public final static int TSIDE_MIDDLE     = 2;
  public final static int TSIDE_RIGHT      = 3;

  /** 
    * converts the enum value to a human readable string
    */
  public static String getString(int pnEnum) {
    String oResult = enumClass.getString(pnEnum);

    switch(pnEnum) {
      case TSIDE_UNDEFINED:oResult = "[undefined]";break;
      case  TSIDE_EVERYWHERE:oResult = "[everywhere]";break;
      case  TSIDE_LEFT:oResult = "[left]";break;
      case  TSIDE_MIDDLE:oResult = "[middle]";break;
      case  TSIDE_RIGHT:oResult = "[right]";break;

      default:oResult = "_unkown_"+pnEnum+"_";
    }

    return oResult;
  }
  
  /**
    * converts a string read from an XML-file into its corresponding enum value
    */
  public static int getInteger(String poValue)
  {
     int nValue = TSIDE_UNDEFINED;
     if( poValue.toLowerCase().equals("everywhere") )
     {
      nValue = TSIDE_EVERYWHERE;
     }
     else if( poValue.toLowerCase().equals("left") )
     {
      nValue = TSIDE_LEFT;
     }
     else if( poValue.toLowerCase().equals("middle") )
     {
      nValue = TSIDE_MIDDLE;
     }
     else if( poValue.toLowerCase().equals("right") )
     {
      nValue = TSIDE_RIGHT;
     }
     return nValue;
  }
};
