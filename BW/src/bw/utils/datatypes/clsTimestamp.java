// File clsTimestamp.java
// May 19, 2006
//

// Belongs to package
package bw.utils.datatypes;

// Imports
import java.text.NumberFormat;

/**
 *
 * This is the class description ...
 *
 * $Revision$:  Revision of last commit
 * $Author$: Author of last commit
 * $Date$: Date of last commit
 *
 */
public class clsTimestamp extends clsCloneable {
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
