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

// File enumTypeObstacle.java
// May 22, 2007
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
public class enumTypeObstacle extends enumClass {
  public final static int TOBSTACLE_UNDEFINED    = -1;

  public final static int TOBSTACLE_ANY          = -2;

  public final static int TOBSTACLE_STONE        = 0;
  public final static int TOBSTACLE_HOLYCROSS    = 1;
  public final static int TOBSTACLE_UNHOLYCROSS  = 2;


  /** 
    * converts the enum value to a human readable string
    */
  public static String getString(int pnEnum) {
    String oResult = enumClass.getString(pnEnum);

    switch(pnEnum) {
      case  TOBSTACLE_UNDEFINED:oResult = "[undefined]";break;
      case  TOBSTACLE_ANY:oResult = "[any]";break;

      case  TOBSTACLE_STONE:oResult = "[stone]";break;
      case  TOBSTACLE_HOLYCROSS:oResult = "[holycross]";break;
      case  TOBSTACLE_UNHOLYCROSS:oResult = "[unholycross]";break;

      default:oResult = "_unkown_"+pnEnum+"_";
    }

    return oResult;
  }
  /**
    * converts a string read from an XML-file into its corresponding enum value
    */
  public static int getInteger(String poValue)
  {
     int nValue = TOBSTACLE_UNDEFINED;
     if( poValue.toLowerCase().equals("stone"))
     {
      nValue = TOBSTACLE_STONE;
     }
     else if( poValue.toLowerCase().equals("any") )
     {
      nValue = TOBSTACLE_ANY;
     }
     else if( poValue.toLowerCase().equals("holycross") )
     {
      nValue = TOBSTACLE_HOLYCROSS;
     }
     else if( poValue.toLowerCase().equals("unholycross") )
     {
      nValue = TOBSTACLE_UNHOLYCROSS;
     }
     return nValue;
  }
  
};
