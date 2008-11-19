// File cls0to1.java
// May 02, 2006
//

// Belongs to package
package pkgTools;

// Imports
import java.io.Serializable;

/**
 *
 * This is the class description ...
 *
 * $Revision: 956 $:  Revision of last commit
 * $Author: gartner $: Author of last commit
 * $Date: 2008-03-11 12:33:01 +0100 (Di, 11 Mär 2008) $: Date of last commit
 *
 */
public class cls0to1 extends clsCloneable implements Serializable {
  float mrValue = 0;
  float mrMinValue = 0;
  float mrMaxValue = 1;

  public cls0to1() {
  }

  public cls0to1(cls0to1 poValue) {
    set(poValue);
  }

    public cls0to1(float prValue) {
	  if (prValue <= 1 && prValue >= 0)  
		  set(prValue);
  }  
  
  public cls0to1(Float prValue) {
	  new cls0to1(prValue.floatValue());
  }
 
  public float set(cls0to1 poValue) {
    return set(poValue.get());
  }
  public float set(float prValue) {
    if (prValue > mrMaxValue) {
      mrValue = mrMaxValue;
    } else if (prValue < mrMinValue) {
      mrValue = mrMinValue;
    } else {
      mrValue = prValue;
    }

    return mrValue;
  }

  public float get() {
    return mrValue;
  }

  
  public String toString() {
    return ""+mrValue;
  }
}
