// File clsPerceptionSmellOMat.java
// May 08, 2006
//

// Belongs to package
package pa._v19.bfg.symbolization.brainimages;

// Imports
import pa._v19.bfg.tools.clsId;
import bfg.utils.enumsOld.enumTypeScentIntensity;

/**
 *
 * This is the class description ...
 *
 * $Revision: 572 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2007-05-31 10:56:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
 * @deprecated
 */
public class clsPerceptionSmellOMat {
  public boolean mnTeamMate = false;
  public boolean mnEnergyProducer = false;
  public boolean mnEnergyConsumer = false;
  public int meScentIntensity = enumTypeScentIntensity.TSCENTINT_NONE; //enumTypeScentIntensity
  public int mnScentId = -1; // -1 equals unkown

  public clsId moId;
  public int mnCount = 0;

  public clsPerceptionSmellOMat(clsId poId, int pnCount) {
    moId = new clsId(poId);
  }

  public clsPerceptionSmellOMat(boolean pnTeamMate, boolean pnEnergyProducer, boolean pnEnergyConsumer, int pnCount) {
    moId = new clsId();

    mnTeamMate = pnTeamMate;
    mnEnergyProducer = pnEnergyProducer;
    mnEnergyConsumer = pnEnergyConsumer;

    setCount(pnCount);
  }

  private void setCount(int pnCount) {
    mnCount = pnCount;

    if (mnCount < 0) {
      mnCount = 0;
    }

    switch (mnCount) {
      case 0:  meScentIntensity = enumTypeScentIntensity.TSCENTINT_NONE; break;
      case 1:  meScentIntensity = enumTypeScentIntensity.TSCENTINT_LOW; break;
      case 2:  meScentIntensity = enumTypeScentIntensity.TSCENTINT_MEDIUM; break;
      case 3:  meScentIntensity = enumTypeScentIntensity.TSCENTINT_HIGH; break;
      default: meScentIntensity = enumTypeScentIntensity.TSCENTINT_EXTREME; break;
    }
  }

  public boolean isTeamMate() {
    return mnTeamMate;
  }

  public boolean isEnergyProducer() {
    return mnEnergyProducer;
  }

  public boolean isEnergyConsumer() {
    return mnEnergyConsumer;
  }

  public int getScentIntensitiy() {
    return meScentIntensity;
  }

  public int getScentId() {
    return mnScentId;
  }

  @Override
  public String toString() {
    return "tm:"+mnTeamMate+" ep:"+mnEnergyProducer+" ec:"+mnEnergyConsumer+" sc:"+enumTypeScentIntensity.getString(meScentIntensity)+" sid:"+mnScentId;
  }
};
