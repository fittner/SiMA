// File clsPerceptionAcoustic.java
// September 19, 2006
//

// Belongs to package
package bfg.symbolization.brainimages;

// Imports
import bfg.utils.enums.enumTypeEntityMessages;

/**
 *
 * This is the class description ...
 *
 * $Revision: 572 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2007-05-31 10:56:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
 *
 */
public class clsPerceptionAcoustic { //extends clsCloneable
  int mnMessageTypeId = enumTypeEntityMessages.TENTITYMESSAGE_UNDEFINED;

  public clsPerceptionAcoustic() {
  }

  public clsPerceptionAcoustic(int pnMessageTypeId) {
    mnMessageTypeId = pnMessageTypeId;
  }

  public clsPerceptionAcoustic(clsPerceptionAcoustic poPerception) {
    mnMessageTypeId = poPerception.mnMessageTypeId;
  }

  public void set(int pnMessageTypeId) {
    mnMessageTypeId = pnMessageTypeId;
  }

  public void set(clsPerceptionAcoustic poPerception) {
    mnMessageTypeId = poPerception.mnMessageTypeId;
  }

  public int getMessageType()
  {
    return mnMessageTypeId;
  }

  @Override
  public String toString() {
    return "message:"+enumTypeEntityMessages.getString(mnMessageTypeId);
  }

};                    
