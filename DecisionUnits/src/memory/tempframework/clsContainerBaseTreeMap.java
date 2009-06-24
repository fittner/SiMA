// File clsContainerBaseTreeMap.java
// May 15, 2006
//

// Belongs to package
package memory.tempframework;

import bw.memory.tempframework.clsMutableInteger;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

/**
 * This is the class description ... $Revision: 572 $:  Revision of last commit $Author: deutsch $: Author of last commit $Date: 2007-05-31 10:56:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
 */
abstract public class clsContainerBaseTreeMap extends clsContainerBase {
  protected TreeMap moContainer;
  protected clsMutableInteger moMaxKey;

  public clsContainerBaseTreeMap() {
    super();
    moContainer = new TreeMap();
    moMaxKey = new clsMutableInteger(-1);
  }

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
  public void remove(int pnKey) {
    remove(pnKey);
  }
  public void remove(Integer poKey) {
    moContainer.remove(poKey);
  }
  public void removeAll() {
    moContainer.clear();
  }

  public Set getKeySet() {
    return moContainer.keySet();
  }

  public int size() {
    return moContainer.size();
  }

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

  protected abstract String gettoString(Object poObject);

};
