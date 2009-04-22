// File clsContainerPercHormones.java
// May 22, 2007
//

// Belongs to package
package bfg.symbolization.brainimages;

import java.util.Vector;

// Imports

/**
 *
 * This is the class description ...
 *
 * $Revision: 627 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2007-06-25 12:20:22 +0200 (Mo, 25 Jun 2007) $: Date of last commit
 *
 */
public class clsContainerPercHormones  {
	public Vector<clsPerceptionHormone> moHormones = new Vector<clsPerceptionHormone>();
  public clsContainerPercHormones() {
  }

  protected String gettoString(Object poObject) {
    return ((clsPerceptionHormone)poObject).toString();
  }
};
