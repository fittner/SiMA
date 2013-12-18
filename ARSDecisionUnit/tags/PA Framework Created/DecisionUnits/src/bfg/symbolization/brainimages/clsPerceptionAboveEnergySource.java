// File clsPerceptionAboveEnergySource.java
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
public class clsPerceptionAboveEnergySource  { //extends clsCloneable
  boolean mnAbove = false;
  boolean mnConsumable = false;

  public clsPerceptionAboveEnergySource() {
  }

  public clsPerceptionAboveEnergySource(boolean pnValue, boolean pnConsumable) {
    mnAbove = pnValue;
    mnConsumable = pnConsumable;
  }

  public void set(boolean pnValue, boolean pnConsumable) {
    mnAbove = pnValue;
    mnConsumable = pnConsumable;
  }

  public boolean isAbove() {
    return mnAbove;
  }

  public boolean isConsumable() {
    return mnConsumable;
  }

  @Override
  public String toString() {
    return mnAbove+" "+mnConsumable;
  }
};
