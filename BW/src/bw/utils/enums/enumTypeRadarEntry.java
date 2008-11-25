// File enumTypeRadarEntry.java
// May 22, 2007
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
public class enumTypeRadarEntry  extends enumClass {
  public final static int TRADARENTRY_UNDEFINED    = -1;

  public final static int TRADARENTRY_ENTITY       = 0;
  public final static int TRADARENTRY_OBSTACLE     = 1;
  public final static int TRADARENTRY_LANDSCAPE    = 2;


  /** 
    * converts the enum value to a human readable string
    */
  public static String getString(int pnEnum) {
    String oResult = enumClass.getString(pnEnum);

    switch(pnEnum) {
      case  TRADARENTRY_UNDEFINED:oResult = "[undefined]";break;

      case  TRADARENTRY_ENTITY:oResult    = "[entity]";break;
      case  TRADARENTRY_OBSTACLE:oResult  = "[obstacle]";break;
      case  TRADARENTRY_LANDSCAPE:oResult = "[landscape]";break;

      default:oResult = "_unkown_"+pnEnum+"_";
    }

    return oResult;
  }
  /**
    * converts a string read from an XML-file into its corresponding enum value
    */
  public static int getInteger(String poValue)
  {
     int nValue = TRADARENTRY_UNDEFINED;
     if( poValue.toLowerCase().equals("entity"))
     {
      nValue = TRADARENTRY_ENTITY;
     }
     else if( poValue.toLowerCase().equals("obstacle") )
     {
      nValue = TRADARENTRY_OBSTACLE;
     }
     else if( poValue.toLowerCase().equals("landscape") )
     {
      nValue = TRADARENTRY_LANDSCAPE;
     }
     return nValue;
  }
  
};
