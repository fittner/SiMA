/**
 * @author deutsch
 * 
 * Copied from the old bubble family game
 * 
 * 
 *  taken from old BFG
 * 
 * $Rev:: 1825                 $: Revision of last commit
 * $Author:: deutsch           $: Author of last commit
 * $Date:: 2009-01-27 19:59:35#$: Date of last commit
 */

// File enumTypeRotation.java
// May 10, 2006
//

// Belongs to package
package bfg.utils.enumsOld;

import bfg.utils.enums.enumClass;

// Imports

/**
 *
 * This is the class description ...
 *
 * @deprecated
 * 
 * $Revision: 1825 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2009-01-27 19:59:35 +0100 (Di, 27 Jän 2009) $: Date of last commit
 *
 */
public class enumTypeRotation extends enumClass {
  public final static int TROTATION_UNDEFINED  = -1;

  public final static int TROTATION_WIDELEFT   = 1;
  public final static int TROTATION_LEFT       = 2;
  public final static int TROTATION_SHORTLEFT  = 3;
  public final static int TROTATION_NONE       = 4;
  public final static int TROTATION_SHORTRIGHT = 5;
  public final static int TROTATION_RIGHT      = 6;
  public final static int TROTATION_WIDERIGHT  = 7;

  /** 
    * converts the enum value to a human readable string
    */
  public static String getString(int pnEnum) {
    String oResult = enumClass.getString(pnEnum);

    switch(pnEnum) {
      case TROTATION_UNDEFINED:oResult = "[undefined]";break;
      case  TROTATION_WIDELEFT:oResult = "[wideleft]";break;
      case  TROTATION_LEFT:oResult = "[left]";break;
      case  TROTATION_SHORTLEFT:oResult = "[shortleft]";break;
      case  TROTATION_NONE:oResult = "[none]";break;
      case  TROTATION_SHORTRIGHT:oResult = "[shortright]";break;
      case  TROTATION_RIGHT:oResult = "[right]";break;
      case  TROTATION_WIDERIGHT:oResult = "[wideright]";break;

      default:oResult = "_unkown_"+pnEnum+"_";
    }

    return oResult;
  }
  /**
    * converts a string read from an XML-file into its corresponding enum value
    */
  public static int getInteger(String poValue)
  {
     int nValue = TROTATION_UNDEFINED;
     if( poValue.toLowerCase().equals("left") )
     {
      nValue = TROTATION_LEFT;
     }
     else if( poValue.toLowerCase().equals("shortleft") )
     {
      nValue = TROTATION_SHORTLEFT;
     }
     else if( poValue.toLowerCase().equals("none") )
     {
      nValue = TROTATION_NONE;
     }
     else if( poValue.toLowerCase().equals("shortright") )
     {
      nValue = TROTATION_SHORTRIGHT;
     }
     else if( poValue.toLowerCase().equals("right") )
     {
      nValue = TROTATION_RIGHT;
     }
      else if( poValue.toLowerCase().equals("wideright") )
     {
      nValue = TROTATION_WIDERIGHT;
     }
     return nValue;
  }
};
