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

// File enumTypeTrippleState.java
// May 09, 2006
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
public class enumTypeTrippleState extends enumClass {
  public final static int TTRIPPLE_UNDEFINED = -1;
  public final static int TTRIPPLE_FALSE     = 0;
  public final static int TTRIPPLE_TRUE      = 1;

  /** 
    * converts the enum value to a human readable string
    */
  public static String getString(int pnEnum) {
    String oResult = enumClass.getString(pnEnum);

    switch(pnEnum) {
      case TTRIPPLE_UNDEFINED:oResult = "[undefined]";break;
      case  TTRIPPLE_FALSE:oResult = "false";break;
      case  TTRIPPLE_TRUE:oResult = "true";break;

      default:oResult = "_unkown_"+pnEnum+"_";
    }

    return oResult;
  }
  /**
    * converts a string read from an XML-file into its corresponding enum value
    */
  public static int getInteger(String poValue)
  {
     int nValue = TTRIPPLE_UNDEFINED;
     if( poValue.toLowerCase().equals("false") )
     {
      nValue = TTRIPPLE_FALSE;
     }
     else if( poValue.toLowerCase().equals("true") )
     {
      nValue = TTRIPPLE_TRUE;
     }
     return nValue;
  }

  /**
    * converts a string read from an XML-file into its corresponding enum value
    */
  public static int getInteger(boolean pnValue)
  {
     int nResult = TTRIPPLE_FALSE;

     if( pnValue )
     {
       nResult = TTRIPPLE_TRUE;
     }
     return nResult;
  }

  /**
    * converts a string read from an XML-file into its corresponding boolean value
    */
  public static boolean getBoolean(String poValue)
  {
    boolean oResult = false;
    if( poValue.toLowerCase().equals("false") )
    {
      oResult = false;
    }
    else if( poValue.toLowerCase().equals("true") )
    {
      oResult = true;
    }
    return oResult;
  }
};      
