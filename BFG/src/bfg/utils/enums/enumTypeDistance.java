// File enumTypeDistance.java
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
public class enumTypeDistance extends enumClass {
  public final static int TDISTANCE_UNDEFINED  = -1;
  public final static int TDISTANCE_NULL       = 0;
  public final static int TDISTANCE_ABOVE      = 1;
  public final static int TDISTANCE_VERYSHORT  = 2;
  public final static int TDISTANCE_SHORT      = 3;
  public final static int TDISTANCE_MEDIUM     = 4;
  public final static int TDISTANCE_FAR        = 5;

  /** 
    * converts the enum value to a human readable string
    */
  public static String getString(int pnEnum) {
    String oResult = enumClass.getString(pnEnum);

    switch(pnEnum) {
      case  TDISTANCE_UNDEFINED:oResult = "[undefined]";break;
      case  TDISTANCE_NULL:oResult = "[null]";break;
      case  TDISTANCE_ABOVE:oResult = "[above]";break;
      case  TDISTANCE_VERYSHORT:oResult = "[veryshort]";break;
      case  TDISTANCE_SHORT:oResult = "[short]";break;
      case  TDISTANCE_MEDIUM:oResult = "[medium]";break;
      case  TDISTANCE_FAR:oResult = "[far]";break;

      default:oResult = "_unkown_"+pnEnum+"_";
    }

    return oResult;
  }

  /**
    * converts a string read from an XML-file into its corresponding enum value
    */
  public static int getInteger(String poValue)
  {
     if(poValue==null) return TDISTANCE_UNDEFINED;
     int nValue = TDISTANCE_UNDEFINED;
     if( poValue.toLowerCase().equals("null") || poValue.toLowerCase().equals("everywhere") )
     {
      nValue = TDISTANCE_NULL;
     }
     else if( poValue.toLowerCase().equals("above") )
     {
      nValue = TDISTANCE_ABOVE;
     }
     else if( poValue.toLowerCase().equals("veryshort") )
     {
      nValue = TDISTANCE_VERYSHORT;
     }
     else if( poValue.toLowerCase().equals("close") || poValue.toLowerCase().equals("short") )
     {
      nValue = TDISTANCE_SHORT;
     }
     else if( poValue.toLowerCase().equals("medium") )
     {
      nValue = TDISTANCE_MEDIUM;
     }
     else if( poValue.toLowerCase().equals("far") )
     {
      nValue = TDISTANCE_FAR;
     }
     return nValue;
  }
};
