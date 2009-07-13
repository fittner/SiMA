// File clsPerceptionAboveLandscape.java
// Feb 26, 2008
//

// Belongs to package
package bfg.symbolization.brainimages;

// Imports
import bfg.utils.enums.enumTypeLandscape;

/**
 *
 * This is the class description ...
 *
 * $Revision: 572 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2007-05-31 10:56:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
 *
 */
public class clsPerceptionAboveLandscape  { //extends clsCloneable
  public int mnLandscapeType  = enumTypeLandscape.TLANDSCAPE_UNDEFINED;

  public clsPerceptionAboveLandscape() {
  }

  public clsPerceptionAboveLandscape(int pnLandscapeType) {
    mnLandscapeType = pnLandscapeType;
  }

  public void set(int pnLandscapeType) {
    mnLandscapeType = pnLandscapeType;
  }

  @Override
  public String toString() {
    return enumTypeLandscape.getString(mnLandscapeType);
  }
};
