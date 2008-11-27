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

// File enumOptionalType.java
// May 09, 2006
//

// Belongs to package
package bw.utils.enums;

// Imports

/**
 *
 * Represents if a condition is "optional" or "mandatory".
 *
 * $Revision$:  Revision of last commit
 * $Author$: Author of last commit
 * $Date$: Date of last commit
 *
 */
public class enumOptionalType extends enumClass {
  public final static int TOPT_UNDEFINED  = -1;
  public final static int TOPT_OPTIONAL  = 0;
  public final static int TOPT_MANDATORY = 1;

  /** 
    * converts the enum value to a human readable string
    */
  public static String getString(int pnEnum) {
    String oResult = enumClass.getString(pnEnum);

    switch(pnEnum) {
      case TOPT_UNDEFINED:oResult = "[undefined]";break;
      case TOPT_OPTIONAL:oResult = "[optional]";break;
      case TOPT_MANDATORY:oResult = "[mandatory]";break;

      default:oResult = "_unkown_"+pnEnum+"_";
    }

    return oResult;
  }

  /**
    * converts a string read from an XML-file into its corresponding enum value
    */
  public static int getInteger(String poValue)
  {
     int nValue = TOPT_UNDEFINED;
     if( poValue.toLowerCase().equals("optional") )
     {
      nValue = TOPT_OPTIONAL;
     }
     else if( poValue.toLowerCase().equals("mandatory") )
     {
      nValue = TOPT_MANDATORY;
     }
     return nValue;
  }
};
