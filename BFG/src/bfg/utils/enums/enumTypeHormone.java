// File enumTypeEmotion.java
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
public class enumTypeHormone extends enumClass {
  public final static int THORMONE_UNDEFINED  = -1;
  public final static int THORMONE_ANY        = 0;

  public final static int THORMONE_FEAR       = 1;
  public final static int THORMONE_SEEK       = 2;
  public final static int THORMONE_PANIC      = 3;
  public final static int THORMONE_RAGE       = 4;


  /** 
    * converts the enum value to a human readable string
    */
  public static String getString(int pnEnum) {
    String oResult = enumClass.getString(pnEnum);

    switch(pnEnum) {
      case  THORMONE_ANY  :oResult = "[any]";break;
      case  THORMONE_FEAR :oResult = "[fear]";break;
      case  THORMONE_SEEK :oResult = "[seek]";break;
      case  THORMONE_PANIC:oResult = "[panic]";break;
      case  THORMONE_RAGE :oResult = "[rage]";break;

      default:oResult = "_unkown_"+pnEnum+"_";
    }

    return oResult;
  }

  /**
    * converts a string read from an XML-file into its corresponding enum value
    */
  public static int getInteger(String poValue)
  {
     int nValue = THORMONE_UNDEFINED;
     if( poValue.toLowerCase().equals("any") )
     {
      nValue = THORMONE_ANY;
     }
     else if( poValue.toLowerCase().equals("fear") )
     {
      nValue = THORMONE_FEAR;
     }
     else if( poValue.toLowerCase().equals("seek") )
     {
      nValue = THORMONE_SEEK;
     }
     else if( poValue.toLowerCase().equals("panic") )
     {
      nValue = THORMONE_PANIC;
     }
     else if( poValue.toLowerCase().equals("rage") )
     {
      nValue = THORMONE_RAGE;
     }
     return nValue;
  }
};
