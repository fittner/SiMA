// File clsCloneable.java
// May 16, 2006
//

// Belongs to package
package pa.bfg.tools;

// Imports
import java.io.Serializable;

/**
 *
 * This is the class description ...
 *
 * $Revision: 572 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2007-05-31 10:56:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
 * @deprecated
 */
public class clsCloneable implements Cloneable, Serializable {
	@Override
	public Object clone() {
    try {
      return super.clone();
    } catch (CloneNotSupportedException e) {
      return null;
    }
  }
};
