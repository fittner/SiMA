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

// File enumTypeCount.java
// May 09, 2006
//

// Belongs to package
package bfg.utils.enumsOld;

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
public class enumTypeCount extends enumClass {
  public final static int TCOUNT_UNDEFINED  = -1;
  public final static int TCOUNT_NONE = 0;
  public final static int TCOUNT_ONE   = 1;
  public final static int TCOUNT_TWO   = 2;
  public final static int TCOUNT_MANY  = 3;

  /** 
    * converts the enum value to a human readable string
    */
  public static String getString(int pnEnum) {
    String oResult = enumClass.getString(pnEnum);

    switch(pnEnum) {
      case TCOUNT_UNDEFINED:oResult = "[undefined]";break;
      case TCOUNT_NONE:oResult = "[none]";break;
      case TCOUNT_ONE:oResult = "[one]";break;
      case TCOUNT_TWO:oResult = "[two]";break;
      case TCOUNT_MANY:oResult = "[many]";break;

      default:oResult = "_unkown_"+pnEnum+"_";
    }

    return oResult;
  }

  /**
    * converts a string read from an XML-file into its corresponding enum value
    */
  public static int getInteger(String poValue)
  {
     int nValue = TCOUNT_UNDEFINED;

//     Engine.log.println("enumTypeCount:getInteger - "+poValue +" -> " + poValue.toLowerCase() );

     if( poValue.toLowerCase().equals("none") )
     {
//      Engine.log.println("isNone!" );
      nValue = TCOUNT_NONE;
     }
     else if( poValue.equals("1") )
     {
//      Engine.log.println("isOne!" );
      nValue = TCOUNT_ONE;
     }
     else if( poValue.equals("2") )
     {
//      Engine.log.println("isTwo!" );
      nValue = TCOUNT_TWO;
     }
     else if( poValue.toLowerCase().equals("many") )
     {
//      Engine.log.println("isMany!" );
      nValue = TCOUNT_MANY;
     }
     return nValue;
  }
};
