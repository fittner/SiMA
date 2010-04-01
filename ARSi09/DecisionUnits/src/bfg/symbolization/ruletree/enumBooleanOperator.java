/**
 * enumBooleanOperator.java: DecisionUnits - bfg.symbolization.ruletree
 * 
 * @author langr
 * 24.10.2009, 12:30:11
 */
package bfg.symbolization.ruletree;

import bfg.utils.enums.enumClass;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 24.10.2009, 12:30:11
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


