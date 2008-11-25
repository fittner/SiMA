// File enumTypeBrainAction.java
// September 19, 2006
//

// Belongs to package
package bw.utils.enums;

// Imports

/**
 *
 * Represents the external actions performed by the brain. 
 * (aka neuro-psychoanalytically inspired perception and decision making).
 *
 * $Revision: 811 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2007-08-28 15:36:37 +0200 (Di, 28 Aug 2007) $: Date of last commit
 *
 */
public class enumTypeBrainAction extends enumClass {
  public final static int TBRAINACTION_UNDEFINED             = -1;

  public final static int TBRAINACTION_NOTHING               = 0;

  public final static int TBRAINACTION_PROMENADE             = 10;
  public final static int TBRAINACTION_PROMENADERESET        = 11;

  public final static int TBRAINACTION_ATTACK                = 20;
  public final static int TBRAINACTION_FLEE                  = 21;
  public final static int TBRAINACTION_CALLHELP_DEFEND       = 22;

  public final static int TBRAINACTION_MOVETOFOOD            = 30;
  public final static int TBRAINACTION_EATFOOD               = 31;
  public final static int TBRAINACTION_CALLHELP_CRACKFOOD    = 32;
  public final static int TBRAINACTION_MOVETONEXTFOOD        = 33;

  public final static int TBRAINACTION_DANCE                 = 40;
  public final static int TBRAINACTION_DANCEWITH             = 41;
  public final static int TBRAINACTION_CALL_FOR_DANCE        = 42;
  public final static int TBRAINACTION_ANSWER_CALL_FOR_DANCE = 43;

  public final static int TBRAINACTION_SOCIAL_PRAISE         = 50;
  public final static int TBRAINACTION_SOCIAL_REPROACH       = 51;

  public final static int TBRAINACTION_INNERCONFLICT         = 60;

  public final static int TBRAINACTION_REFRESH_SLEEP         = 70;
  public final static int TBRAINACTION_REFRESH_REST          = 71;

  /** 
    * converts the enum value to a human readable string
    */
  public static String getString(int pnEnum) {
    String oResult = enumClass.getString(pnEnum);

    switch(pnEnum) {
      case  TBRAINACTION_UNDEFINED:oResult = "[undefined]";break;
      case  TBRAINACTION_NOTHING:oResult = "[nothing]";break;

      case  TBRAINACTION_PROMENADE:oResult = "[promenade]";break;
      case  TBRAINACTION_PROMENADERESET:oResult = "[promenade reset]";break;

      case  TBRAINACTION_ATTACK:oResult = "[attack]";break;
      case  TBRAINACTION_FLEE:oResult = "[flee]";break;
      case  TBRAINACTION_CALLHELP_DEFEND:oResult = "[call help - defend]";break;

      case  TBRAINACTION_MOVETOFOOD:oResult = "[move to food]";break;
      case  TBRAINACTION_EATFOOD:oResult = "[eat food]";break;
      case  TBRAINACTION_CALLHELP_CRACKFOOD:oResult = "[call help - crack food]";break;
      case  TBRAINACTION_MOVETONEXTFOOD:oResult = "[move to next food]";break;

      case  TBRAINACTION_DANCE:oResult = "[dance]";break;
      case  TBRAINACTION_DANCEWITH:oResult = "[dance with]";break;
      case  TBRAINACTION_CALL_FOR_DANCE:oResult = "[call for dance]";break;
      case  TBRAINACTION_ANSWER_CALL_FOR_DANCE:oResult = "[answer call for dance]";break;

      case  TBRAINACTION_SOCIAL_PRAISE:oResult = "[social praise]";break;
      case  TBRAINACTION_SOCIAL_REPROACH:oResult = "[social reproach]";break;

      case  TBRAINACTION_INNERCONFLICT:oResult = "[inner conflict]";break;

      case  TBRAINACTION_REFRESH_SLEEP:oResult = "[sleep]";break;
      case  TBRAINACTION_REFRESH_REST:oResult = "[rest]";break;

      default:oResult = "_unkown_"+pnEnum+"_";
    }

    return oResult;
  }
  /**
    * converts a string read from an XML-file into its corresponding enum value
    */
  public static int getInteger(String poValue)
  {
     int nValue = TBRAINACTION_UNDEFINED;

     if( poValue.toLowerCase().equals("nothing") ) {
       nValue = TBRAINACTION_NOTHING;

     } else if( poValue.toLowerCase().equals("promenade") ) {
       nValue = TBRAINACTION_PROMENADE;
     } else if( poValue.toLowerCase().equals("promenade_reset") ) {
       nValue = TBRAINACTION_PROMENADERESET;

     } else if( poValue.toLowerCase().equals("attack") ) {
       nValue = TBRAINACTION_ATTACK;
     } else if( poValue.toLowerCase().equals("flee") ) {
       nValue = TBRAINACTION_FLEE;
     } else if( poValue.toLowerCase().equals("callhelp_defend") ) {
       nValue = TBRAINACTION_CALLHELP_DEFEND;

     } else if( poValue.toLowerCase().equals("movetofood") ) {
       nValue = TBRAINACTION_MOVETOFOOD;
     } else if( poValue.toLowerCase().equals("eatfood") ) {
       nValue = TBRAINACTION_EATFOOD;
     } else if( poValue.toLowerCase().equals("callhelp_crackfood") ) {
       nValue = TBRAINACTION_CALLHELP_CRACKFOOD;
     } else if( poValue.toLowerCase().equals("movetonextfood") ) {
       nValue = TBRAINACTION_MOVETONEXTFOOD;

     } else if( poValue.toLowerCase().equals("dance") ) {
       nValue = TBRAINACTION_DANCE;
     } else if( poValue.toLowerCase().equals("dancewith") ) {
       nValue = TBRAINACTION_DANCEWITH;
     } else if( poValue.toLowerCase().equals("callfor_dance") ) {
       nValue = TBRAINACTION_CALL_FOR_DANCE;
     } else if( poValue.toLowerCase().equals("callfor_answerdance") ) {
       nValue = TBRAINACTION_ANSWER_CALL_FOR_DANCE;

     } else if( poValue.toLowerCase().equals("social_praise") ) {
       nValue = TBRAINACTION_SOCIAL_PRAISE;
     } else if( poValue.toLowerCase().equals("social_reproach") ) {
       nValue = TBRAINACTION_SOCIAL_REPROACH;

     } else if( poValue.toLowerCase().equals("inner_conflict") ) {
       nValue = TBRAINACTION_INNERCONFLICT;

     } else if( poValue.toLowerCase().equals("refresh_rest") ) {
       nValue = TBRAINACTION_REFRESH_SLEEP;
     } else if( poValue.toLowerCase().equals("refresh_sleep") ) {
       nValue = TBRAINACTION_REFRESH_REST;
     }

     return nValue;
  }
};  
