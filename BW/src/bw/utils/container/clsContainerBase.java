/**
 * @author deutsch
 * 
 * Copied from the old bubble family game
 * 
 * CHKME keep this file?
 * @deprecated taken from old BFG
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */

// File clsContainerBase.java
// May 08, 2006
//

// Belongs to package
package bw.utils.container;

// Imports
import bw.utils.datatypes.clsCloneable;

/**
 *
 * This is the class description ...
 *
 * @deprecated
 * 
 * $Revision$:  Revision of last commit
 * $Author$: Author of last commit
 * $Date$: Date of last commit
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
