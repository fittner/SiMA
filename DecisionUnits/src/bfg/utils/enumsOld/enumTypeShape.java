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

// File enumTypeShape.java
// July 18, 2006
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
public class enumTypeShape extends enumClass {
  public final static int TSHAPE_UNDEFINED    = -1;
  public final static int TSHAPE_CIRCLE       = 0;
  public final static int TSHAPE_SQUARE       = 1;
  public final static int TSHAPE_RECTANGLE    = 2;
  public final static int TSHAPE_TRIANGLE     = 3;
  public final static int TSHAPE_POLYGON      = 4;


  /** 
    * converts the enum value to a human readable string
    */
  public static String getString(int pnEnum) {
    String oResult = enumClass.getString(pnEnum);

    switch(pnEnum) {
      case  TSHAPE_UNDEFINED:oResult = "[undefined]";break;
      case  TSHAPE_CIRCLE   :oResult = "[circle]";break;
      case  TSHAPE_SQUARE   :oResult = "[square]";break;
      case  TSHAPE_RECTANGLE:oResult = "[rectangle]";break;
      case  TSHAPE_TRIANGLE :oResult = "[triangle]";break;
      case  TSHAPE_POLYGON  :oResult = "[polygon]";break;

      default:oResult = "_unkown_"+pnEnum+"_";
    }

    return oResult;
  }

  /**
    * converts a string read from an XML-file into its corresponding enum value
    */
  public static int getInteger(String poValue)
  {
     int nValue = TSHAPE_UNDEFINED;
     if( poValue.toLowerCase().equals("circle") )
     {
      nValue = TSHAPE_CIRCLE;
     }
     else if( poValue.toLowerCase().equals("square") )
     {
      nValue = TSHAPE_SQUARE;
     }
     else if( poValue.toLowerCase().equals("rectangle") )
     {
      nValue = TSHAPE_RECTANGLE;
     }
     else if( poValue.toLowerCase().equals("triangle") )
     {
      nValue = TSHAPE_TRIANGLE;
     }
     else if( poValue.toLowerCase().equals("polygon") )
     {
      nValue = TSHAPE_POLYGON;
     }
     return nValue;
}  }
