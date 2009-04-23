/**
 * @author deutsch
 * 
 * Copied from the old bubble family game
 * 
 * CHKME keep this file?
 * @deprecated taken from old BFG
 * 
 * $Rev:: 1825                 $: Revision of last commit
 * $Author:: deutsch           $: Author of last commit
 * $Date:: 2009-01-27 19:59:35#$: Date of last commit
 */

// File enumTypeComplexEmotion.java
// August 03, 2006
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
public class enumTypeComplexEmotion extends enumClass {
  public final static int TCEMOTION_UNDEFINED   = -1;
  public final static int TCEMOTION_ANY         = 0;

  public final static int TCEMOTION_HOPE            = 5;
  public final static int TCEMOTION_JOY             = 6;
  public final static int TCEMOTION_DISSAPPOINTMENT = 7;
  public final static int TCEMOTION_GRATITUDE       = 8;  
  public final static int TCEMOTION_REPROACH        = 9;
  public final static int TCEMOTION_PRIDE           = 10;
  public final static int TCEMOTION_SHAME           = 11;

//  public final static int TCEMOTION_JOY         = 101;
  public final static int TCEMOTION_DOLOR       = 102;
  public final static int TCEMOTION_HATE        = 103;
  public final static int TCEMOTION_AFFECTION   = 104;
  public final static int TCEMOTION_ANTIPATHY   = 105;
  public final static int TCEMOTION_RESPECT     = 106;
  public final static int TCEMOTION_DISRESPECT  = 107;
  public final static int TCEMOTION_ADMIRATION  = 108;
  public final static int TCEMOTION_ENVIOUSNESS = 109;
  public final static int TCEMOTION_GUILT       = 110;
  public final static int TCEMOTION_PROUDNESS   = 111;
  public final static int TCEMOTION_LOVE        = 126;


  /** 
    * converts the enum value to a human readable string
    */
  public static String getString(int pnEnum) {
    String oResult = enumClass.getString(pnEnum);

    switch(pnEnum) {
      case  TCEMOTION_ANY:          oResult  = "[any]";break;

      case  TCEMOTION_HOPE:           oResult  = "[hope]";break;
      case  TCEMOTION_JOY:            oResult  = "[joy]";break;
      case  TCEMOTION_DISSAPPOINTMENT:oResult  = "[dissappointment]";break;
      case  TCEMOTION_GRATITUDE:      oResult  = "[gratitude]";break;
      case  TCEMOTION_REPROACH:       oResult  = "[reproach]";break;
      case  TCEMOTION_PRIDE:          oResult  = "[pride]";break;
      case  TCEMOTION_SHAME:          oResult  = "[shame]";break;
      case  TCEMOTION_DOLOR:          oResult  = "[dolor]";break;
      case  TCEMOTION_HATE:           oResult  = "[hate]";break;
      case  TCEMOTION_AFFECTION:      oResult  = "[affection]";break;
      case  TCEMOTION_ANTIPATHY:      oResult  = "[antipathy]";break;
      case  TCEMOTION_RESPECT:        oResult  = "[respect]";break;
      case  TCEMOTION_DISRESPECT:     oResult  = "[disrespect]";break;
      case  TCEMOTION_ADMIRATION:     oResult  = "[admiration]";break;
      case  TCEMOTION_ENVIOUSNESS:    oResult  = "[anviousness]";break;
      case  TCEMOTION_GUILT:          oResult  = "[guilt]";break;
      case  TCEMOTION_PROUDNESS:      oResult  = "[proudness]";break;
      case  TCEMOTION_LOVE:           oResult  = "[love]";break;

      default:oResult = "_unkown_"+pnEnum+"_";
    }

    return oResult;
  }

  /**
    * converts a string read from an XML-file into its corresponding enum value
    */
  public static int getInteger(String poValue)
  {
     int nValue = TCEMOTION_UNDEFINED;
     if( poValue.toLowerCase().equals("any") ) {
      nValue = TCEMOTION_ANY;
     } else if( poValue.toLowerCase().equals("hope") ) {
       nValue = TCEMOTION_HOPE;
     } else if( poValue.toLowerCase().equals("joy") ) {
       nValue = TCEMOTION_JOY;
     } else if( poValue.toLowerCase().equals("dissappointment") ) {
       nValue = TCEMOTION_DISSAPPOINTMENT;
     } else if( poValue.toLowerCase().equals("gratitude") ) {
       nValue = TCEMOTION_GRATITUDE;
     } else if( poValue.toLowerCase().equals("reproach") ) {
       nValue = TCEMOTION_REPROACH;
     } else if( poValue.toLowerCase().equals("pride") ) {
       nValue = TCEMOTION_PRIDE;
     } else if( poValue.toLowerCase().equals("shame") ) {
       nValue = TCEMOTION_SHAME;
     } else if( poValue.toLowerCase().equals("dolor") ) {
       nValue = TCEMOTION_DOLOR;
     } else if( poValue.toLowerCase().equals("hate") ) {
       nValue = TCEMOTION_HATE;
     } else if( poValue.toLowerCase().equals("affection") ) {
       nValue = TCEMOTION_AFFECTION;
     } else if( poValue.toLowerCase().equals("antipathy") ) {
       nValue = TCEMOTION_ANTIPATHY;
     } else if( poValue.toLowerCase().equals("respect") ) {
       nValue = TCEMOTION_RESPECT;
     } else if( poValue.toLowerCase().equals("disrespect") ) {
       nValue = TCEMOTION_DISRESPECT;
     } else if( poValue.toLowerCase().equals("admiration") ) {
       nValue = TCEMOTION_ADMIRATION;
     } else if( poValue.toLowerCase().equals("enviousness") ) {
       nValue = TCEMOTION_ENVIOUSNESS;
     } else if( poValue.toLowerCase().equals("guilt") ) {
       nValue = TCEMOTION_GUILT;
     } else if( poValue.toLowerCase().equals("proudness") ) {
       nValue = TCEMOTION_PROUDNESS;
     } else if( poValue.toLowerCase().equals("love") ) {
       nValue = TCEMOTION_LOVE;
     } 

     return nValue;
  }
};
