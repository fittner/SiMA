// File clsContainerEmotion.java
// May 02, 2006
//

// Belongs to package
package episodicMemory.tempframework;

// Imports
import java.util.Set;
import java.util.Iterator;
import episodicMemory.tempframework.clsContainerBaseTreeMap;

/**
 *
 * This is the class description ...
 *
 * $Revision: 690 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2007-07-12 18:54:27 +0200 (Do, 12 Jul 2007) $: Date of last commit
 *
 */
public class clsContainerEmotion extends clsContainerBaseTreeMap {

  public void set(clsContainerEmotion poEmotions) {
     moContainer.clear();

    Set oTemp = poEmotions.moContainer.keySet();

    Iterator oI = oTemp.iterator();
    while (oI.hasNext()) {
      Integer oKey = (Integer)oI.next();

      moContainer.put(new Integer(oKey.intValue()), (clsEmotion)poEmotions.moContainer.get(oKey));
    }
  }

  public clsEmotion get(int pnPos) {
    return (clsEmotion)getObject(pnPos);
  }
  public clsEmotion get(Integer poKey) {
    return (clsEmotion)moContainer.get(poKey);
  }

  @Override
  protected String gettoString(Object poObject) {
    return ((clsEmotion)poObject).toString();
  }

  public void resetValues()
  {
    Set oTemp = moContainer.keySet();

    Iterator oI = oTemp.iterator();
    while (oI.hasNext()) {
      Integer oKey = (Integer)oI.next();

      ((clsEmotion)moContainer.get(oKey)).setValue(0);
    }
  }

};
