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

// File enumGUIElement.java
// Belongs to package
package bfg.utils.enums;

// Imports

/**
 *
 * Represents the possible basic actions of an entity.
 *
 * @deprecated
 *
 $Revision: 1825 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2009-01-27 19:59:35 +0100 (Di, 27 Jän 2009) $: Date of last commit
 *
 */

public class enumGUIElement extends enumClass {
  public final static int TGUI_UNDEFINED       = 0;

  public final static int TGUI_SETUPSELECTION  = 1;
  public final static int TGUI_SIMCONTROL      = 2;
  public final static int TGUI_VISUALIZATION   = 4;
  public final static int TGUI_PHYSICSSERVER   = 8;
  public final static int TGUI_ENTITIES        = 16;

  public final static int TGUI_SIMRUN          =  TGUI_SIMCONTROL +
                                                  TGUI_VISUALIZATION +
                                                  TGUI_PHYSICSSERVER +
                                                  TGUI_ENTITIES;

  /** 
    * converts the enum value to a human readable string
    */
  public static String getString(int pnEnum) {
    String oResult = enumClass.getString(pnEnum);

    switch(pnEnum) {
      case  TGUI_UNDEFINED:oResult      = "[undefined]";break;
      case  TGUI_SETUPSELECTION:oResult = "[setup selection]";break;
      case  TGUI_SIMCONTROL:oResult     = "[sim control]";break;
      case  TGUI_VISUALIZATION:oResult  = "[visualization]";break;
      case  TGUI_PHYSICSSERVER:oResult  = "[physics server]";break;
      case  TGUI_ENTITIES:oResult       = "[entitiy details]";break;
      case  TGUI_SIMRUN:oResult         = "[sim run]";break;

      default:oResult = "_unkown_"+pnEnum+"_";
    }

    return oResult;
  }
  /**
    * converts a string read from an XML-file into its corresponding enum value
    */
  public static int getInteger(String poValue)
  {
     int nValue = TGUI_UNDEFINED;

     if( poValue.toLowerCase().equals("setupselection") ) {
       nValue = TGUI_SETUPSELECTION;
     } else if( poValue.toLowerCase().equals("simcontrol") ) {
       nValue = TGUI_SIMCONTROL;
     } else if( poValue.toLowerCase().equals("visualization") ) {
       nValue = TGUI_VISUALIZATION;
     } else if( poValue.toLowerCase().equals("physicsserver") ) {
       nValue = TGUI_PHYSICSSERVER;
     } else if( poValue.toLowerCase().equals("entitiydetails") ) {
       nValue = TGUI_ENTITIES;
     } else if( poValue.toLowerCase().equals("simrun") ) {
       nValue = TGUI_SIMRUN;
     }

     return nValue;
  }
};  
