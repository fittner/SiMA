// File clsContainerBase.java
// May 08, 2006
//

// Belongs to package
package bfg.utils.container;

// Imports
import bfg.utils.datatypes.clsCloneable;

/**
 *
 * This is the class description ...
 *
 * $Revision: 572 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2007-05-31 10:56:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
 *
 */
abstract class clsContainerBase extends clsCloneable implements Cloneable  {
  public clsContainerBase() {
  }

  abstract public void add(Object poObject);

  abstract protected Object getObject(int pnPos);

  abstract public void remove(Object poObject);
  abstract public void remove(int pnPos);
  abstract public void removeAll();

  abstract public int size();

  abstract public String toString();

  protected abstract String gettoString(Object poObject);
};
