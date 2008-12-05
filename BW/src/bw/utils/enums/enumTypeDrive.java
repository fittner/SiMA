/**
 * @author deutsch
 * 
 * Copied from the old bubble family game
 * 
 * CHKME keep this file?
 * @deprecated taken from old BFG
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */

// File enumTypeDrive.java
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
public class enumTypeDrive extends enumClass {
  public final static int TDRIVE_UNDEFINED  = -1;
  public final static int TDRIVE_ANY  = 0;
  public final static int TDRIVE_HUNGER  = 1;
  public final static int TDRIVE_THIRST  = 2;
  public final static int TDRIVE_SEEK    = 3;
  public final static int TDRIVE_PLAY    = 4;
  public final static int TDRIVE_SLEEP   = 5;

  /** 
    * converts the enum value to a human readable string
    */
  public static String getString(int pnEnum) {
    String oResult = enumClass.getString(pnEnum);

    switch(pnEnum) {
      case  TDRIVE_ANY:oResult = "[any]";break;
      case  TDRIVE_HUNGER:oResult = "[hunger]";break;
      case  TDRIVE_THIRST:oResult = "[thirst]";break;
      case  TDRIVE_SEEK:oResult = "[seek]";break;
      case  TDRIVE_PLAY:oResult = "[play]";break;
      case  TDRIVE_SLEEP:oResult = "[sleep]";break;

      default:oResult = "_unkown_"+pnEnum+"_";
    }

    return oResult;
  }

  /**
    * converts a string read from an XML-file into its corresponding enum value
    */
  public static int getInteger(String poValue)
  {
     int nValue = TDRIVE_UNDEFINED;
     if( poValue.toLowerCase().equals("any") )
     {
      nValue = TDRIVE_ANY;
     }
     else if( poValue.toLowerCase().equals("hunger") )
     {
      nValue = TDRIVE_HUNGER;
     }
     else if( poValue.toLowerCase().equals("thirst") )
     {
      nValue = TDRIVE_THIRST;
     }
     else if( poValue.toLowerCase().equals("seek") )
     {
      nValue = TDRIVE_SEEK;
     }
     else if( poValue.toLowerCase().equals("play") )
     {
      nValue = TDRIVE_PLAY;
     }
     else if( poValue.toLowerCase().equals("sleep") )
     {
      nValue = TDRIVE_SLEEP;
     }
     return nValue;
  }
};
