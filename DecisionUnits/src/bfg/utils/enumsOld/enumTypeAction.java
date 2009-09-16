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

// File enumTypeAction.java
// June 01, 2006
//

// Belongs to package
package bfg.utils.enumsOld;

// Imports

/**
 *
 * Represents the possible basic actions of an entity.
 *
 * @deprecated
 * 
 * $Revision: 1825 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2009-01-27 19:59:35 +0100 (Di, 27 Jän 2009) $: Date of last commit
 *
 */

public class enumTypeAction extends enumClass {
  public final static int TACTION_UNDEFINED    = -1;

  public final static int TACTION_NOTHING      = 0;
  public final static int TACTION_PROMENADE    = 1;

  public final static int TACTION_ATTACK       = 10;
  public final static int TACTION_FLEE         = 11;

  public final static int TACTION_CONSUME      = 20;
  public final static int TACTION_REFRESH      = 21;

  public final static int TACTION_MOVETOENTITY = 30;
  public final static int TACTION_MOVETORELPOS = 31;
  public final static int TACTION_MOVEAHEAD    = 32;

  public final static int TACTION_ROTATELEFT   = 40;
  public final static int TACTION_ROTATERIGHT  = 41;

  /** 
    * converts the enum value to a human readable string
    */
  public static String getString(int pnEnum) {
    String oResult = enumClass.getString(pnEnum);

    switch(pnEnum) {
      case  TACTION_UNDEFINED:oResult = "[undefined]";break;
      case  TACTION_NOTHING:oResult = "nothing";break;
      case  TACTION_PROMENADE:oResult = "promenade";break;
      case  TACTION_ATTACK:oResult = "attack";break;
      case  TACTION_FLEE:oResult = "flee";break;
      case  TACTION_CONSUME:oResult = "consume";break;
      case  TACTION_REFRESH:oResult = "refresh";break;
      case  TACTION_MOVETOENTITY:oResult = "movetoentity";break;
      case  TACTION_MOVETORELPOS:oResult = "movetorelpos";break;
      case  TACTION_MOVEAHEAD:oResult = "moveahead";break;
      case  TACTION_ROTATELEFT:oResult = "rotateleft";break;
      case  TACTION_ROTATERIGHT:oResult = "rotateright";break;

      default:oResult = "_unkown_"+pnEnum+"_";
    }

    return oResult;
  }
  /**
    * converts a string read from an XML-file into its corresponding enum value
    */
  public static int getInteger(String poValue)
  {
     int nValue = TACTION_UNDEFINED;

     if( poValue.toLowerCase().equals("nothing") ) {
       nValue = TACTION_NOTHING;
     } else if( poValue.toLowerCase().equals("promenade") ) {
       nValue = TACTION_PROMENADE;
     } else if( poValue.toLowerCase().equals("attack") ) {
       nValue = TACTION_ATTACK;
     } else if( poValue.toLowerCase().equals("flee") ) {
       nValue = TACTION_FLEE;
     } else if( poValue.toLowerCase().equals("consume") ) {
       nValue = TACTION_CONSUME;
     } else if( poValue.toLowerCase().equals("refresh") ) {
       nValue = TACTION_REFRESH;
     } else if( poValue.toLowerCase().equals("movetoentity") ) {
       nValue = TACTION_MOVETOENTITY;
     } else if( poValue.toLowerCase().equals("movetorelpos") ) {
       nValue = TACTION_MOVETORELPOS;
     } else if( poValue.toLowerCase().equals("moveahead") ) {
       nValue = TACTION_MOVEAHEAD;
     } else if( poValue.toLowerCase().equals("rotateleft") ) {
       nValue = TACTION_ROTATELEFT;
     } else if( poValue.toLowerCase().equals("rotateright") ) {
       nValue = TACTION_ROTATERIGHT;
     }

     return nValue;
  }
};  
