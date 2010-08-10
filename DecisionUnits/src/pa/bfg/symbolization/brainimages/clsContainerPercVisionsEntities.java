// File clsContainerPercVisions.java
// May 08, 2006
//

// Belongs to package
package pa.bfg.symbolization.brainimages;

// Imports
import java.util.Vector;

/**
 *
 * This is the class description ...
 *
 * $Revision: 627 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2007-06-25 12:20:22 +0200 (Mo, 25 Jun 2007) $: Date of last commit
 * @deprecated
 */
public class clsContainerPercVisionsEntities  {
	
	public Vector<clsPerceptionVisionEntity> moEntities = new Vector<clsPerceptionVisionEntity>();
	
  public clsContainerPercVisionsEntities() {
  }

  public clsPerceptionVisionEntity get(int pnPos) {
    return moEntities.get(pnPos);
  }

  protected String gettoString(Object poObject) {
    return ((clsPerceptionVisionEntity)poObject).toString();
  }
};
