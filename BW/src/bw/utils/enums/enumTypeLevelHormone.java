// File enumTypeLevelHormone.java
// May 22, 2007
//

// Belongs to package
package bw.utils.enums;

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
public class enumTypeLevelHormone extends enumClass {
  public final static int TLEVELHORMONE_UNDEFINED  = -1;

  public final static int TLEVELHORMONE_VERYLOW    = 0;
  public final static int TLEVELHORMONE_LOW        = 1;
  public final static int TLEVELHORMONE_MEDIUMLOW  = 2;
  public final static int TLEVELHORMONE_NORMAL     = 3;
  public final static int TLEVELHORMONE_MEDIUMHIGH = 4;
  public final static int TLEVELHORMONE_HIGH       = 5;
  public final static int TLEVELHORMONE_VERYHIGH   = 6;

  /** 
    * converts the enum value to a human readable string
    */
  public static String getString(int pnEnum) {
    String oResult = enumClass.getString(pnEnum);

    switch(pnEnum) {
      case TLEVELHORMONE_UNDEFINED:oResult  = "[undefined]";break;
      case TLEVELHORMONE_VERYLOW:oResult    = "[verylow]";break;
      case TLEVELHORMONE_LOW:oResult        = "[low]";break;
      case TLEVELHORMONE_MEDIUMLOW:oResult  = "[mediumlow]";break;
      case TLEVELHORMONE_NORMAL:oResult     = "[normal]";break;
      case TLEVELHORMONE_MEDIUMHIGH:oResult = "[mediumhigh]";break;
      case TLEVELHORMONE_HIGH:oResult       = "[high]";break;
      case TLEVELHORMONE_VERYHIGH:oResult   = "[veryhigh]";break;

      default:oResult = "_unkown_"+pnEnum+"_";
    }

    return oResult;
  }
  /**
    * converts a string read from an XML-file into its corresponding enum value
    */
  public static int getInteger(String poValue)
  {
     int nValue = TLEVELHORMONE_UNDEFINED;
     if( poValue.toLowerCase().equals("verylow") )
     {
      nValue = TLEVELHORMONE_VERYLOW;
     }
     else if( poValue.toLowerCase().equals("low") )
     {
      nValue = TLEVELHORMONE_LOW;
     }
     else if( poValue.toLowerCase().equals("mediumlow") )
     {
      nValue = TLEVELHORMONE_MEDIUMLOW;
     }
     else if( poValue.toLowerCase().equals("normal") )
     {
      nValue = TLEVELHORMONE_NORMAL;
     }
     else if( poValue.toLowerCase().equals("mediumhigh") )
     {
      nValue = TLEVELHORMONE_MEDIUMHIGH;
     }
     else if( poValue.toLowerCase().equals("high") )
     {
      nValue = TLEVELHORMONE_HIGH;
     }
     else if( poValue.toLowerCase().equals("veryhigh") )
     {
      nValue = TLEVELHORMONE_VERYHIGH;
     }
     return nValue;
  }

  /**
    * converts the float value (range from -1 to 1) into a symbolic enum value
    */
  public static int getHormoneLevel(float prLevel) {
    int nLevel = TLEVELHORMONE_UNDEFINED;

    if (prLevel < -0.75) {
      nLevel = TLEVELHORMONE_VERYLOW;
    } else if (prLevel < -0.4) {
      nLevel = TLEVELHORMONE_LOW;
    } else if (prLevel < -0.2) {
      nLevel = TLEVELHORMONE_MEDIUMLOW;
    } else if (prLevel < 0.2) {
      nLevel = TLEVELHORMONE_NORMAL;
    } else if (prLevel < 0.4) {
      nLevel = TLEVELHORMONE_MEDIUMHIGH;
    } else if (prLevel < 0.75) {
      nLevel = TLEVELHORMONE_HIGH;
    } else {
      nLevel = TLEVELHORMONE_VERYHIGH;
    }    
    return nLevel;
  }
};
