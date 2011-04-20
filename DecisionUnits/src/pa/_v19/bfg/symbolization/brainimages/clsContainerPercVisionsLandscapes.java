// File clsContainerPercVisions.java
// May 08, 2006
//

// Belongs to package
package pa._v19.bfg.symbolization.brainimages;

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
public class clsContainerPercVisionsLandscapes  {
	
	public Vector<clsPerceptionVisionLandscape> moLandscapse = new Vector<clsPerceptionVisionLandscape>();

	public clsContainerPercVisionsLandscapes() {
  }

  protected String gettoString(Object poObject) {
    return ((clsPerceptionVisionLandscape)poObject).toString();
  }
};
