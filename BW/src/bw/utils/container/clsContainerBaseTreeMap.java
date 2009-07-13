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

// File clsContainerBaseTreeMap.java
// May 15, 2006
//

// Belongs to package
package bw.utils.container;

// Imports
import bw.utils.datatypes.clsMutableInteger;
import java.util.TreeMap;
import java.util.Set;
import java.util.Iterator;

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
abstract public class clsContainerBaseTreeMap extends clsContainerBase {
  protected TreeMap moContainer;
  protected clsMutableInteger moMaxKey;

  public clsContainerBaseTreeMap() {
    super();
    moContainer = new TreeMap();
    moMaxKey = new clsMutableInteger(-1);
  }

  @Override
  public void add(Object poObject) {
    moMaxKey.inc();
    add(poObject, moMaxKey.IntegerValue());
  }
  public void add(Object poObject, int pnKey) {
    add(poObject, new Integer(pnKey));
  }
  public void add(Object poObject, Integer poKey) {
    moContainer.put(poKey,poObject);

    if (poKey.intValue() > moMaxKey.intValue()) {
      moMaxKey.set(poKey.intValue());
    }
  }

  @Override
  protected Object getObject(int pnKey) {
    return getObject(new Integer(pnKey));
  }
  protected Object getObject(Integer poKey) {
    return moContainer.get(poKey);
  }

  public TreeMap getContainer() {
    return moContainer;
  }

  public Set keySet() {
    return moContainer.keySet();
  }

  @Override
  public void remove(Object poObject) {
    Set oTemp = moContainer.keySet();

    Iterator oI = oTemp.iterator();
    while (oI.hasNext()) {
      Integer oKey = (Integer)oI.next();

      if (poObject == moContainer.get(oKey)) {
        remove(oKey);
        break;
      }
    }
  }
  @Override
  public void remove(int pnKey) {
    remove(pnKey);
  }
  public void remove(Integer poKey) {
    moContainer.remove(poKey);
  }
  @Override
  public void removeAll() {
    moContainer.clear();
  }

  public Set getKeySet() {
    return moContainer.keySet();
  }

  @Override
  public int size() {
    return moContainer.size();
  }

  @Override
  public String toString() {
    String oResult = "";

    Set oTemp = moContainer.keySet();

    oResult = "["+size()+"]\n";

    if (oTemp.size()>0) {
      Iterator oI = oTemp.iterator();
      while (oI.hasNext()) {
        Integer oKey = (Integer)oI.next();

        oResult += " "+oKey+":";
        oResult += gettoString(moContainer.get(oKey))+"\n";
      }
    }

   return oResult;
  }

  @Override
  protected abstract String gettoString(Object poObject);

};
