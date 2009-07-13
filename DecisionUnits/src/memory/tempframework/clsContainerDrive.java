// File clsContainerDrive.java
// May 02, 2006
//

// Belongs to package
package memory.tempframework;

// Imports
import java.util.Set;
import java.util.Iterator;

import memory.tempframework.clsContainerBaseTreeMap;

/**
 *
 * This is the class description ...
 *
 * $Revision: 627 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2007-06-25 12:20:22 +0200 (Mo, 25 Jun 2007) $: Date of last commit
 *
 */
public class clsContainerDrive extends clsContainerBaseTreeMap {

  public clsDrive get(int pnKey) {
    return (clsDrive)getObject(new Integer(pnKey));
  }
  public clsDrive get(Integer poKey) {
    return (clsDrive)moContainer.get(poKey);
  }

  @Override
  protected String gettoString(Object poObject) {
    return ((clsDrive)poObject).toString();
  }

  public void resetValues()
  {
    Set oTemp = moContainer.keySet();

    Iterator oI = oTemp.iterator();
    while (oI.hasNext()) {
      Integer oKey = (Integer)oI.next();

      ((clsDrive)moContainer.get(oKey)).setValue(0);
    }
  }
};
