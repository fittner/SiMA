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

// File clsValueExponential.java
// May 02, 2006
//

// Belongs to package
package bw.utils.datatypes;

// Imports

/**
 *
 * This is the class description ...
 *
 * @deprecated
 * 
 * Das ist in Wirklichkeit eine Abbildung! Gesettet wird mrValue (original),  
 * gegettet wird mrInternalValue (abgebildet). Die Abbildungsfunktion ist 
 * im Bereich [-1/2,+1/2] linear, au�erhalb irgendeine das Wachstum begrenzende
 * Exponentialfunktion.
 *
 * $Revision$:  Revision of last commit
 * $Author$: Author of last commit
 * $Date$: Date of last commit
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
 
  @Override
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

  @Override
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
