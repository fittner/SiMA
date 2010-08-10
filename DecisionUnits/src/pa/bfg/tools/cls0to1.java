// File cls0to1.java
// May 02, 2006
//

// Belongs to package
package pa.bfg.tools;

// Imports
import java.io.Serializable;

/**
 *
 * This is the class description ...
 *
 * $Revision: 956 $:  Revision of last commit
 * $Author: gartner $: Author of last commit
 * $Date: 2008-03-11 12:33:01 +0100 (Di, 11 Mär 2008) $: Date of last commit
 * @deprecated
 */
public class cls0to1 extends clsCloneable implements Serializable {
  double mrValue = 0;
  double mrMinValue = 0;
  double mrMaxValue = 1;

  public cls0to1() {
  }

  public cls0to1(cls0to1 poValue) {
    set(poValue);
  }

    public cls0to1(double prValue) {
	  //if (prValue <= 1 && prValue >= 0) commented (langr) -> check already done in set-method  
		  set(prValue);
  }  
  
  public cls0to1(Double prValue) {
	  set(prValue.doubleValue());
	  //line above was former: "new cls0to1(prValue.doubleValue());" -- (langr) changed it
  }
 
  public double set(cls0to1 poValue) {
    return set(poValue.get());
  }
  public double set(double prValue) {
    if (prValue > mrMaxValue) {
      mrValue = mrMaxValue;
    } else if (prValue < mrMinValue) {
      mrValue = mrMinValue;
    } else {
      mrValue = prValue;
    }

    return mrValue;
  }

  public double get() {
    return mrValue;
  }

  
  @Override
  public String toString() {
    return ""+mrValue;
  }
}
