// File clsValueExponential.java
// May 02, 2006
//

// Belongs to package
package bfg.utils.datatypes;

// Imports

/**
 *
 * This is the class description ...
 *
 * Das ist in Wirklichkeit eine Abbildung! Gesettet wird mrValue (original),  
 * gegettet wird mrInternalValue (abgebildet). Die Abbildungsfunktion ist 
 * im Bereich [-1/2,+1/2] linear, auﬂerhalb irgendeine das Wachstum begrenzende
 * Exponentialfunktion.
 *
 * $Revision: 572 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2007-05-31 10:56:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
 *
 */
public class clsValueExponential extends clsValueFuzzy {
  float mrInternalValue = 0;
  static final float mrLinearPoint = 0.5f;

  public clsValueExponential(float prValue) {
    super(-1, 1, 0.01f);
    setValue(prValue);
  }

  public clsValueExponential(float prValue, float prSigma) {
    super(-1, 1, prSigma);
    setValue(prValue);
  }

  public clsValueExponential(clsValueExponential poValue) {
    super(-1, 1, poValue.mrSigma);
    mrInternalValue = poValue.mrInternalValue;
    mrValue = poValue.mrValue;
  }
 
  public float getValue() {
    return mrInternalValue;
  }

  private float calcInternalValue() {
    if (mrValue < mrLinearPoint) {
      mrInternalValue = mrValue;
    } else {
      mrInternalValue = (1-(float)Math.exp(-(mrValue-mrLinearPoint) * 2)) * (1-mrLinearPoint) + mrLinearPoint;
    }
    return mrInternalValue;
  }

  public float setValue(float prValue) {
    mrValue = prValue;

    return calcInternalValue();
  }

  public void set(clsValueExponential poValue) {
    mrSigma         = poValue.mrSigma;
    mrInternalValue = poValue.mrInternalValue;
    mrValue         = poValue.mrValue;
  }
};
