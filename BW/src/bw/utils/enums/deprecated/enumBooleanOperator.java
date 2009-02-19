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

// File enumBooleanOperator.java
// May 09, 2006
//

// Belongs to package
package bw.utils.enums.deprecated;

// Imports

/**
 *
 * Represents the boolean operators "and" and "or".
 *
 * @deprecated
 *
 * $Revision: 1825 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2009-01-27 19:59:35 +0100 (Di, 27 Jän 2009) $: Date of last commit
 *
 */
public class enumBooleanOperator extends enumClass {
  public final static int TBOOLOP_UNDEFINED  = -1; 
  public final static int TBOOLOP_AND        = 0;
  public final static int TBOOLOP_OR         = 1;

  /** 
    * converts the enum value to a human readable string
    */
  public static String getString(int pnEnum) {
    String oResult = enumClass.getString(pnEnum);

    switch(pnEnum) {
      case -1:oResult = "[undefined]";break;
      case  0:oResult = "[and]";break;
      case  1:oResult = "[or]";break;

      default:oResult = "_unkown_"+pnEnum+"_";
    }
    return oResult;
  }

  /**
    * converts a string read from an XML-file into its corresponding enum value
    */
  public static int getInteger(String poValue)
  {
     int nValue = TBOOLOP_UNDEFINED;
     if( poValue.toLowerCase().equals("and") )
     {
      nValue = TBOOLOP_AND;
     }
     else if( poValue.toLowerCase().equals("or") )
     {
      nValue = TBOOLOP_OR;
     }
     return nValue;
  }
};


