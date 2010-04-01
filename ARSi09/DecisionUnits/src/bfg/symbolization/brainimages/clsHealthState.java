// File clsHealthState.java
// May 04, 2006
//

// Belongs to package
package bfg.symbolization.brainimages;

// Imports
import bfg.tools.clsCloneable;
import bfg.utils.enumsOld.enumTypeHealthState;
import bfg.utils.enumsOld.enumTypeLevelHealthState;

/**
 *
 * This is the class description ...
 *
 * $Revision: 572 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2007-05-31 10:56:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
 *
 */
public class clsHealthState extends clsCloneable {
  int meType = -1; // enumHealthStates;
  int meState = enumTypeLevelHealthState.TLEVELHEALTHST_UNDEFINED; //enumTypeLevelHealthState
  float mrLevel = 0;
 
  public clsHealthState(int peType) {
    this(peType, 0.0f);
  }

  public clsHealthState(int peType, float prLevel) {
    meType = peType;
    setLevel(prLevel);
  }
  public clsHealthState(int peType, int pnLevel) {
    meType = peType;
    setLevel(pnLevel);
  }

  private void setLevel(int pnLevel) {
    meState = pnLevel;
  }

  private void setLevel(float prLevel) {
    mrLevel = prLevel;

    if (mrLevel < 0) {
      mrLevel = 0;
    }

    meState = enumTypeLevelHealthState.getHealthLevel(mrLevel);
  }

  public float getInternalValue() {
    return mrLevel;
  }
  public int getValue() {
    return meState;
  }
  public int getType() {
    return meType;
  }

  @Override
  public String toString() {
    return "t:"+enumTypeHealthState.getString(meType)+" s:"+enumTypeLevelHealthState.getString(meState);
  }
}

