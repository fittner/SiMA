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

// File enumTypeHealthState.java
// May 09, 2006
//

// Belongs to package
package bw.utils.enums;

// Imports

/**
 *
 * This is the class description ...
 *
 * @deprecated
 * 
 * $Revision$:  Revision of last commit
 * $Author$: Author of last commit
 * $Date$: Date of last commit
 *
 */
public class enumTypeHealthState extends enumClass {
  public final static int THEALTHST_UNDEFINED = -1;
  public final static int THEALTHST_GENERAL   = 0;
  public final static int THEALTHST_ENERGY    = 1;
  public final static int THEALTHST_STAMINA   = 2;

  /** 
    * converts the enum value to a human readable string
    */
  public static String getString(int pnEnum) {
    String oResult = enumClass.getString(pnEnum);

    switch(pnEnum) {
      case  THEALTHST_GENERAL : oResult = "[general]";break;
      case  THEALTHST_ENERGY  : oResult = "[energy]";break;
      case  THEALTHST_STAMINA : oResult = "[stamina]";break;

      default:oResult = "_unkown_"+pnEnum+"_";
    }

    return oResult;
  }

  /**
    * converts a string read from an XML-file into its corresponding enum value
    */
  public static int getInteger(String poValue)
  {
     int nValue = THEALTHST_UNDEFINED;
     if( poValue.toLowerCase().equals("general") )
     {
      nValue = THEALTHST_GENERAL;
     }
     else if( poValue.toLowerCase().equals("energy") )
     {
      nValue = THEALTHST_ENERGY;
     }
     else if( poValue.toLowerCase().equals("stamina") )
     {
      nValue = THEALTHST_STAMINA;
     }     
     return nValue;
  }
};
