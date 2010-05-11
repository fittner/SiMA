// File clsPerceptionBumped.java
// May 09, 2006
//

// Belongs to package
package pa.bfg.symbolization.brainimages;

// Imports

/**
 *
 * This is the class description ...
 *
 * $Revision: 572 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2007-05-31 10:56:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
 *
 */
public class clsPerceptionBumped extends clsPerceptionBoolean {

  public boolean isBumped() {
    return isTrue();
  }

  @Override
  public String toString() {
    return "bumped: "+super.toString();
  }

};
