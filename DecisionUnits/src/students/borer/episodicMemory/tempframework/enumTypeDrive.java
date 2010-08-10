// File enumTypeDrive.java
// May 09, 2006
//

// Belongs to package
package students.borer.episodicMemory.tempframework;

// Imports

/**
 *
 * This is the class description ...
 *
 * $Revision: 838 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2007-08-29 20:07:19 +0200 (Mi, 29 Aug 2007) $: Date of last commit
 *
 */
public class enumTypeDrive extends enumClass {
  /**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 10.08.2010, 17:52:12
	 */
	private static final long serialVersionUID = 6605254387804581794L;
public final static int TDRIVE_UNDEFINED  = -1;
  public final static int TDRIVE_ANY  = 0;
  public final static int TDRIVE_HUNGER  = 1;
  public final static int TDRIVE_THIRST  = 2;
  public final static int TDRIVE_SEEK    = 3;
  public final static int TDRIVE_PLAY    = 4;
  public final static int TDRIVE_SLEEP   = 5;

  /** 
    * converts the enum value to a human readable string
    */
  public static String getString(int pnEnum) {
    String oResult = enumClass.getString(pnEnum);

    switch(pnEnum) {
      case  TDRIVE_ANY:oResult = "[any]";break;
      case  TDRIVE_HUNGER:oResult = "[hunger]";break;
      case  TDRIVE_THIRST:oResult = "[thirst]";break;
      case  TDRIVE_SEEK:oResult = "[seek]";break;
      case  TDRIVE_PLAY:oResult = "[play]";break;
      case  TDRIVE_SLEEP:oResult = "[sleep]";break;

      default:oResult = "_unkown_"+pnEnum+"_";
    }

    return oResult;
  }

  /**
    * converts a string read from an XML-file into its corresponding enum value
    */
  public static int getInteger(String poValue)
  {
     int nValue = TDRIVE_UNDEFINED;
     if( poValue.toLowerCase().equals("any") )
     {
      nValue = TDRIVE_ANY;
     }
     else if( poValue.toLowerCase().equals("hunger") )
     {
      nValue = TDRIVE_HUNGER;
     }
     else if( poValue.toLowerCase().equals("thirst") )
     {
      nValue = TDRIVE_THIRST;
     }
     else if( poValue.toLowerCase().equals("seek") )
     {
      nValue = TDRIVE_SEEK;
     }
     else if( poValue.toLowerCase().equals("play") )
     {
      nValue = TDRIVE_PLAY;
     }
     else if( poValue.toLowerCase().equals("sleep") )
     {
      nValue = TDRIVE_SLEEP;
     }
     return nValue;
  }
};
