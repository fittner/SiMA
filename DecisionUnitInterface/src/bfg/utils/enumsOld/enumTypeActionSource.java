/**
 * @author deutsch
 * 
 * Copied from the old bubble family game
 * 
 *  taken from old BFG
 * 
 * $Rev:: 1825                 $: Revision of last commit
 * $Author:: deutsch           $: Author of last commit
 * $Date:: 2009-01-27 19:59:35#$: Date of last commit
 */

// File enumTypeActionSource.java
// July 13, 2007
//

// Belongs to package
package bfg.utils.enumsOld;

import bfg.utils.enums.enumClass;

// Imports

// Class definition
/**
 *  @deprecated
 * 
 */
public class enumTypeActionSource extends enumClass {
  public final static int TACTIONSOURCE_UNDEFINED    = -1;

  public final static int TACTIONSOURCE_NOTHING = 0;
  public final static int TACTIONSOURCE_OUTER   = 1;
  public final static int TACTIONSOURCE_INNER  = 2;

  /** 
    * converts the enum value to a human readable string
    */
  public static String getString(int pnEnum) {
    String oResult = enumClass.getString(pnEnum);

    switch(pnEnum) {
      case  TACTIONSOURCE_UNDEFINED:oResult = "[undefined]";break;
      case  TACTIONSOURCE_NOTHING:oResult = "nothing";break;
      case  TACTIONSOURCE_OUTER:oResult = "outer";break;
      case  TACTIONSOURCE_INNER:oResult = "inner";break;

      default:oResult = "_unkown_"+pnEnum+"_";
    }
    return oResult;
  }
  /**
    * converts a string read from an XML-file into its corresponding enum value
    */
  public static int getInteger(String poValue)
  {
     int nValue = TACTIONSOURCE_UNDEFINED;

     if( poValue.toLowerCase().equals("nothing") ) {
       nValue = TACTIONSOURCE_NOTHING;
     } else if( poValue.toLowerCase().equals("outer") ) {
       nValue = TACTIONSOURCE_OUTER;
     } else if( poValue.toLowerCase().equals("inner") ) {
       nValue = TACTIONSOURCE_INNER;
     }
     return nValue;
  }
};
