/**
 * @author deutsch
 * 
 * Copied from the old bubble family game
 * 
 * 
 *  taken from old BFG
 * 
 * $Rev:: 1825                 $: Revision of last commit
 * $Author:: deutsch           $: Author of last commit
 * $Date:: 2009-01-27 19:59:35#$: Date of last commit
 */

// File enumTypeSpecialCommand.java
// June 26, 2006
//

// Belongs to package
package bfg.utils.enumsOld;

import bfg.utils.enums.enumClass;

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
public class enumTypeSpecialCommand extends enumClass {
  public final static int TSPECIALCOMMAND_UNDEFINED      = -1;
  public final static int TSPECIALCOMMAND_NONE           = 0;
  public final static int TSPECIALCOMMAND_ATTACK         = 1;
  public final static int TSPECIALCOMMAND_EAT            = 2;
  public final static int TSPECIALCOMMAND_DANCE          = 3;
  public final static int TSPECIALCOMMAND_DANCEWITH      = 4;
  public final static int TSPECIALCOMMAND_SOCIALPRAISE   = 5;
  public final static int TSPECIALCOMMAND_SOCIALREPROACH = 6;
  public final static int TSPECIALCOMMAND_TALK           = 7;
  public final static int TSPECIALCOMMAND_DECISIONCONFLICT = 8;
  public final static int TSPECIALCOMMAND_SLEEP          = 9;
  public final static int TSPECIALCOMMAND_REST           = 10;

  /** 
    * converts the enum value to a human readable string
    */
  public static String getString(int pnEnum) {
    String oResult = enumClass.getString(pnEnum);

    switch(pnEnum) {
      case TSPECIALCOMMAND_UNDEFINED:oResult = "[undefined]";break;
      case TSPECIALCOMMAND_NONE:oResult = "[none]";break;
      case TSPECIALCOMMAND_ATTACK:oResult = "[attack]";break;
      case TSPECIALCOMMAND_EAT:oResult = "[eat]";break;
      case TSPECIALCOMMAND_DANCE:oResult = "[dance]";break;
      case TSPECIALCOMMAND_DANCEWITH:oResult = "[dancewith]";break;
      case TSPECIALCOMMAND_SOCIALPRAISE:oResult = "[praise]";break;
      case TSPECIALCOMMAND_SOCIALREPROACH:oResult = "[reproach]";break;
      case TSPECIALCOMMAND_TALK:oResult = "[talk]";break;
      case TSPECIALCOMMAND_DECISIONCONFLICT:oResult = "[conflict]";break;
      case TSPECIALCOMMAND_SLEEP:oResult = "[sleep]";break;
      case TSPECIALCOMMAND_REST:oResult = "[rest]";break;

      default:oResult = "_unkown_"+pnEnum+"_";
    }

    return oResult;
  }
  /**
    * converts a string read from an XML-file into its corresponding enum value
    */
  public static int getInteger(String poValue)
  {
     int nValue = TSPECIALCOMMAND_UNDEFINED;
     if( poValue.toLowerCase().equals("none") )
     {
      nValue = TSPECIALCOMMAND_NONE;
     }
     else if( poValue.toLowerCase().equals("attack") )
     {
      nValue = TSPECIALCOMMAND_ATTACK;
     }
     else if( poValue.toLowerCase().equals("eat") )
     {
      nValue = TSPECIALCOMMAND_EAT;
     }
     else if( poValue.toLowerCase().equals("dance") )
     {
      nValue = TSPECIALCOMMAND_DANCE;
     }
     else if( poValue.toLowerCase().equals("dancewith") )
     {
      nValue = TSPECIALCOMMAND_DANCEWITH;
     }
     else if( poValue.toLowerCase().equals("praise") )
     {
      nValue = TSPECIALCOMMAND_SOCIALPRAISE;
     }
     else if( poValue.toLowerCase().equals("reproach") )
     {
      nValue = TSPECIALCOMMAND_SOCIALREPROACH;
     }
     else if( poValue.toLowerCase().equals("talk") )
     {
      nValue = TSPECIALCOMMAND_TALK;
     }
     else if( poValue.toLowerCase().equals("conflict") )
     {
      nValue = TSPECIALCOMMAND_DECISIONCONFLICT;
     }
     else if( poValue.toLowerCase().equals("sleep") )
     {
      nValue = TSPECIALCOMMAND_SLEEP;
     }
     else if( poValue.toLowerCase().equals("rest") )
     {
      nValue = TSPECIALCOMMAND_REST;
     }
     return nValue;
  }
};
