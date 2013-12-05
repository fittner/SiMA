// File clsContainerCompareResults.java
// May 16, 2006
//

// Belongs to package
package students.borer.episodicMemory.tempframework;

// Imports
//import java.util.TreeMap;
import java.util.Iterator;

import students.borer.episodicMemory.tempframework.clsContainerBaseVector;
import students.borer.episodicMemory.tempframework.clsRuleCompareResult;
//import java.util.Set;
//import memory.tempframework.clsMutableDouble;
//import memory.tempframework.clsMutableFloat;
//import memory.tempframework.clsMutableInteger;
//import memory.tempframework.clsContainerEmotion;
//import memory.tempframework.clsEmotion;
//import memory.tempframework.clsContainerDrive;
//import memory.tempframework.clsDrive;

/**
 *
 * This is the class description ...
 *
 * $Revision: 691 $:  Revision of last commit
 * $Author: langr $: Author of last commit
 * $Date: 2007-07-14 08:59:49 +0200 (Sa, 14 Jul 2007) $: Date of last commit
 *
 */
public class clsContainerCompareResults  extends clsContainerBaseVector {
  /**
	 * @author deutsch
	 * 10.08.2010, 17:45:29
	 */
	private static final long serialVersionUID = 1434376115842954246L;

public clsContainerCompareResults(int pnMaxSize) {
    super(pnMaxSize);
  } 
  public clsContainerCompareResults() {
  }

  public clsRuleCompareResult get(int pnPos) {
    return (clsRuleCompareResult)getObject(pnPos);
  }

  public clsRuleCompareResult containsAbstractImage( int pnId )
  {
    clsRuleCompareResult oResult = null;
    for( int i=0;i<moContainer.size();++i)
    {
      clsRuleCompareResult oCurrentResult = ((clsRuleCompareResult)moContainer.get(i));
      if( oCurrentResult.moAbstractImageId.intValue() == pnId )
      {
        oResult = oCurrentResult;
      }
    }
    return oResult;
  }

//  public void copyActionsList(clsActionContainer oResult, clsContainerAbstractImages poBrainsAbstractImages)
//  {
//    for( int i=0;i<moContainer.size();++i)
//    {
//      clsRuleCompareResult oCurrentResult = ((clsRuleCompareResult)moContainer.get(i));
//
//      clsImageAbstract oImageAbstract = poBrainsAbstractImages.get( oCurrentResult.moAbstractImageId.intValue() );
//      if (oImageAbstract.moActionPlan != null) {
//        oImageAbstract.moActionPlan.getCurrentActionList( oResult, oCurrentResult.moMatch.get() );
//      }
//    }
//  }
//
//  public void copyActionsList(clsActionContainer oOuterResult, clsActionContainer oInnerResult, clsContainerAbstractImages poBrainsAbstractImages)
//  {
//    for( int i=0;i<moContainer.size();++i)
//    {
//      clsRuleCompareResult oCurrentResult = ((clsRuleCompareResult)moContainer.get(i));
//
//      clsImageAbstract oImageAbstract = poBrainsAbstractImages.get( oCurrentResult.moAbstractImageId.intValue() );
//      if (oImageAbstract.moActionPlan != null) {
//        oImageAbstract.moActionPlan.getOuterActionList( oOuterResult, oCurrentResult.moMatch.get() );
//        oImageAbstract.moActionPlan.getInnerActionList( oInnerResult, oCurrentResult.moMatch.get() );
//      }
//    }
//  }
//
//
//  public clsAction getSuperEgoImages(clsAction poCurrentAction, 
//                                     clsContainerAbstractImages poBrainsAbstractImages)
//  {
//    clsAction oResult = null;
//    for( int i=0;i<moContainer.size();++i)
//    {
//      clsRuleCompareResult oCurrentResult = ((clsRuleCompareResult)moContainer.get(i));
//
//      clsImageAbstract oImageAbstract = poBrainsAbstractImages.get( oCurrentResult.moAbstractImageId.intValue() );
//      if( oImageAbstract != null && oImageAbstract.moSuperEgoAction != null)
//      {
//        //case we have a super ego abstract image match
//        if( oCurrentResult.moMatch.get() > oImageAbstract.moSuperEgoAction.mrThreashold )
//        {
//          //case we have a threashold-detection --> super ego should become active
//          if( oImageAbstract.moSuperEgoAction.contains( poCurrentAction ) )
//          {
//            oResult = clsAction.conflictAction();
//            break;
//          }
//        }
//      }
//    }
//    return oResult;
//  }

  @Override
  protected String gettoString(Object poObject) {
    return ((clsRuleCompareResult)poObject).toString();
  }
  
