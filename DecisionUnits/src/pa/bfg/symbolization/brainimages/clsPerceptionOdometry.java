// File clsPerceptionOdometry.java
// May 08, 2006
//

// Belongs to package
package pa.bfg.symbolization.brainimages;

// Imports
import bfg.tools.shapes.clsAngle;
import bfg.tools.shapes.clsPolarcoordinate;
import bfg.tools.shapes.clsPoint;
import bfg.utils.enumsOld.enumTypeDistance;
import bfg.utils.enumsOld.enumTypeRotation;
import bfg.utils.enumsOld.enumTypeSide;

/**
 *
 * This is the class description ...
 *
 * $Revision: 627 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2007-06-25 12:20:22 +0200 (Mo, 25 Jun 2007) $: Date of last commit
 *
 */
public class clsPerceptionOdometry  { //extends clsCloneable
  clsPolarcoordinate moRelativeMovement;
  clsAngle moRelativeRotation;

  int meDistance  = enumTypeDistance.TDISTANCE_UNDEFINED;
  int meDirection = enumTypeSide.TSIDE_UNDEFINED;
  int meRotation  = enumTypeRotation.TROTATION_UNDEFINED;

  public clsPerceptionOdometry() {
    moRelativeMovement = new clsPolarcoordinate();
    moRelativeRotation = new clsAngle();
  }

  public clsPerceptionOdometry(clsPolarcoordinate poRelativeMovementVector, clsAngle poRelativeRotation) {
    moRelativeMovement = new clsPolarcoordinate(poRelativeMovementVector);
    moRelativeRotation.set(poRelativeRotation);

    setDirection();
    setDistance();
    setRotation();
  }

  public clsPerceptionOdometry(clsPoint poRelativeMovement, clsAngle poRelativeRotation) {
    moRelativeMovement = new clsPolarcoordinate(poRelativeMovement);
    moRelativeRotation.set(poRelativeRotation);

    setDirection();
    setDistance();
    setRotation();
  }

  public void setOdometry(clsPerceptionOdometry poOdometry) {
    setOdometry(poOdometry.moRelativeMovement, poOdometry.moRelativeRotation);
  }

  public void setOdometry(clsPolarcoordinate poRelativeMovementVector, clsAngle poRelativeRotation) {
    moRelativeMovement.set(poRelativeMovementVector);
    moRelativeRotation.set(poRelativeRotation);

    setDirection();
    setDistance();    
    setRotation();
  }
  public void setOdometry(clsPoint poRelativeMovement, clsAngle poRelativeRotation) {
    moRelativeMovement.set(poRelativeMovement);
    moRelativeRotation.set(poRelativeRotation);

    setDirection();
    setDistance();  
    setRotation();
  }

  private void setRotation() {
    meRotation = enumTypeRotation.TROTATION_NONE;

    if (moRelativeRotation.mrAlpha > 0.05 && moRelativeRotation.mrAlpha < Math.PI) {
      if (moRelativeRotation.mrAlpha > Math.PI / 2) {
        meRotation = enumTypeRotation.TROTATION_WIDELEFT;
      } else if (moRelativeRotation.mrAlpha > 0.2) {
        meRotation = enumTypeRotation.TROTATION_LEFT;
      } else {
        meRotation = enumTypeRotation.TROTATION_SHORTLEFT;
      }
    } else if (moRelativeRotation.mrAlpha < (2*Math.PI - 0.05) ) {
      if (moRelativeRotation.mrAlpha < 3*Math.PI / 2 ) {
        meRotation = enumTypeRotation.TROTATION_WIDERIGHT;
      } else if (moRelativeRotation.mrAlpha < 2*Math.PI - 0.2) {
        meRotation = enumTypeRotation.TROTATION_RIGHT;
      } else {
        meRotation = enumTypeRotation.TROTATION_SHORTRIGHT;
      }
    }
  }

  private void setDirection() {
    if (moRelativeMovement.moAzimuth.mrAlpha > 0.1) {
      meDirection = enumTypeSide.TSIDE_LEFT;
    } else if (moRelativeMovement.moAzimuth.mrAlpha < clsAngle.getNormalizedAngle(-0.1f)) {
      meDirection = enumTypeSide.TSIDE_RIGHT;
    } else {
      meDirection = enumTypeSide.TSIDE_MIDDLE;
    }
  }

  private void setDistance() {
    double rEps = 0.0001;

    if (moRelativeMovement.mrLength < 0+rEps) {
      meDistance = enumTypeDistance.TDISTANCE_NULL;
    } else if (moRelativeMovement.mrLength < 10) {
      meDistance = enumTypeDistance.TDISTANCE_VERYSHORT;
    } else if (moRelativeMovement.mrLength < 20) {
      meDistance = enumTypeDistance.TDISTANCE_SHORT;
    } else if (moRelativeMovement.mrLength < 40) {
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
    return "dist:"+enumTypeDistance.getString(meDistance)+" dir:"+enumTypeSide.getString(meDirection)+" rot:"+enumTypeRotation.getString(meRotation);
  }

};                               
