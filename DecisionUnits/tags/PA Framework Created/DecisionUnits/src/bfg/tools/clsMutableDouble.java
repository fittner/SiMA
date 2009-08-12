// File clsMutableDouble.java
// May 16, 2006
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
public class clsMutableDouble extends clsCloneable implements Serializable {
    private double m_value;
    
    /** Constructor */
    public clsMutableDouble(double value) {
        m_value = value;
    }
    
    /** Return int value. */
    public double doubleValue() {
        return m_value;
    }

    public Double DoubleValue() {
      return new Double(m_value);
    }
    

    public void add(double prValue) {
      m_value+=prValue;
    }
    public void set(double prValue) {
      m_value = prValue;
    }

    @Override
    public String toString() {
      return Double.toString(m_value);
    }
}
