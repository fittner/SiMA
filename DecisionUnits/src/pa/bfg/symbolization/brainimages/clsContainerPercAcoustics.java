// File clsContainerPercAcoustics.java
// September 19, 2006
//

// Belongs to package
package pa.bfg.symbolization.brainimages;

import java.util.Vector;

// Imports

/**
 *
 * This is the class description ...
 *
 * $Revision: 627 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2007-06-25 12:20:22 +0200 (Mo, 25 Jun 2007) $: Date of last commit
 * @deprecated
 */
public class clsContainerPercAcoustics {
	
	public Vector<clsPerceptionAcoustic> moAcoustics = new Vector<clsPerceptionAcoustic>();
	
  public clsContainerPercAcoustics() {
  }

  protected String gettoString(Object poObject) {
    return ((clsPerceptionAcoustic)poObject).toString();
  }
};
