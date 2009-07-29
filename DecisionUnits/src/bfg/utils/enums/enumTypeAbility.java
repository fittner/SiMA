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

// Belongs to package
package bfg.utils.enums;

// Imports

/**
 *  @deprecated
 * 
 */
public class enumTypeAbility extends enumClass {
  public final static int TABILITY_UNDEFINED    = -1;
  public final static int TABILITY_ANY          = -2;

  public final static int TABILITY_SWIM       = 0;
  public final static int TABILITY_CLIMB      = 1;
  public final static int TABILITY_DANCE      = 2;
  public final static int TABILITY_RUN        = 3;
  public final static int TABILITY_JUMP       = 4;
  public final static int TABILITY_FLY        = 5;
  public final static int TABILITY_FIGHT      = 6;
  public final static int TABILITY_SING       = 7;
  public final static int TABILITY_COOK       = 8;
  public final static int TABILITY_DIVE       = 9;


  /** 
    * converts the enum value to a human readable string
    */
  public static String getString(int pnEnum) {
    String oResult = enumClass.getString(pnEnum);

    switch(pnEnum) {
      case  TABILITY_UNDEFINED:oResult = "[undefined]";break;

      case  TABILITY_ANY:oResult = "[any]";break;

      case  TABILITY_SWIM:oResult = "[swim]";break;
      case  TABILITY_CLIMB:oResult = "[climb]";break;
      case  TABILITY_DANCE:oResult = "[dance]";break;
      case  TABILITY_RUN:oResult = "[run]";break;
      case  TABILITY_JUMP:oResult = "[jump]";break;
      case  TABILITY_FLY:oResult = "[fly]";break;
      case  TABILITY_FIGHT:oResult = "[fight]";break;
      case  TABILITY_COOK:oResult = "[cook]";break;
      case  TABILITY_DIVE:oResult = "[dive]";break;

      default:oResult = "_unkown_"+pnEnum+"_";
    }

    return oResult;
  }
  /**
   * @return every possible ability
   */
  public static int[] getAllAbilities() {
	  return new int[]{TABILITY_SWIM, TABILITY_CLIMB, TABILITY_DANCE, TABILITY_RUN, TABILITY_JUMP, 
			  TABILITY_FLY, TABILITY_FIGHT, TABILITY_SING, TABILITY_COOK, TABILITY_DIVE};
  }
  /**
    * converts a string read from an XML-file into its corresponding enum value
    */
  public static int getInteger(String poValue)
  {
     int nValue = TABILITY_UNDEFINED;

     if( poValue.toLowerCase().equals("any"))
     {
      nValue = TABILITY_ANY;
     }
     else if( poValue.toLowerCase().equals("swim") )
     {
      nValue = TABILITY_SWIM;
     }
     else if( poValue.toLowerCase().equals("climb") )
     {
      nValue = TABILITY_CLIMB;
     }
     else if( poValue.toLowerCase().equals("dance") )
     {
      nValue = TABILITY_DANCE;
     }
     else if( poValue.toLowerCase().equals("run") )
     {
      nValue = TABILITY_RUN;
     }
     else if( poValue.toLowerCase().equals("jump") )
     {
      nValue = TABILITY_JUMP;
     }
     else if( poValue.toLowerCase().equals("fly") )
     {
      nValue = TABILITY_FLY;
     }
     else if( poValue.toLowerCase().equals("fight") )
     {
      nValue = TABILITY_FIGHT;
     }
     else if( poValue.toLowerCase().equals("cook") )
     {
      nValue = TABILITY_COOK;
     }
     else if( poValue.toLowerCase().equals("dive") )
     {
      nValue = TABILITY_DIVE;
     }
     return nValue;
  }
  
};
