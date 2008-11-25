// File enumTypeScentIntensity.java
// May 09, 2006
//

// Belongs to package
package bw.utils.enums;

// Imports

/**
 *
 * This is the class description ...
 *
 * $Revision$:  Revision of last commit
 * $Author$: Author of last commit
 * $Date$: Date of last commit
 *
 */
public class enumTypeScentIntensity extends enumClass {
  public final static int TSCENTINT_UNDEFINED = -1;
  public final static int TSCENTINT_NONE    = 0;
  public final static int TSCENTINT_LOW     = 1;
  public final static int TSCENTINT_MEDIUM  = 2;
  public final static int TSCENTINT_HIGH    = 3;
  public final static int TSCENTINT_EXTREME = 4;

  /** 
    * converts the enum value to a human readable string
    */
  public static String getString(int pnEnum) {
    String oResult = enumClass.getString(pnEnum);

    switch(pnEnum) {
      case TSCENTINT_UNDEFINED:oResult = "[undefined]";break;
      case  TSCENTINT_NONE:oResult = "[none]";break;
      case  TSCENTINT_LOW:oResult = "[low]";break;
      case  TSCENTINT_MEDIUM:oResult = "[medium]";break;
      case  TSCENTINT_HIGH:oResult = "[high]";break;
      case  TSCENTINT_EXTREME:oResult = "[extreme]";break;

      default:oResult = "_unkown_"+pnEnum+"_";
    }

    return oResult;
  }

  /**
    * converts a string read from an XML-file into its corresponding enum value
    */
  public static int getInteger(String poValue)
  {
     int nValue = TSCENTINT_UNDEFINED;
     if( poValue.toLowerCase().equals("none") )
     {
      nValue = TSCENTINT_NONE;
     }
     else if( poValue.toLowerCase().equals("low") )
     {
      nValue = TSCENTINT_LOW;
     }
     else if( poValue.toLowerCase().equals("medium") )
     {
      nValue = TSCENTINT_MEDIUM;
     }
     else if( poValue.toLowerCase().equals("high") )
     {
      nValue = TSCENTINT_HIGH;
     }
     else if( poValue.toLowerCase().equals("heybubblegoonandtakeashower!") || poValue.toLowerCase().equals("extreme") )
     {
      nValue = TSCENTINT_EXTREME;
     }
     return nValue;
  }
};
