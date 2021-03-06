// File clsRuleCompareResult.java
// May 02, 2006
//

// Belongs to package
package memory.tempframework;

import memory.tempframework.cls0to1;
import memory.tempframework.clsCloneable;
//import memory.tempframework.clsContainerDrive;
//import memory.tempframework.clsContainerEmotion;

/**
 * This is the class description ... $Revision: 572 $:  Revision of last commit $Author: deutsch $: Author of last commit $Date: 2007-05-31 10:56:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
 */
public class clsRuleCompareResult extends clsCloneable {
  public cls0to1 moMatch;  //percent of coverage of imagePerception and imageAbstract
  public Integer moAbstractImageId;
//  public clsImageAbstract moAbstractImage;
//
//  int mnMatchingLeafCount = 0;
//
//  clsContainerDrive moTargetDrives;
//  clsContainerEmotion moTargetEmotions;
//
//  public clsRuleCompareResult(clsImageAbstract poAbstractImage, float prMatch) {
//    moAbstractImage = poAbstractImage;
//    moAbstractImageId = new Integer(poAbstractImage.mnImageId);
//    moMatch = new cls0to1(prMatch);
//
//    moTargetDrives = new clsContainerDrive();
//    moTargetEmotions = new clsContainerEmotion();
//  }
//
//  public clsRuleCompareResult(clsImageAbstract poAbstractImage, cls0to1 poMatch) {
//    moAbstractImage = poAbstractImage;
//    moAbstractImageId = new Integer(poAbstractImage.mnImageId);
//    moMatch = new cls0to1(poMatch);
//
//    moTargetDrives = new clsContainerDrive();
//    moTargetEmotions = new clsContainerEmotion();
//  }
//
//  public void setTargetDrives(clsContainerDrive poTargetDrives) {
//    moTargetDrives = (clsContainerDrive)poTargetDrives.clone();
//  }
//  public void setTargetEmotions(clsContainerEmotion poTargetEmotions) {
//    moTargetEmotions = (clsContainerEmotion)poTargetEmotions.clone();
//  }
//
//  public clsContainerDrive getTargetDrives() {
//    return moTargetDrives;
//  }
//
//  public clsContainerEmotion getTargetEmotions() {
//    return moTargetEmotions;
//  }
//
  public cls0to1 getMatch() {
    return moMatch;
  }
//
//  public void stress(clsIdentity poBrainsIdentity)
//  {
//    if( poBrainsIdentity.mnFocusOfAttention )
//    {
//      moMatch.set( moMatch.get() * 1.2f );
//    }
//  }
//
//  public String toString() {
//    return toString(true);
//  }
//
//  public String toString(boolean pnNice) {
//    String oResult = "";
//
//    if (pnNice) {
//      oResult = "["+moAbstractImageId+"]: "+moMatch.toString()+" | ("+moAbstractImage.moName+")" ;
//    } else {
//      oResult = "abstract image:"+moAbstractImageId+" match:"+moMatch.toString()+"\n";
//      oResult+= moTargetDrives.toString()+"\n";
//      oResult+= moTargetEmotions.toString();
//    }
//
//    return oResult;
//  }
};
