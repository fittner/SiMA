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

// File enumTypePositionMethod.java
// July 24, 2006
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
public class enumTypePositionMethod extends enumClass {
  public final static int TPOSITIONMETHOD_UNDEFINED = -1;

  public final static int TPOSITIONMETHOD_EXACT    = 0;
  public final static int TPOSITIONMETHOD_RANDOM   = 1;
  public final static int TPOSITIONMETHOD_CLOSE    = 2;
  public final static int TPOSITIONMETHOD_NEXTFREE = 3;

  /** 
    * converts the enum value to a human readable string
    */
  public static String getString(int pnEnum) {
    String oResult = enumClass.getString(pnEnum);

    switch(pnEnum) {
      case TPOSITIONMETHOD_UNDEFINED:oResult = "[undefined]";break;
      case  TPOSITIONMETHOD_EXACT:oResult = "[exact]";break;
      case  TPOSITIONMETHOD_RANDOM:oResult = "[random]";break;
      case  TPOSITIONMETHOD_CLOSE:oResult = "[close]";break;
      case  TPOSITIONMETHOD_NEXTFREE:oResult = "[nextfree]";break;
    }

    return oResult;
  }

  /**
    * converts a string read from an XML-file into its corresponding enum value
    */
  public static int getInteger(String poValue)
  {
     int nValue = TPOSITIONMETHOD_UNDEFINED;
     if( poValue.toLowerCase().equals("exact") )
     {
      nValue = TPOSITIONMETHOD_EXACT;
     }
     else if( poValue.toLowerCase().equals("random") )
     {
      nValue = TPOSITIONMETHOD_RANDOM;
     }
     else if( poValue.toLowerCase().equals("close") )
     {
      nValue = TPOSITIONMETHOD_CLOSE;
     }
     else if( poValue.toLowerCase().equals("nextfree") )
     {
      nValue = TPOSITIONMETHOD_NEXTFREE;
     }

     return nValue;
  }
};
