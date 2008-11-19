// File enumTypeLevelDrive.java
// May 09, 2006
//

// Belongs to package
package pkgEnum;

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
public class enumTypeLevelDrive extends enumClass {
  public final static int TLEVELDRIVE_UNDEFINED = -1;

  public final static int TLEVELDRIVE_NORMAL    = 0;
  public final static int TLEVELDRIVE_VERYLOW   = 1;
  public final static int TLEVELDRIVE_LOW       = 2;
  public final static int TLEVELDRIVE_MEDIUMLOW = 3;
  public final static int TLEVELDRIVE_MEDIUM    = 4;
  public final static int TLEVELDRIVE_MEDIUMHIGH= 5;
  public final static int TLEVELDRIVE_HIGH      = 6;
  public final static int TLEVELDRIVE_VERYHIGH  = 7;

  /** 
    * converts the enum value to a human readable string
    */
  public static String getString(int pnEnum) {
    String oResult = enumClass.getString(pnEnum);

    switch(pnEnum) {
      case  TLEVELDRIVE_UNDEFINED:oResult = "[undefined]";break;
      case  TLEVELDRIVE_NORMAL:oResult = "[normal]";break;
      case  TLEVELDRIVE_VERYLOW:oResult = "[very low]";break;
      case  TLEVELDRIVE_LOW:oResult = "[low]";break;
      case  TLEVELDRIVE_MEDIUMLOW:oResult = "[medium low]";break;
      case  TLEVELDRIVE_MEDIUM:oResult = "[medium]";break;
      case  TLEVELDRIVE_MEDIUMHIGH:oResult = "[medium high]";break;
      case  TLEVELDRIVE_HIGH:oResult = "[high]";break;
      case  TLEVELDRIVE_VERYHIGH:oResult = "[veryhigh]";break;

      default:oResult = "_unkown_"+pnEnum+"_";
    }

    return oResult;
  }
  /**
    * converts a string read from an XML-file into its corresponding enum value
    */
  public static int getInteger(String poValue)
  {
     int nValue = TLEVELDRIVE_UNDEFINED;
     if( poValue.toLowerCase().equals("normal") )
     {
      nValue = TLEVELDRIVE_NORMAL;
     }
     else if( poValue.toLowerCase().equals("verylow") )
     {
      nValue = TLEVELDRIVE_VERYLOW;
     }
     else if( poValue.toLowerCase().equals("low") )
     {
      nValue = TLEVELDRIVE_LOW;
     }
     else if( poValue.toLowerCase().equals("mediumlow") )
     {
      nValue = TLEVELDRIVE_MEDIUMLOW;
     }
     else if( poValue.toLowerCase().equals("medium") )
     {
      nValue = TLEVELDRIVE_MEDIUM;
     }
     else if( poValue.toLowerCase().equals("mediumhigh") )
     {
      nValue = TLEVELDRIVE_MEDIUMHIGH;
     }
     else if( poValue.toLowerCase().equals("high") )
     {
      nValue = TLEVELDRIVE_HIGH;
     }
     else if( poValue.toLowerCase().equals("veryhigh") )
     {
      nValue = TLEVELDRIVE_VERYHIGH;
     }
     return nValue;
  }

  /**
    * converts the float value (range 0 to 1) into a symbolic enum value
    */
  public static int getDriveLevel(float prLevel) {
    int nLevel = TLEVELDRIVE_UNDEFINED;

    if (prLevel < 0.05) {
      nLevel = TLEVELDRIVE_NORMAL;
    } else if (prLevel < 0.18) {
      nLevel = TLEVELDRIVE_VERYLOW;
    } else if (prLevel < 0.286) {
      nLevel = TLEVELDRIVE_LOW;
    } else if (prLevel < 0.429) {
      nLevel = TLEVELDRIVE_MEDIUMLOW;
    } else if (prLevel < 0.571) {
      nLevel = TLEVELDRIVE_MEDIUM;
    } else if (prLevel < 0.714) {
      nLevel = TLEVELDRIVE_MEDIUMHIGH;
    } else if (prLevel < 0.857) {
      nLevel = TLEVELDRIVE_HIGH;
    } else {
      nLevel = TLEVELDRIVE_VERYHIGH;
    }    
    return nLevel;
  }
};
