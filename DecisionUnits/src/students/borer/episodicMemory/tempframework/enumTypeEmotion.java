// File enumTypeEmotion.java
// May 09, 2006
//

// Belongs to package
package students.borer.episodicMemory.tempframework;

// Imports

/**
 *
 * This is the class description ...
 *
 * $Revision: 688 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2007-07-12 15:51:25 +0200 (Do, 12 Jul 2007) $: Date of last commit
 *
 */
public class enumTypeEmotion extends enumClass {
  /**
	 * @author deutsch
	 * 10.08.2010, 17:51:55
	 */
	private static final long serialVersionUID = 2827015607716475631L;
public final static int TEMOTION_UNDEFINED  = -1;
  public final static int TEMOTION_ANY        = 0;
  public final static int TEMOTION_FEAR       = 1;
  public final static int TEMOTION_ANGER      = 2;
  public final static int TEMOTION_LUST       = 3;
  public final static int TEMOTION_PANIC      = 4;
  public final static int TEMOTION_RAGE       = 5;
  public final static int TEMOTION_SEEKING    = 6;


  /** 
    * converts the enum value to a human readable string
    */
  public static String getString(int pnEnum) {
    String oResult = enumClass.getString(pnEnum);

    switch(pnEnum) {
      case  TEMOTION_ANY  :oResult = "[any]";break;
      case  TEMOTION_FEAR :oResult = "[fear]";break;
      case  TEMOTION_ANGER:oResult = "[anger]";break;
      case  TEMOTION_LUST :oResult = "[lust]";break;
      case  TEMOTION_PANIC:oResult = "[panic]";break;
      case  TEMOTION_RAGE :oResult = "[rage]";break;
      case  TEMOTION_SEEKING :oResult = "[seeking]";break;

      default:oResult = "_unkown_"+pnEnum+"_";
    }

    return oResult;
  }

  /**
    * converts a string read from an XML-file into its corresponding enum value
    */
  public static int getInteger(String poValue)
  {
     int nValue = TEMOTION_UNDEFINED;
     if( poValue.toLowerCase().equals("any") )
     {
      nValue = TEMOTION_ANY;
     }
     else if( poValue.toLowerCase().equals("fear") )
     {
      nValue = TEMOTION_FEAR;
     }
     else if( poValue.toLowerCase().equals("anger") )
     {
      nValue = TEMOTION_ANGER;
     }
     else if( poValue.toLowerCase().equals("lust") )
     {
      nValue = TEMOTION_LUST;
     }
     else if( poValue.toLowerCase().equals("panic") )
     {
      nValue = TEMOTION_PANIC;
     }
     else if( poValue.toLowerCase().equals("rage") )
     {
      nValue = TEMOTION_RAGE;
     }
     else if( poValue.toLowerCase().equals("seeking") )
     {
      nValue = TEMOTION_SEEKING;
     }
     return nValue;
  }
};
