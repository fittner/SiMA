// File clsPerceptionVision.java
// May 08, 2006
//

// Belongs to package
package pa.bfg.symbolization.brainimages;

// Imports
//import pkgTools.clsCloneable;
import bfg.tools.shapes.clsPolarcoordinate;
import bfg.tools.shapes.clsPoint;
import bfg.tools.shapes.clsAngle;
import bfg.utils.enumsOld.enumTypeDistance;
import bfg.utils.enumsOld.enumTypeSide;

/**
 *
 * This is the class description ...
 *
 * $Revision: 627 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2007-06-25 12:20:22 +0200 (Mo, 25 Jun 2007) $: Date of last commit
 * @deprecated
 */
public class clsPerceptionVision {
  public clsPolarcoordinate moRelativePosition;

  public int meDistance = enumTypeDistance.TDISTANCE_UNDEFINED;
  public int meDirection = enumTypeSide.TSIDE_UNDEFINED;

  public clsPerceptionVision(clsPolarcoordinate poRelativePositionVector) {
    moRelativePosition = new clsPolarcoordinate(poRelativePositionVector);

    setDirection();
    setDistance();
  }

  public clsPerceptionVision(clsPoint poRelativePosition) {
    moRelativePosition = new clsPolarcoordinate(poRelativePosition);

    setDirection();
    setDistance();
  }

  private void setDirection() {
    if (moRelativePosition.moAzimuth.mrAlpha > 0.1) {
      meDirection = enumTypeSide.TSIDE_LEFT;
    } else if (moRelativePosition.moAzimuth.mrAlpha < clsAngle.getNormalizedAngle(-0.1f)) {
      meDirection = enumTypeSide.TSIDE_RIGHT;
    } else {
      meDirection = enumTypeSide.TSIDE_MIDDLE;
    }
  }

  private void setDistance() {
    if (moRelativePosition.mrLength < 20) {
      meDistance = enumTypeDistance.TDISTANCE_SHORT;
    } else if (moRelativePosition.mrLength < 40) {
      meDistance = enumTypeDistance.TDISTANCE_MEDIUM;
    } else {
      meDistance = enumTypeDistance.TDISTANCE_FAR;
    }
  }

  public int getDistance() {
    return meDistance;
  }

  public int getDirection() {
    return meDirection;
  }

  @Override
  public String toString() {
    return "dist:"+enumTypeDistance.getString(meDistance)+" dir:"+enumTypeSide.getString(meDirection);
  }
};
