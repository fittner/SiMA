// File clsContainerBaseVector.java
// May 15, 2006
//

// Belongs to package
package memory.tempframework;

// Imports
import java.util.Vector;
import java.util.Iterator;

/**
 *
 * This is the class description ...
 *
 * $Revision: 572 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2007-05-31 10:56:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
 *
 */
abstract public class clsContainerBaseVector extends clsContainerBase {
  protected Vector moContainer;
  protected int mnMaxSize = -1;

  public clsContainerBaseVector() {
    super();
    moContainer = new Vector();
  }
  public clsContainerBaseVector(int pnMaxSize) {
    super();
    moContainer = new Vector();
    setMaxSize(pnMaxSize);
  }

  public void setMaxSize(int pnMaxSize) {
    mnMaxSize = pnMaxSize;
    checkSize();  
  }

  public void add(Object poObject) {
    moContainer.add(poObject);
    checkSize();
  }

  public void add(int index, Object poObject) {
    moContainer.add(index, poObject);
    checkSize();
  }

  public boolean checkSize() 
  {
    boolean oResult = false;
    if (mnMaxSize > 0 && moContainer.size()>mnMaxSize && moContainer.size()>0) 
    {
      //Engine.log.println("remove list entry");
      moContainer.remove(0);
      oResult = true;
    }
    return oResult;
  }

  protected Object getObject(int pnPos) {
    Object oResult = null;

    oResult = moContainer.get(pnPos);

    return oResult;
  }

  public Vector getContainer() {
    return moContainer;
  }

  public void remove(Object poObject) {
    moContainer.removeElement(poObject);
  }

  public boolean removeIt(Object poObject) {
    return moContainer.removeElement(poObject);
  }

  public void remove(int pnPos) {
    moContainer.removeElementAt(pnPos);
  }
  public void removeAll() {
    moContainer.removeAllElements();
  }

  public int size() {
    return moContainer.size();
  }

  public String toString() {
    String oResult = "";

    oResult = "["+size()+"] ";

    if (moContainer.size()>0) {
      Iterator oI = moContainer.iterator();
      while (oI.hasNext()) {
        oResult += "\n "+gettoString(oI.next());
      }
    }

    return oResult;
  }


  protected abstract String gettoString(Object poObject);

};
