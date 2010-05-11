// File clsPerceptionVision.java
// May 08, 2006
//

// Belongs to package
package pa.bfg.symbolization.brainimages;

// Imports
//import pkgTools.clsCloneable;
import bfg.tools.shapes.clsPolarcoordinate;
import bfg.tools.shapes.clsPoint;
import bfg.utils.enumsOld.enumTypeDistance;
import bfg.utils.enumsOld.enumTypeHealthState;
import bfg.utils.enumsOld.enumTypeSide;
import bfg.utils.enumsOld.enumTypeSocialLevel;

/**
 *
 * This is the class description ...
 *
 * $Revision: 627 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2007-06-25 12:20:22 +0200 (Mo, 25 Jun 2007) $: Date of last commit
 *
 */
public class clsPerceptionVisionEntity extends clsPerceptionVision {
  public boolean mnEnergyProducer = false;
  public boolean mnEnergyConsumer = false;
  public int mnTeamId = -1;

  public clsHealthState moHealthState;

  public int meSocialLevel = enumTypeSocialLevel.TSOCIALLEVEL_NORMAL;

  public clsPerceptionVisionEntity(clsPolarcoordinate poRelativePositionVector, int pnHealthState, int pnTeamId, boolean pnEnergyConsumer, boolean pnEnergyProducer, int pnSocialLevel) {
    super(poRelativePositionVector);

    moHealthState = new clsHealthState(pnHealthState, enumTypeHealthState.THEALTHST_GENERAL);
    mnTeamId = pnTeamId;
    mnEnergyConsumer = pnEnergyConsumer;
    mnEnergyProducer = pnEnergyProducer;
    meSocialLevel = pnSocialLevel;
  }

  public clsPerceptionVisionEntity(clsPoint poRelativePosition, int pnHealthState, int pnTeamId, boolean pnEnergyConsumer, boolean pnEnergyProducer, int pnSocialLevel) {
    super(poRelativePosition);

    moHealthState = new clsHealthState(pnHealthState, enumTypeHealthState.THEALTHST_GENERAL);
    mnTeamId = pnTeamId;
    mnEnergyConsumer = pnEnergyConsumer;
    mnEnergyProducer = pnEnergyProducer;
    meSocialLevel = pnSocialLevel;
  }

  public int getTeamId() {
    return mnTeamId;
  }

  public boolean isEnergyProducer() {
    return mnEnergyProducer;
  }

  public boolean isEnergyConsumer() {
    return mnEnergyConsumer;
  }          

  @Override
  public String toString() {
    return "tid:"+mnTeamId+" mp:"+mnEnergyProducer+" mc:"+mnEnergyConsumer+" dist:"+enumTypeDistance.getString(meDistance)+" dir:"+enumTypeSide.getString(meDirection)+" soc.lvl:"+enumTypeSocialLevel.getString(meSocialLevel);
  }
};
