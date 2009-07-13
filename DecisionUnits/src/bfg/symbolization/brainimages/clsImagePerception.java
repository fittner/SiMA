// File clsAbstractImage.java
// May 02, 2006
//

// Belongs to package
package bfg.symbolization.brainimages;

// Imports
import bfg.utils.enums.enumTypeSocialLevel;

// --> adjust to new structure!
//import pkgBrainAction.clsActionContainer;
//import pkgBrainAction.clsAction;

/**
 *
 * This is the class description ...
 *
 * $Revision: 953 $:  Revision of last commit
 * $Author: gartner $: Author of last commit
 * $Date: 2008-03-11 12:31:58 +0100 (Di, 11 Mär 2008) $: Date of last commit
 *
 */
public class clsImagePerception extends clsImageGeneric {
  public clsContainerHealthState    moHealthStateList;
  public clsContainerPercSmellOMats moSmellingList;
  public clsContainerPercAcoustics  moAcousticList;

  public clsContainerPercVisionsEntities   moVisionEntitiesList;
  public clsContainerPercVisionsObstacles  moVisionObstaclesList;
  public clsContainerPercVisionsLandscapes moVisionLandscapesList;

  //cheater mode on
  public clsContainerPercVisionsEntities   moOmniVisionEntitiesList;
  public clsContainerPercVisionsObstacles  moOmniVisionObstaclesList;
  public clsContainerPercVisionsLandscapes moOmniVisionLandscapesList;
  //public clsPerceptionRealPosition         moRealPosition;
  //cheater mode off

  public clsContainerPercHormones moHormonesList;

  public clsPerceptionOdometry          moOdometry;
  public clsPerceptionBumped            moBumped;
  public clsPerceptionAboveEnergySource moAboveES;
  public clsPerceptionAboveLandscape    moAboveLandscape;

  //public clsActionContainer moLastExecutedActions;

  public int meSocialLevel;

  public clsImagePerception(){
    super();

    moHealthStateList = new clsContainerHealthState();
    moSmellingList    = new clsContainerPercSmellOMats();
    moAcousticList    = new clsContainerPercAcoustics();

    moVisionEntitiesList   = new clsContainerPercVisionsEntities();
    moVisionObstaclesList  = new clsContainerPercVisionsObstacles();
    moVisionLandscapesList = new clsContainerPercVisionsLandscapes();

    //cheater mode on
    moOmniVisionEntitiesList   = new clsContainerPercVisionsEntities();
    moOmniVisionObstaclesList  = new clsContainerPercVisionsObstacles();
    moOmniVisionLandscapesList = new clsContainerPercVisionsLandscapes();
//    moRealPosition             = new clsPerceptionRealPosition();
    //cheater mode off

    moHormonesList = new clsContainerPercHormones();

    moOdometry = new clsPerceptionOdometry();
    moBumped   = new clsPerceptionBumped();
    moAboveES  = new clsPerceptionAboveEnergySource();
    moAboveLandscape = new clsPerceptionAboveLandscape();

    meSocialLevel = enumTypeSocialLevel.TSOCIALLEVEL_NORMAL;

    //moLastExecutedActions   = new clsActionContainer();
  }

  @Override
  public String toString() {
    String oResult = super.toString()+"\n";
    oResult += "healthstate: "+moHealthStateList.toString()+"\n";
    oResult += "smellings: "+moSmellingList.toString()+"\n";
    oResult += "visions (entities): "+moVisionEntitiesList.toString()+"\n";
    oResult += "++ omnivisions (entities): "+moOmniVisionEntitiesList.toString()+"\n";
    oResult += "visions (obstacles): "+moVisionObstaclesList.toString()+"\n";
    oResult += "++ omnivisions (obstacles): "+moOmniVisionObstaclesList.toString()+"\n";
    oResult += "visions (landscapes): "+moVisionLandscapesList.toString()+"\n";
    oResult += "++ omnivisions (landscapes): "+moOmniVisionLandscapesList.toString()+"\n";
    oResult += "hormones: "+moHormonesList.toString()+"\n";
    oResult += "acoustics: "+moAcousticList.toString()+"\n";
    oResult += "odometrics: "+moOdometry.toString()+"\n";
    oResult += "bumped: "+moBumped.toString()+"\n";
    oResult += "above es: "+moAboveES.toString()+"\n";
    oResult += "above landscape: "+moAboveLandscape.toString()+"\n";
    //oResult += "last actions: "+moLastExecutedActions.toString()+"\n";
//    oResult += "++ real position: "+moRealPosition.toString()+"\n";
    return oResult;
  }

  public void addHealthState(clsHealthState poHealthState) {
    moHealthStateList.moHealth.add(poHealthState);
  }
  public void addSmelling(clsPerceptionSmellOMat poSmelling) {
    moSmellingList.moSmells.add(poSmelling);
  }
  public void addVisionEntity(clsPerceptionVisionEntity poVision) {
    moVisionEntitiesList.moEntities.add(poVision);
  }
  public void addVisionObstacle(clsPerceptionVisionObstacle poVision) {
    moVisionObstaclesList.moObstacles.add(poVision);
  }
  public void addVisionLandscape(clsPerceptionVisionLandscape poVision) {
    moVisionLandscapesList.moLandscapse.add(poVision);
  }
  public void addOmniVisionEntity(clsPerceptionVisionEntity poVision) {
    moOmniVisionEntitiesList.moEntities.add(poVision);
  }
  public void addOmniVisionObstacle(clsPerceptionVisionObstacle poVision) {
    moOmniVisionObstaclesList.moObstacles.add(poVision);
  }
  public void addOmniVisionLandscape(clsPerceptionVisionLandscape poVision) {
    moOmniVisionLandscapesList.moLandscapse.add(poVision);
  }
  public void addAcoustic(clsPerceptionAcoustic poAcoustic) {
    moAcousticList.moAcoustics.add(poAcoustic);
  }

  public void addHormone(clsPerceptionHormone poHormone) {
    moHormonesList.moHormones.add(poHormone);
  }

  public void setOdometry(clsPerceptionOdometry poOdometry) {
    moOdometry.setOdometry(poOdometry);
  }
  public void setBumped(boolean pnBumped) {
    moBumped.set(pnBumped);
  }
  public void setAboveES(boolean pnAboveES, boolean pnConsumable) {
    moAboveES.set(pnAboveES, pnConsumable);
  }

//  public void setRealPosition(clsPerceptionRealPosition poRealPosition) {
//    moRealPosition.set(poRealPosition);
//  }

  public void setAboveLandscape(int pnLandscapeType) {
    moAboveLandscape.set(pnLandscapeType);
  }

//  public void addLastExecutedAction(clsAction poAction) {
//    moLastExecutedActions.add(poAction);
//  }

  public void setSocialLevel(int pnSocialLevel) {
    meSocialLevel = pnSocialLevel;
  }

};


                                                  



