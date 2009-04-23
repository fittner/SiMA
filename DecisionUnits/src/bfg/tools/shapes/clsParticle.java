// File clsParticle.java
// February 04, 2006
//

// Belongs to package
package bfg.tools.shapes;

// Imports
import java.text.NumberFormat;
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
public class clsParticle extends clsPoint implements Serializable {
  public float mrWeight;

  public clsParticle() {
    super();

    mrWeight = 0;
  }

  public clsParticle(float prX, float prY, float prWeight) {
    super(prX, prY);

    setWeight(prWeight);
  }
  
  public clsParticle(clsParticle poParticle) {
    super(poParticle);
    setWeight(poParticle.mrWeight);
  }

  public void setWeight(float prWeight) throws IllegalArgumentException {
    if (prWeight<0) {
      throw new IllegalArgumentException("prWeight >= 0");
    }
    mrWeight = prWeight;
  }

  public float normalize(float prNorm) {
    mrWeight = mrWeight / prNorm;

    return mrWeight;
  }
  
  public String toString() {
    String oResult = super.toString();

    NumberFormat nf = java.text.NumberFormat.getInstance( );
    nf.setMinimumFractionDigits( 3 );
    nf.setMaximumFractionDigits( 3 );

    oResult += " W:"+nf.format(mrWeight);

    return oResult;
  }

};
