// File clsContainerPerceptions.java
// May 04, 2006
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
public class clsContainerPerceptions  {
	
  public Vector<clsImagePerception> moPerceptions = new Vector<clsImagePerception>();
	
  public clsContainerPerceptions() {
  }

  public clsImagePerception get(int pnPos) {
    return moPerceptions.get(pnPos);
  }

  protected String gettoString(Object poObject) {
    return ((clsImagePerception)poObject).toString();
  }
};
