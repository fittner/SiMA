/**
 * @author deutsch
 * 
 * Copied from the old bubble family game
 * 
 * CHKME keep this file?
 * @deprecated taken from old BFG
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */

// File enumClass.java
// May 09, 2006
//

// Belongs to package
package bw.utils.enums;

// Imports
import bw.utils.datatypes.clsCloneable;

/**
 *
 * Abstract base class for all enum classes. Gurantees that the static methods getString and getInteger are declared.
 *
 * @deprecated
 *
 * $Revision$:  Revision of last commit
 * $Author$: Author of last commit
 * $Date$: Date of last commit
 *
 */
abstract class enumClass extends clsCloneable {
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
