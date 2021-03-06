// File enumClass.java
// May 09, 2006
//

// Belongs to package
package memory.tempframework;

// Imports
import memory.tempframework.clsCloneable;

/**
 *
 * Abstract base class for all enum classes. Gurantees that the static methods getString and getInteger are declared.
 *
 * $Revision: 580 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2007-05-31 18:07:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
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
