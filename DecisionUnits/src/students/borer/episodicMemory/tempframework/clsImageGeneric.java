// File clsGenericImage.java
// May 02, 2006
//

// Belongs to package
package students.borer.episodicMemory.tempframework;

import students.borer.episodicMemory.tempframework.clsCloneable;
import students.borer.episodicMemory.tempframework.clsContainerDrive;
import students.borer.episodicMemory.tempframework.clsContainerEmotion;
import students.borer.episodicMemory.tempframework.clsDrive;
import students.borer.episodicMemory.tempframework.clsEmotion;

/**
 * This is the class description ... $Revision: 572 $:  Revision of last commit $Author: deutsch $: Author of last commit $Date: 2007-05-31 10:56:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
 */
abstract class clsImageGeneric extends clsCloneable {
  /**
	 * @author deutsch
	 * 10.08.2010, 17:51:00
	 */
	private static final long serialVersionUID = -6108880079193606227L;
public clsContainerDrive   moDriveList = new clsContainerDrive();
  public clsContainerEmotion moEmotionList = new clsContainerEmotion();

  @Override
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
