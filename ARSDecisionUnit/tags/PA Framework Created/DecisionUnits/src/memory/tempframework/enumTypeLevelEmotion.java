// File enumTypeLevelEmotion.java
// May 09, 2006
//

// Belongs to package
package memory.tempframework;

// Imports

/**
 *
 * This is the class description ...
 *
 * $Revision: 580 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2007-05-31 18:07:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
 *
 */
public class enumTypeLevelEmotion extends enumClass {
  public final static int TLEVELEMOTION_UNDEFINED = -1;
  public final static int TLEVELEMOTION_NORMAL    = 0;
  public final static int TLEVELEMOTION_VERYLOW   = 1;
  public final static int TLEVELEMOTION_LOW       = 2;
  public final static int TLEVELEMOTION_MEDIUMLOW = 3;
  public final static int TLEVELEMOTION_MEDIUM    = 4;
  public final static int TLEVELEMOTION_MEDIUMHIGH= 5;
  public final static int TLEVELEMOTION_HIGH      = 6;
  public final static int TLEVELEMOTION_VERYHIGH  = 7;

  /** 
    * converts the enum value to a human readable string
    */
  public static String getString(int pnEnum) {
    String oResult = enumClass.getString(pnEnum);

    switch(pnEnum) {
      case  TLEVELEMOTION_UNDEFINED:oResult = "[undefined]";break;
      case  TLEVELEMOTION_NORMAL:oResult = "[normal]";break;
      case  TLEVELEMOTION_VERYLOW:oResult = "[verylow]";break;
      case  TLEVELEMOTION_LOW:oResult = "[low]";break;
      case  TLEVELEMOTION_MEDIUMLOW:oResult = "[medium low]";break;
      case  TLEVELEMOTION_MEDIUM:oResult = "[medium]";break;
      case  TLEVELEMOTION_MEDIUMHIGH:oResult = "[medium high]";break;
      case  TLEVELEMOTION_HIGH:oResult = "[high]";break;
      case  TLEVELEMOTION_VERYHIGH:oResult = "[veryhigh]";break;

      default:oResult = "_unkown_"+pnEnum+"_";
    }

    return oResult;
  }
  /**
    * converts a string read from an XML-file into its corresponding enum value
    */
  public static int getInteger(String poValue)
  {
     int nValue = TLEVELEMOTION_UNDEFINED;

     if( poValue.toLowerCase().equals("normal") )
     {
      nValue = TLEVELEMOTION_NORMAL;
     }
     else if( poValue.toLowerCase().equals("verylow") )
     {
      nValue = TLEVELEMOTION_VERYLOW;
     }
     else if( poValue.toLowerCase().equals("low") )
     {
      nValue = TLEVELEMOTION_LOW;
     }
     else if( poValue.toLowerCase().equals("mediumlow") )
     {
      nValue = TLEVELEMOTION_MEDIUMLOW;
     }
     else if( poValue.toLowerCase().equals("medium") )
     {
      nValue = TLEVELEMOTION_MEDIUM;
     }
     else if( poValue.toLowerCase().equals("mediumhigh") )
     {
      nValue = TLEVELEMOTION_MEDIUMHIGH;
     }
     else if( poValue.toLowerCase().equals("high") )
     {
      nValue = TLEVELEMOTION_HIGH;
     }
     else if( poValue.toLowerCase().equals("veryhigh") )
     {
      nValue = TLEVELEMOTION_VERYHIGH;
     }
     return nValue;
  }

  /**
    * converts the float value (range 0 to 1) into a symbolic enum value
    */
  public static int getEmotionLevel(float prLevel) {
    int nLevel = TLEVELEMOTION_UNDEFINED;

    if (prLevel < 0.143) {
      nLevel = TLEVELEMOTION_NORMAL;
    } else if (prLevel < 0.286) {
      nLevel = TLEVELEMOTION_LOW;
    } else if (prLevel < 0.429) {
      nLevel = TLEVELEMOTION_MEDIUMLOW;
    } else if (prLevel < 0.571) {
      nLevel = TLEVELEMOTION_MEDIUM;
    } else if (prLevel < 0.714) {
      nLevel = TLEVELEMOTION_MEDIUMHIGH;
    } else if (prLevel < 0.857) {
      nLevel = TLEVELEMOTION_HIGH;
    } else {
      nLevel = TLEVELEMOTION_VERYHIGH;
    }    
    return nLevel;
  }

  /**
    * converts the symbloc enum value into a float value
    */
  public static float getFloatLevel(int pnLevel) 
  {
    float nLevel = 0;

    switch( pnLevel )
    {
      case TLEVELEMOTION_NORMAL:
        nLevel = 0;
        break;
      case TLEVELEMOTION_LOW:
        nLevel = 0.2f;
        break;
      case TLEVELEMOTION_MEDIUMLOW:
        nLevel = 0.4f;
        break;
      case TLEVELEMOTION_MEDIUM:
        nLevel = 0.5f;
        break;
      case TLEVELEMOTION_MEDIUMHIGH:
        nLevel = 0.7f;
        break;
      case TLEVELEMOTION_HIGH:
        nLevel = 0.8f;
        break;
      case TLEVELEMOTION_VERYHIGH:
        nLevel = 1;
        break;
      default:
        nLevel = 0;
        break;
    }
    return nLevel;
  }

};
