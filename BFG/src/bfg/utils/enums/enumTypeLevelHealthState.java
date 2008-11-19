// File enumTypeLevelHealthState.java
// May 09, 2006
//

// Belongs to package
package pkgEnum;

// Imports

/**
 *
 * This is the class description ...
 *
 * $Revision: 955 $:  Revision of last commit
 * $Author: gartner $: Author of last commit
 * $Date: 2008-03-11 12:32:22 +0100 (Di, 11 Mär 2008) $: Date of last commit
 *
 */
public class enumTypeLevelHealthState extends enumClass {
  public final static int TLEVELHEALTHST_UNDEFINED = -1;
  public final static int TLEVELHEALTHST_DEAD      = 0;
  public final static int TLEVELHEALTHST_VERYWEAK     = 1;
  public final static int TLEVELHEALTHST_WEAK         = 2;
  public final static int TLEVELHEALTHST_HEALTHY      = 3;
  public final static int TLEVELHEALTHST_string       = 4;

  /** 
    * converts the enum value to a human readable string
    */
  public static String getString(int pnEnum) {
    String oResult = enumClass.getString(pnEnum);

    switch(pnEnum) {
      case  TLEVELHEALTHST_UNDEFINED:oResult = "[undefined]";break;
      case  TLEVELHEALTHST_DEAD:oResult = "[dead]";break;
      case  TLEVELHEALTHST_VERYWEAK:oResult = "[veryweak]";break;
      case  TLEVELHEALTHST_WEAK:oResult = "[weak]";break;
      case  TLEVELHEALTHST_HEALTHY:oResult = "[healthy]";break;
      case  TLEVELHEALTHST_string:oResult = "[string]";break;

      default:oResult = "_unkown_"+pnEnum+"_";
    }

    return oResult;
  }
  /**
    * converts a string read from an XML-file into its corresponding enum value
    */
  public static int getInteger(String poValue)
  {
     int nValue = TLEVELHEALTHST_UNDEFINED;
     if( poValue.toLowerCase().equals("dead") )
     {
      nValue = TLEVELHEALTHST_DEAD;
     }
     else if( poValue.toLowerCase().equals("veryweak") )
     {
      nValue = TLEVELHEALTHST_VERYWEAK;
     }
     else if( poValue.toLowerCase().equals("weak") )
     {
      nValue = TLEVELHEALTHST_WEAK;
     }
     else if( poValue.toLowerCase().equals("healthy") )
     {
      nValue = TLEVELHEALTHST_HEALTHY;
     }
     else if( poValue.toLowerCase().equals("string") )
     {
      nValue = TLEVELHEALTHST_string;
     }
     return nValue;
  }

  /**
    * converts the float value (range 0 to larger than 1) into a symbolic enum value
    */
  public static int getHealthLevel(float prLevel) {
    float rEps = 0.0001f;

    int nLevel = TLEVELHEALTHST_UNDEFINED;

    if (prLevel < 0+rEps) {
      nLevel = enumTypeLevelHealthState.TLEVELHEALTHST_DEAD;
    } else if (prLevel < 0.2f+rEps) {
      nLevel = enumTypeLevelHealthState.TLEVELHEALTHST_VERYWEAK;
    } else if (prLevel < 0.6f+rEps) {
      nLevel = enumTypeLevelHealthState.TLEVELHEALTHST_WEAK;
    } else if (prLevel < 1.0f+rEps) {
      nLevel = enumTypeLevelHealthState.TLEVELHEALTHST_HEALTHY;
    } else {
      nLevel = enumTypeLevelHealthState.TLEVELHEALTHST_string;
    }    

    return nLevel;
  }

   public static float calcNormalizedLevel(float prLevel, float prNorm, float prMax) {
    float rResult = 0;

    if (prLevel < prNorm) {
      rResult = prLevel / prNorm;
    } else {
      if (prLevel > prMax) {
        prLevel = prMax;
      }

      rResult =  1.0f + ((prLevel - prNorm) / (prMax - prNorm));
    }

    return rResult;
  }

};
