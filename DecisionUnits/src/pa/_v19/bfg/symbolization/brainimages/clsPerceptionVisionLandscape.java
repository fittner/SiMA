// File clsPerceptionVision.java
// May 08, 2006
//

// Belongs to package
package pa._v19.bfg.symbolization.brainimages;

// Imports
//import com.xj.anylogic.*;
import bfg.tools.shapes.clsPolarcoordinate;
import bfg.tools.shapes.clsPoint;
import bfg.utils.enumsOld.enumTypeLandscape;

/**
 *
 * This is the class description ...
 *
 * $Revision: 729 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2007-07-17 21:42:21 +0200 (Di, 17 Jul 2007) $: Date of last commit
 * @deprecated
 */
public class clsPerceptionVisionLandscape extends clsPerceptionVision {
  public int mnLandscapeType = enumTypeLandscape.TLANDSCAPE_UNDEFINED;

  public clsPerceptionVisionLandscape(clsPolarcoordinate poRelativePositionVector, int pnLandscapeType) {
    super(poRelativePositionVector);

    mnLandscapeType = pnLandscapeType;
  }

  public clsPerceptionVisionLandscape(clsPoint poRelativePosition, int pnLandscapeType) {
    super(poRelativePosition);

    mnLandscapeType = pnLandscapeType;
  }

  @Override
  public String toString() {
    return super.toString()+" "+enumTypeLandscape.getString(mnLandscapeType);
  }
};
