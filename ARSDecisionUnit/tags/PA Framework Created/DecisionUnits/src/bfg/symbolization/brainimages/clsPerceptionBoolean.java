// File clsPerceptionBoolean.java
// May 09, 2006
//

// Belongs to package
package bfg.symbolization.brainimages;

// Imports

/**
 *
 * This is the class description ...
 *
 * $Revision: 572 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2007-05-31 10:56:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
 *
 */
abstract class clsPerceptionBoolean  { //extends clsCloneable
  boolean mnValue = false;

  public clsPerceptionBoolean() {
    mnValue = false;
  }

  public clsPerceptionBoolean(boolean pnValue) {
    mnValue = pnValue;
  }

  public void set(boolean pnValue) {
    mnValue = pnValue;
  }

  protected boolean isTrue() {
    return mnValue;
  }

  @Override
  public String toString() {
    return ""+mnValue;
  }
};
