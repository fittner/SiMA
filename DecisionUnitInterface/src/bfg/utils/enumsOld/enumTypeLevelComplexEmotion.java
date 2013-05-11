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

// File enumTypeLevelComplexEmotion.java
// May 09, 2006
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
public class enumTypeLevelComplexEmotion extends enumClass {
  public final static int TLEVELCEMOTION_UNDEFINED = -1;
  public final static int TLEVELCEMOTION_VERYLOW   = 0;
  public final static int TLEVELCEMOTION_LOW       = 1;
  public final static int TLEVELCEMOTION_NORMAL    = 2;
  public final static int TLEVELCEMOTION_HIGH      = 3;
  public final static int TLEVELCEMOTION_VERYHIGH = 4;

  /** 
    * converts the enum value to a human readable string
    */
  public static String getString(int pnEnum) {
    String oResult = enumClass.getString(pnEnum);

    switch(pnEnum) {
      case TLEVELCEMOTION_UNDEFINED:oResult = "[undefined]";break;
      case TLEVELCEMOTION_VERYLOW:oResult = "[verylow]";break;
      case TLEVELCEMOTION_LOW:oResult = "[low]";break;
      case TLEVELCEMOTION_NORMAL:oResult = "[normal]";break;
      case TLEVELCEMOTION_HIGH:oResult = "[high]";break;
      case TLEVELCEMOTION_VERYHIGH:oResult = "[veryhigh]";break;

      default:oResult = "_unkown_"+pnEnum+"_";
    }

    return oResult;
  }
  /**
    * converts a string read from an XML-file into its corresponding enum value
    */
  public static int getInteger(String poValue)
  {
     int nValue = TLEVELCEMOTION_UNDEFINED;
     if( poValue.toLowerCase().equals("verylow") )
     {
      nValue = TLEVELCEMOTION_VERYLOW;
     }
     else if( poValue.toLowerCase().equals("low") )
     {
      nValue = TLEVELCEMOTION_LOW;
     }
     else if( poValue.toLowerCase().equals("normal") )
     {
      nValue = TLEVELCEMOTION_NORMAL;
     }
     else if( poValue.toLowerCase().equals("high") )
     {
      nValue = TLEVELCEMOTION_HIGH;
     }
     else if( poValue.toLowerCase().equals("veryhigh") )
     {
      nValue = TLEVELCEMOTION_VERYHIGH;
     }
     return nValue;
  }
};
