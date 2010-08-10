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
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 10.08.2010, 18:00:58
	 */
	private static final long serialVersionUID = 1817007602167912157L;

	@Override
	public Object clone() {
    try {
      return super.clone();
    } catch (CloneNotSupportedException e) {
      return null;
    }
  }
};
