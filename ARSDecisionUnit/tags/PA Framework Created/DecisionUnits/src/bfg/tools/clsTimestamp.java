// File clsTimestamp.java
// May 19, 2006
//

// Belongs to package
package bfg.tools;

// Imports
import java.io.Serializable;
import java.text.NumberFormat;

/**
 *
 * This is the class description ...
 *
 * $Revision: 572 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2007-05-31 10:56:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
 *
 */
public class clsTimestamp extends clsCloneable implements Serializable {
  public float mrTimeStamp;

  public clsTimestamp() {
    super();

    mrTimeStamp = 0;
  }
  public clsTimestamp(float prTimeStamp) {
    super();
    set(prTimeStamp);
  }
  public clsTimestamp(clsTimestamp poStamp) {
    super();
    set(poStamp);
  }

  public float getTimeDiff(clsTimestamp poStamp) {
    return mrTimeStamp - poStamp.mrTimeStamp;
  }
  public float getTimeDiff(float prStamp) {
    return mrTimeStamp - prStamp;
  }

  public void set(clsTimestamp poStamp) {
    mrTimeStamp = poStamp.mrTimeStamp;
  }
  public void set(float prStamp) {
    mrTimeStamp = prStamp;
  }

  @Override
  public String toString() {
    return ""+mrTimeStamp;
  }
  public String toString(int pnFractionDigits) {
    NumberFormat nf = java.text.NumberFormat.getInstance( );
    nf.setMinimumFractionDigits( pnFractionDigits );
    nf.setMaximumFractionDigits( pnFractionDigits );

    return nf.format(mrTimeStamp);
  }
};