  public String toString(boolean pnFull) {
    if (pnFull) {
      return super.toString();
    } else {
      String oResult = "";

      @SuppressWarnings("rawtypes")
	Iterator i = moContainer.iterator();

      while (i.hasNext()) {
        clsRuleCompareResult oCompResult = (clsRuleCompareResult)i.next();

        oResult += oCompResult.moMatch+" ";
      }

      return oResult;
    }
  }

//  public clsContainerDrive getWeightedTargetDrives() {
//    TreeMap oDriveSum = new TreeMap();
//    TreeMap oDriveCount = new TreeMap();
//    TreeMap oDriveMaxMatch = new TreeMap();
//
//    clsContainerDrive moTargetDrives = new clsContainerDrive();
//
//    Iterator i = moContainer.iterator();
//
//    // iterate through all stored results
//    while (i.hasNext()) {
//      clsRuleCompareResult oResult = (clsRuleCompareResult)i.next();
//      
//      // fetch match (0..1). if match better than any previous found - store it
//      double rMatch = (oResult.getMatch()).get();
//
//      // iterate through all target drives in the result
//      clsContainerDrive oTargetDrives = oResult.getTargetDrives();
//      Set oDriveTypes = oTargetDrives.keySet();
//      Iterator j = oDriveTypes.iterator();
//      while (j.hasNext()) {
//        Integer oKey = (Integer)j.next();
//        clsDrive oDrive = oTargetDrives.get(oKey);
//
//        //get drive value and weight it by match
//        double rValue = (oDrive.getInternalValue()).getValue();
//        rValue *= rMatch;
//
//        if (oDriveSum.containsKey(oKey)) { // we have already found this particlar drive previously
//          clsMutableDouble oSum = (clsMutableDouble)oDriveSum.get(oKey);
//          clsMutableInteger oCount = (clsMutableInteger)oDriveCount.get(oKey);
//          clsMutableDouble oMaxMatch = (clsMutableDouble)oDriveMaxMatch.get(oKey);
//
//          oSum.add(rValue);
//          oCount.inc();
//          if (oMaxMatch.doubleValue() < rMatch) {
//            oMaxMatch.set(rMatch);
//          }
//        } else { // this drive has not been in any previous result set.
//          oDriveSum.put(oKey, new clsMutableDouble(rValue));
//          oDriveCount.put(oKey, new clsMutableInteger(1));
//          oDriveMaxMatch.put(oKey, new clsMutableDouble(rMatch));
//        }
//      }
//    }
//
//    // normalize to maximum found match and build average for every found drive
//    Set oFinalTargetDrives = oDriveSum.keySet();
//    Iterator k = oFinalTargetDrives.iterator();
//    while (k.hasNext()) {
//      Integer oKey = (Integer)k.next();
//
//      clsMutableFloat oSum = (clsMutableFloat)oDriveSum.get(oKey);
//      clsMutableInteger oCount = (clsMutableInteger)oDriveSum.get(oKey);
//      clsMutableFloat oMaxMatch = (clsMutableFloat)oDriveMaxMatch.get(oKey);
//
//      float rSum = oSum.floatValue();
//      rSum = rSum / oMaxMatch.floatValue();           //normalize to maximal found match
//      rSum = rSum / (float)oCount.intValue(); //build average
//
//      moTargetDrives.add(new clsDrive(oKey.intValue(), rSum), oKey);
//    }
//
//    // return results
//    return moTargetDrives;
//  }

//  public clsContainerEmotion getWeightedTargetEmotions() {
//    TreeMap oEmotionSum = new TreeMap();
//    TreeMap oEmotionCount = new TreeMap();
//    TreeMap oEmotionMaxMatch = new TreeMap();
//
//    clsContainerEmotion moTargetEmotions = new clsContainerEmotion();
//
//    Iterator i = moContainer.iterator();
//
//    // iterate through all stored results
//    while (i.hasNext()) {
//      clsRuleCompareResult oResult = (clsRuleCompareResult)i.next();
//      
//      // fetch match (0..1). if match better than any previous found - store it
//      double rMatch = (oResult.getMatch()).get();
//
//      // iterate through all target Emotions in the result
//      clsContainerEmotion oTargetEmotions = oResult.getTargetEmotions();
//      Set oEmotionTypes = oTargetEmotions.keySet();
//      Iterator j = oEmotionTypes.iterator();
//      while (j.hasNext()) {
//        Integer oKey = (Integer)j.next();
//        clsEmotion oEmotion = oTargetEmotions.get(oKey);
//
//        //get Emotion value and weight it by match
//        double rValue = (oEmotion.getInternalValue()).getValue();
//        rValue *= rMatch;
//
//        if (oEmotionSum.containsKey(oKey)) { // we have already found this particlar Emotion previously
//          clsMutableDouble oSum = (clsMutableDouble)oEmotionSum.get(oKey);
//          clsMutableInteger oCount = (clsMutableInteger)oEmotionCount.get(oKey);
//          clsMutableDouble oMaxMatch = (clsMutableDouble)oEmotionMaxMatch.get(oKey);
//
//          oSum.add(rValue);
//          oCount.inc();
//          if (oMaxMatch.doubleValue() < rMatch) {
//            oMaxMatch.set(rMatch);
//          }
//        } else { // this Emotion has not been in any previous result set.
//          oEmotionSum.put(oKey, new clsMutableDouble(rValue));
//          oEmotionCount.put(oKey, new clsMutableInteger(1));
//          oEmotionMaxMatch.put(oKey, new clsMutableDouble(rMatch));
//        }
//      }
//    }
//
//    // normalize to maximum found match and build average for every found Emotion
//    Set oFinalTargetEmotions = oEmotionSum.keySet();
//    Iterator k = oFinalTargetEmotions.iterator();
//    while (k.hasNext()) {
//      Integer oKey = (Integer)k.next();
//
//      clsMutableFloat oSum = (clsMutableFloat)oEmotionSum.get(oKey);
//      clsMutableInteger oCount = (clsMutableInteger)oEmotionSum.get(oKey);
//      clsMutableFloat oMaxMatch = (clsMutableFloat)oEmotionMaxMatch.get(oKey);
//
//      float rSum = oSum.floatValue();
//      rSum = rSum / oMaxMatch.floatValue();           //normalize to maximal found match
//      rSum = rSum / (float)oCount.intValue(); //build average
//
//      moTargetEmotions.add(new clsEmotion(oKey.intValue(), rSum), oKey);
//    }
//
//    // return results
//    return moTargetEmotions;
//  }
};
