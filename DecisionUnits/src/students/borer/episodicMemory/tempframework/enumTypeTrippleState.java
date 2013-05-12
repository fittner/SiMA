// File enumTypeTrippleState.java
// May 09, 2006
//

// Belongs to package
package students.borer.episodicMemory.tempframework;

// Imports

/**
 *
 * This is the class description ...
 *
 * $Revision: 580 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2007-05-31 18:07:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
 *
 */
public class enumTypeTrippleState extends enumClass {
  /**
	 * @author deutsch
	 * 10.08.2010, 17:52:30
	 */
	private static final long serialVersionUID = -4397988151211420038L;
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
