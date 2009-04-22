// File clsValueFuzzy.java
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
abstract class clsValueFuzzy extends clsCloneable implements Serializable {
  float mrMinValue = 0;
  float mrMaxValue = 0;
  float mrSigma = 0;

  static final float mrEpsilon = 0.00001f;

  float mrValue = 0;

  public clsValueFuzzy(float prMinValue, float prMaxValue, float prSigma) {
    mrMinValue = prMinValue;
    mrMaxValue = prMaxValue;
    mrSigma    = prSigma;
  }

  public abstract float getValue();
  public abstract float setValue(float prValue);

  public float addValue(float prValue) {
    return setValue(mrValue + prValue);
  }

  public String toString(boolean pnFull) {
    if (pnFull) {
      return "v:"+getValue()+" vi:"+mrValue+" s:"+mrSigma+" min:"+mrMinValue+" max:"+mrMaxValue+" area:"+getArea()+" from:"+getLowerBound()+" to:"+getUpperBound();
    } else {
      return ""+getValue();
    }
  }

  public float getArea() {
    return 1 * mrSigma;
  }

  public float getLowerBound() {
    return getValue() - mrSigma;
  }
  public float getUpperBound() {
    return getValue() + mrSigma;
  }
  public float getSigma() {
    return mrSigma;
  }
  public void setSigma(float prSigma) {
    mrSigma = prSigma;
  }


  //TODO: Das untere ist grober Unsinn: rHA geht nicht in das Ergebnis der Berechnung ein!
  //      Irgend jemand kann da die Fläche von einem Dreieck nicht ausrechnen! 
  private float getArea(float rA, float rHA, float rH) {
    // rA ... untere kantenlänge
    // rHA ... distanz vom linken eck von rA zur höhensymmetrale
    // rH ... höhe des dreiecks auf rA bezogen
 
	float rArea = 0;

    rArea += (rHA * rH) / 2.0;
    rArea += ((rA-rHA) * rH) / 2.0;

    return rArea;
  }

  private float calcSingleIntersectionArea(float prX1, float prSigma1, float prX2, float prSigma2) {
      float rK1 = -1.0f / prSigma1;
      float rD1 = rK1 * ( (prX2 - prSigma2) - prX1 ) + 1.0f;

      float rK2 = 1.0f / prSigma2;
      float rD2 = 0.0f;

      float rX = (rD2 - rD1) / (rK1 - rK2);
      float rY = rK2 * rX + rD2;

      float rArea = getArea( ( (prX1+prSigma1) - (prX2-prSigma2) ), rX, rY);

      return rArea;
  }

  private float calcFloatIntersectionArea(float prX1, float prSigma1, float prX2, float prSigma2) {
    // this function is an approximation to the real intersection area!!!
    // the intersection between one slope of x1 and the exact value of x2 is taken to determine a "trapez".
    // for the real value, the intersection between one slope of x1 and the two slopes of x2 should be 
    // calculated. the lower y value is taken as hight for the trapez and this hight, the upper width
    // of the trapez and difference between the lower and the hight intersection point for the triangle
    // on top of the trapez ...

    float rArea = 0.0f;
    float rHeight = 0.0f;

    if (prX1 < prX2) {
      float rK1 = -1.0f / prSigma1;
      float rD1 = 1.0f;
      float rX = prX2 - prX1;

      rHeight = rK1 * rX + rD1;
    } else {
      float rK1 = 1.0f / prSigma1;
      float rD1 = 0.0f;

      float rX = prX2 - (prX1 - prSigma1);

      rHeight = rK1 * rX + rD1;
    }

    float rDX = rHeight * prSigma2;

    rArea = 0.5f * (2*prSigma2 + rDX*2) * rHeight;

    return rArea;
  }


  public cls0to1 getCongruence(clsValueFuzzy poOther) {
    cls0to1 oResult = new cls0to1(0.0f);

    if ( getLowerBound() > poOther.getUpperBound() ) {
      // other is completely smaller than me
      oResult.set(0);

    } else if ( getUpperBound() < poOther.getLowerBound() ) {
      // other is completely larger than me
      oResult.set(0);

    } else if (getValue() < (poOther.getValue()+mrEpsilon) && getValue() > (poOther.getValue()-mrEpsilon) ) {
      // both values are identical - we don't need mrSigma ...
      oResult.set(1);

    } else if ( (poOther.getLowerBound() > getLowerBound()) && ( (poOther.getLowerBound() < getUpperBound()) && (getUpperBound() < poOther.getUpperBound()) )  ) {
      // other is only with its lower bound within my bounderies 
      float rArea = calcSingleIntersectionArea( getValue(), getSigma(), poOther.getValue(), poOther.getSigma() );

      oResult.set( rArea / getArea() );

    } else if ( (poOther.getLowerBound() < getLowerBound()) && ( (getLowerBound() < poOther.getUpperBound()) && (poOther.getUpperBound() < getUpperBound()) )  ) {
      // other is only with its upper bound within my bounderies 
      float rArea = calcSingleIntersectionArea(poOther.getValue(), poOther.getSigma(), getValue(), getSigma() );

      oResult.set( rArea / getArea() );

    } else if ( (poOther.getLowerBound() > getLowerBound()) && (poOther.getUpperBound() < getUpperBound()) ) {
      // other is completely inside me
      float rArea = 0.0f;
 
      rArea = calcFloatIntersectionArea( getValue(), getSigma(), poOther.getValue(), poOther.getSigma() );

      oResult.set( rArea / getArea() );

    } else if ( (poOther.getLowerBound() < getLowerBound()) && (poOther.getUpperBound() > getUpperBound()) ) {
      // i am completely inside the other
      float rArea = 0.0f;

      rArea = calcFloatIntersectionArea( poOther.getValue(), poOther.getSigma(), getValue(), getSigma() );

      oResult.set( rArea / getArea() );

    } else {
      // undefined!!!!
      System.out.println("undefined triangle intersection (clsValueFuzzy.java) !!!!");
    }

    return oResult;
  }
};


