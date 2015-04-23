// File clsPerceptionHormone.java
// May 22, 2007
//

// Belongs to package
package bfg.symbolization.brainimages;

// Imports
import bfg.tools.clsValueExponential;
import bfg.utils.enums.enumTypeHormone;
import bfg.utils.enums.enumTypeLevelHormone;

/**
 *
 * This is the class description ...
 *
 * $Revision: 925 $:  Revision of last commit
 * $Author: gartner $: Author of last commit
 * $Date: 2008-02-26 14:34:28 +0100 (Di, 26 Feb 2008) $: Date of last commit
 *
 */
public class clsPerceptionHormone {
  int meTypeId = enumTypeHormone.THORMONE_UNDEFINED;
  int meLevel = enumTypeLevelHormone.TLEVELHORMONE_UNDEFINED;

  clsValueExponential moValue;

  public clsPerceptionHormone(int pnTypeId) {
    meTypeId = pnTypeId;

    moValue = new clsValueExponential(0.0f);
    setLevel();
  }
  public clsPerceptionHormone(int pnTypeId, float prValue) {
    meTypeId = pnTypeId;

    moValue = new clsValueExponential(prValue);
    setLevel();
  }

  public void setValue(float prValue) {
    moValue.setValue(prValue);
    setLevel();
  }

  public float getValue() {
    return moValue.getValue();
  }

  public int getType() {
    return meTypeId;
  }
  public int getLevel() {
    return meLevel;
  }
  public clsValueExponential getInternalValue() {
    return moValue;
  }

  public void setLevel(int level) {
    meLevel = level;
  }

  private void setLevel() {
    meLevel = enumTypeLevelHormone.getHormoneLevel(moValue.getValue());
  }

  @Override
  public String toString() {
    return toString(false);
  }

  public String toString(boolean pnDetails) {
    String oResult = "";

    oResult = "hormone:"+enumTypeHormone.getString(meTypeId)+" value:"+enumTypeLevelHormone.getString(meLevel);

    if (pnDetails) {
     oResult += " internals:"+moValue.toString(true);
    }

    return oResult;
  }
};
