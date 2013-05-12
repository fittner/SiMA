// File clsValueLinear.java
// May 02, 2006
//

// Belongs to package
package bfg.tools;

// Imports
import java.io.Serializable;

/**
 *
 * This is the class description ...
 *
 * $Revision: 572 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2007-05-31 10:56:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
 *
 */
public class clsValueLinear extends clsValueFuzzy implements Serializable {

  public clsValueLinear(float prValue, float prMinValue, float prMaxValue, float prSigma) {
    super(-prMinValue, prMaxValue, prSigma);
    setValue(prValue);
  }

  public clsValueLinear(float prValue) {
    super(-1, 1, 0.01f);
    setValue(prValue);
  }


  public clsValueLinear(float prValue, float prSigma) {
    super(-1, 1, prSigma);
    setValue(prValue);
  }
 
  @Override
  public float getValue() {
    return mrValue;
  }



  @Override
  public float setValue(float prValue) {
    if (prValue < mrMinValue) {
      mrValue = mrMinValue; 
    } else if (prValue > mrMaxValue) {
      mrValue = mrMaxValue;
    } else {
      mrValue = prValue;
    }

    return mrValue;
  }
};
