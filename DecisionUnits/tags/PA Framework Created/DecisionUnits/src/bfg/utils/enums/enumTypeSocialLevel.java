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

// File enumTypeSocialLevel.java
// September 20, 2006
//

// Belongs to package
package bfg.utils.enums;

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
public class enumTypeSocialLevel  extends enumClass {
  public final static int TSOCIALLEVEL_UNDEFINED  = -99;
  public final static int TSOCIALLEVEL_VERYLOW    = -3;
  public final static int TSOCIALLEVEL_LOW        = -2;
  public final static int TSOCIALLEVEL_MEDIUMLOW  = -1;
  public final static int TSOCIALLEVEL_NORMAL     = 0;
  public final static int TSOCIALLEVEL_MEDIUMHIGH = 1;
  public final static int TSOCIALLEVEL_HIGH       = 2;
  public final static int TSOCIALLEVEL_VERYHIGH   = 3;

  /** 
    * converts the enum value to a human readable string
    */
  public static String getString(int pnEnum) {
    String oResult = enumClass.getString(pnEnum);

    switch(pnEnum) {
      case TSOCIALLEVEL_UNDEFINED:oResult = "[undefined]";break;
      case TSOCIALLEVEL_VERYLOW:oResult = "[very low]";break;
      case TSOCIALLEVEL_LOW:oResult = "[low]";break;
      case TSOCIALLEVEL_MEDIUMLOW:oResult = "[medium low]";break;
      case TSOCIALLEVEL_NORMAL:oResult = "[normal]";break;
      case TSOCIALLEVEL_MEDIUMHIGH:oResult = "[medium high]";break;
      case TSOCIALLEVEL_HIGH:oResult = "[high]";break;
      case TSOCIALLEVEL_VERYHIGH:oResult = "[very high]";break;

      default:oResult = "_unkown_"+pnEnum+"_";
    }

    return oResult;
  }
  /**
    * converts a string read from an XML-file into its corresponding enum value
    */
  public static int getInteger(String poValue)
  {
     int nValue = TSOCIALLEVEL_NORMAL;
     if( poValue.toLowerCase().equals("verylow") )
     {
      nValue = TSOCIALLEVEL_VERYLOW;
     }
     else if( poValue.toLowerCase().equals("low") )
     {
      nValue = TSOCIALLEVEL_LOW;
     }
     else if( poValue.toLowerCase().equals("mediumlow") )
     {
      nValue = TSOCIALLEVEL_MEDIUMLOW;
     }
     else if( poValue.toLowerCase().equals("normal") )
     {
      nValue = TSOCIALLEVEL_NORMAL;
     }
     else if( poValue.toLowerCase().equals("mediumhigh") )
     {
      nValue = TSOCIALLEVEL_MEDIUMHIGH;
     }
     else if( poValue.toLowerCase().equals("high") )
     {
      nValue = TSOCIALLEVEL_HIGH;
     }
     else if( poValue.toLowerCase().equals("veryhigh") )
     {
      nValue = TSOCIALLEVEL_VERYHIGH;
     }
     return nValue;
  }

  /**
    * converts a float value (-1 to 1) into its corresponding symbolic enum value
    */
  public static int getSocialLevel(float prSocialLevel) {
    int nResult = enumTypeSocialLevel.TSOCIALLEVEL_NORMAL; //normal

    if        (prSocialLevel < -0.75) {
      nResult = enumTypeSocialLevel.TSOCIALLEVEL_VERYLOW;
    } else if (prSocialLevel < -0.50) {
      nResult = enumTypeSocialLevel.TSOCIALLEVEL_LOW;
    } else if (prSocialLevel < -0.25) {
      nResult = enumTypeSocialLevel.TSOCIALLEVEL_MEDIUMLOW;
    } else if (prSocialLevel >  0.25) {
      nResult = enumTypeSocialLevel.TSOCIALLEVEL_MEDIUMHIGH;
    } else if (prSocialLevel >  0.50) {
      nResult = enumTypeSocialLevel.TSOCIALLEVEL_HIGH;
    } else if (prSocialLevel >  0.75) {
      nResult = enumTypeSocialLevel.TSOCIALLEVEL_VERYHIGH;
    }

    return nResult;
  }
};
