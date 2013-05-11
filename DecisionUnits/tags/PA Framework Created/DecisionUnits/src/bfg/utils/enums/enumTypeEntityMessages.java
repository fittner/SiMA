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

// File enumTypeEntityMessages.java
// September 19, 2006
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

public class enumTypeEntityMessages extends enumClass {
  public final static int TENTITYMESSAGE_UNDEFINED           = -1;
  public final static int TENTITYMESSAGE_NONE                = 0;
  public final static int TENTITYMESSAGE_DANCEWITHME         = 1;
  public final static int TENTITYMESSAGE_IDANCEWITHYOU       = 2;
  public final static int TENTITYMESSAGE_HELPME_CRACKFOOD    = 3;
  public final static int TENTITYMESSAGE_HELPME_DEFEND       = 4;
  public final static int TENTITYMESSAGE_IHELPYOU_CRACKFOOD  = 5;
  public final static int TENTITYMESSAGE_IHELPYOU_DEFEND     = 6;
  public final static int TENTITYMESSAGE_IPRAISEYOU          = 7;
  public final static int TENTITYMESSAGE_IREPROACHYOU        = 8;

  /** 
    * converts the enum value to a human readable string
    */
  public static String getString(int pnEnum) {
    String oResult = enumClass.getString(pnEnum);

    switch(pnEnum) {
      case  TENTITYMESSAGE_NONE:oResult = "[none]";break;
      case  TENTITYMESSAGE_DANCEWITHME:oResult = "[dance with me]";break;
      case  TENTITYMESSAGE_IDANCEWITHYOU:oResult = "[i dance with you]";break;
      case  TENTITYMESSAGE_HELPME_CRACKFOOD:oResult = "[help me - crack food]";break;
      case  TENTITYMESSAGE_HELPME_DEFEND:oResult = "[help me - defend]";break;
      case  TENTITYMESSAGE_IHELPYOU_CRACKFOOD:oResult = "[i help you - crack food]";break;
      case  TENTITYMESSAGE_IHELPYOU_DEFEND:oResult = "[i help you - defend]";break;
      case  TENTITYMESSAGE_IPRAISEYOU:oResult = "[i praise you]";break;
      case  TENTITYMESSAGE_IREPROACHYOU:oResult = "[i reproach you]";break;

      default:oResult = "_unkown_"+pnEnum+"_";
    }

    return oResult;
  }

  /**
    * converts a string read from an XML-file into its corresponding enum value
    */
  public static int getInteger(String poValue)
  {
     int nValue = TENTITYMESSAGE_UNDEFINED;
     if( poValue.toLowerCase().equals("none") )
     {
      nValue = TENTITYMESSAGE_NONE;
     }
     else if( poValue.toLowerCase().equals("dancewithme") )
     {
      nValue = TENTITYMESSAGE_DANCEWITHME;
     }
     else if( poValue.toLowerCase().equals("idancewithyou") )
     {
      nValue = TENTITYMESSAGE_IDANCEWITHYOU;
     }
     else if( poValue.toLowerCase().equals("helpme_crackfood") )
     {
      nValue = TENTITYMESSAGE_HELPME_CRACKFOOD;
     }
     else if( poValue.toLowerCase().equals("helpme_defend") )
     {
      nValue = TENTITYMESSAGE_HELPME_DEFEND;
     }
     else if( poValue.toLowerCase().equals("ihelpyou_crackfood") )
     {
      nValue = TENTITYMESSAGE_IHELPYOU_CRACKFOOD;
     }
     else if( poValue.toLowerCase().equals("ihelpyou_defend") )
     {
      nValue = TENTITYMESSAGE_IHELPYOU_DEFEND;
     }
     else if( poValue.toLowerCase().equals("ipraiseyou") )
     {
      nValue = TENTITYMESSAGE_IPRAISEYOU;
     }
     else if( poValue.toLowerCase().equals("ireproachyou") )
     {
      nValue = TENTITYMESSAGE_IREPROACHYOU;
     }
     return nValue;
  }
};
