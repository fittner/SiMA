// File enumTypeBrainInnerAction.java
// May 21, 2007
//

// Belongs to package
package students.borer.episodicMemory.tempframework;

// Imports

/**
 *
 * Represents the internal actions performed by the brain. (aka 
 * neuro-psychoanalytically inspired perception and decision making). 
 * These actions are mainly to release hormones.
 *
 * $Revision: 1063 $:  Revision of last commit
 * $Author: riediger $: Author of last commit
 * $Date: 2008-05-30 16:58:27 +0200 (Fr, 30 Mai 2008) $: Date of last commit
 *
 */
public class enumTypeBrainInnerAction extends enumClass {
  /**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 10.08.2010, 17:51:44
	 */
	private static final long serialVersionUID = -6259321744707060669L;
public final static int TBRAININNERACTION_UNDEFINED             = -1;
  public final static int TBRAININNERACTION_NOTHING               = 0;

  public final static int TBRAININNERACTION_HORMONE_FEAR          = 10;
  public final static int TBRAININNERACTION_ANTIMONE_FEAR         = 11;

  public final static int TBRAININNERACTION_HORMONE_RAGE          = 20;
  public final static int TBRAININNERACTION_ANTIMONE_RAGE         = 21;

  public final static int TBRAININNERACTION_HORMONE_PANIC         = 30;
  public final static int TBRAININNERACTION_ANTIMONE_PANIC        = 31;

  public final static int TBRAININNERACTION_HORMONE_SEEK          = 40;
  public final static int TBRAININNERACTION_ANTIMONE_SEEK         = 41;


  //CHRISTIANE
  public final static int TBRAININNERACTION_HORMONE_RADAR    = 50;
  public final static int TBRAININNERACTION_ANTIMONE_RADAR  = 51;

  /** 
    * converts the enum value to a human readable string
    */
  public static String getString(int pnEnum) {
    String oResult = enumClass.getString(pnEnum);

    switch(pnEnum) {
      case  TBRAININNERACTION_UNDEFINED:oResult = "[undefined]";break;
      case  TBRAININNERACTION_NOTHING:oResult = "[nothing]";break;

      case TBRAININNERACTION_HORMONE_FEAR :oResult="[hormone_fear]";break;
      case TBRAININNERACTION_ANTIMONE_FEAR:oResult="[antimone_fear]";break;

      case TBRAININNERACTION_HORMONE_RAGE :oResult="[hormone_rage]";break;
      case TBRAININNERACTION_ANTIMONE_RAGE:oResult="[antimone_rage]";break;

      case TBRAININNERACTION_HORMONE_PANIC :oResult="[hormone_panic]";break;
      case TBRAININNERACTION_ANTIMONE_PANIC:oResult="[antimone_panic]";break;

      case TBRAININNERACTION_HORMONE_SEEK :oResult="[hormone_seek]";break;
      case TBRAININNERACTION_ANTIMONE_SEEK:oResult="[antimone_seek]";break;

      //CHRISTIANE
      case TBRAININNERACTION_HORMONE_RADAR :oResult="[hormone_conversion]";break;
      case TBRAININNERACTION_ANTIMONE_RADAR:oResult="[antimone_conversion]";break;

      default:oResult = "_unkown_"+pnEnum+"_";
    }

    return oResult;
  }

  /**
    * converts a string read from an XML-file into its corresponding enum value
    */
  public static int getInteger(String poValue)
  {
     int nValue = TBRAININNERACTION_UNDEFINED;

     if( poValue.toLowerCase().equals("nothing") ) {
       nValue = TBRAININNERACTION_NOTHING;

     } else if( poValue.toLowerCase().equals("hormone_fear") ) {
       nValue = TBRAININNERACTION_HORMONE_FEAR;
     } else if( poValue.toLowerCase().equals("antimone_fear") ) {
       nValue = TBRAININNERACTION_ANTIMONE_FEAR;

     } else if( poValue.toLowerCase().equals("hormone_rage") ) {
       nValue = TBRAININNERACTION_HORMONE_RAGE;
     } else if( poValue.toLowerCase().equals("antimone_rage") ) {
       nValue = TBRAININNERACTION_ANTIMONE_RAGE;

     } else if( poValue.toLowerCase().equals("hormone_panic") ) {
       nValue = TBRAININNERACTION_HORMONE_PANIC;
     } else if( poValue.toLowerCase().equals("antimone_panic") ) {
       nValue = TBRAININNERACTION_ANTIMONE_PANIC;

     } else if( poValue.toLowerCase().equals("hormone_seek") ) {
       nValue = TBRAININNERACTION_HORMONE_SEEK;
     } else if( poValue.toLowerCase().equals("antimone_seek") ) {
       nValue = TBRAININNERACTION_ANTIMONE_SEEK;

     } else if( poValue.toLowerCase().equals("hormone_conversion")){  //CHRISTIANE
       nValue =TBRAININNERACTION_HORMONE_RADAR;
     } else if( poValue.toLowerCase().equals("antimone_conversion")){
       nValue = TBRAININNERACTION_ANTIMONE_RADAR;
     }


     return nValue;
  }
};
