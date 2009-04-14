// File clsGenericImage.java
// May 02, 2006
//

// Belongs to package
package bw.memory.tempframework;

import bw.memory.tempframework.clsCloneable;
import bw.memory.tempframework.clsContainerDrive;
import bw.memory.tempframework.clsContainerEmotion;
import bw.memory.tempframework.clsDrive;
import bw.memory.tempframework.clsEmotion;

/**
 * This is the class description ... $Revision: 572 $:  Revision of last commit $Author: deutsch $: Author of last commit $Date: 2007-05-31 10:56:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
 */
abstract class clsImageGeneric extends clsCloneable {
  public clsContainerDrive   moDriveList = new clsContainerDrive();
  public clsContainerEmotion moEmotionList = new clsContainerEmotion();

  public String toString() {
    return "drives: "+moDriveList.toString()+"\n"+"emotions: "+moEmotionList.toString();
  }

  public void addDrive(clsDrive poDrive) {
    moDriveList.add(poDrive);
  }
  public void addEmotion(clsEmotion poEmotion) {
    moEmotionList.add(poEmotion);
  }
};
