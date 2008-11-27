/**
 * @author deutsch
 * 
 * Copied from the old bubble family game
 * 
 * CHKME keep this file?
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */

// File clsValueLinear.java
// May 02, 2006
//

// Belongs to package
package bw.utils.datatypes;

// Imports

/**
 *
 * This is the class description ...
 *
 * $Revision$:  Revision of last commit
 * $Author$: Author of last commit
 * $Date$: Date of last commit
 *
 */
public class clsValueLinear extends clsValueFuzzy {

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
 
  public float getValue() {
    return mrValue;
  }

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
