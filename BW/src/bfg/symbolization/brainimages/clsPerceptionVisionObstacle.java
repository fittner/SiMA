// File clsPerceptionVision.java
// May 08, 2006
//

// Belongs to package
package bfg.symbolization.brainimages;

// Imports
import bw.utils.enums.deprecated.enumTypeObstacle;
import bfg.tools.shapes.clsPolarcoordinate;
import bfg.tools.shapes.clsPoint;

/**
 *
 * This is the class description ...
 *
 * $Revision: 729 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2007-07-17 21:42:21 +0200 (Di, 17 Jul 2007) $: Date of last commit
 *
 */
public class clsPerceptionVisionObstacle extends clsPerceptionVision {
  public int mnObstacleType = enumTypeObstacle.TOBSTACLE_UNDEFINED;

  public clsPerceptionVisionObstacle(clsPolarcoordinate poRelativePositionVector, int pnObstacleType) {
    super(poRelativePositionVector);

    mnObstacleType = pnObstacleType;
  }

  public clsPerceptionVisionObstacle(clsPoint poRelativePosition, int pnObstacleType) {
    super(poRelativePosition);

    mnObstacleType = pnObstacleType;
  }

  public String toString() {
    return super.toString()+" "+enumTypeObstacle.getString(mnObstacleType);
  }
};
