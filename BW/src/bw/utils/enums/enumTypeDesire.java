/**
 * @author deutsch
 * 
 * Copied from the old bubble family game
 * 
 * CHKME keep this file?
 * @deprecated taken from old BFG
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */

// File enumTypeDesire.java
// September 20, 2006
//

// Belongs to package
package bw.utils.enums;

// Imports

/**
 *
 * This is the class description ...
 *
 * @deprecated
 * 
 * $Revision$:  Revision of last commit
 * $Author$: Author of last commit
 * $Date$: Date of last commit
 *
 */
public class enumTypeDesire extends enumClass {
  public final static int TDESIRE_UNDEFINED        = -1;
  public final static int TDESIRE_ALL              = 0;
  public final static int TDESIRE_DANCEWITH        = 1;
  public final static int TDESIRE_FOODCOOPERATION  = 100;
  public final static int TDESIRE_HELPFOODCOOPERATION  = 101;
  public final static int TDESIRE_FOODX            = 2;
  public final static int TDESIRE_SOCIALACCEPTANCE = 4;

  /** 
    * converts the enum value to a human readable string
    */
  public static String getString(int pnEnum) {
    String oResult = enumClass.getString(pnEnum);

    switch(pnEnum) {
      case TDESIRE_UNDEFINED:        oResult = "[undefined]";break;
      case TDESIRE_ALL:              oResult = "[all]";break;
      case TDESIRE_DANCEWITH:        oResult = "[dance with]";break;
      case TDESIRE_FOODCOOPERATION:  oResult = "[cooperation for food]";break;
      case TDESIRE_HELPFOODCOOPERATION:  oResult = "[help other for food]";break;
      case TDESIRE_FOODX:            oResult = "[want food x]";break;
      case TDESIRE_SOCIALACCEPTANCE: oResult = "[social acceptance]";break;

      default:oResult = "_unkown_"+pnEnum+"_";
    }

    return oResult;
  }

  /**
    * converts a string read from an XML-file into its corresponding enum value
    */
  public static int getInteger(String poValue)
  {
     int nValue = TDESIRE_UNDEFINED;

     if( poValue.toLowerCase().equals("all") ) {
       nValue = TDESIRE_ALL;
     } else if( poValue.equals("dancewith") ) {
       nValue = TDESIRE_DANCEWITH;
     } else if( poValue.equals("foodcooperation") ) {
       nValue = TDESIRE_FOODCOOPERATION;
     } else if( poValue.equals("helpfoodcooperation") ) {
       nValue = TDESIRE_HELPFOODCOOPERATION;
     } else if( poValue.toLowerCase().equals("foodx") ) {
       nValue = TDESIRE_FOODX;
     } else if( poValue.toLowerCase().equals("socialacceptance") ) {
       nValue = TDESIRE_SOCIALACCEPTANCE;
     }
     return nValue;
  }
};
