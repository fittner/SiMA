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

// File enumClass.java
// May 09, 2006
//

// Belongs to package
package bfg.utils.enums;

// Imports

/**
 *
 * Abstract base class for all enum classes. Gurantees that the static methods getString and getInteger are declared.
 *
 * $Revision: 1825 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2009-01-27 19:59:35 +0100 (Di, 27 Jän 2009) $: Date of last commit
 *@deprecated
 */
public abstract class enumClass  {
  public final static int TENUM_UNDEFINED    = Integer.MIN_VALUE;

  /** 
    * converts the enum value to a human readable string
    */
  public static String getString(int pnEnum) {
    return "_unkown_"+pnEnum+"_";
  }

  /**
    * converts a string read from an XML-file into its corresponding enum value
    */
  public static int getInteger(String poValue) {
    return TENUM_UNDEFINED;
  }
}
