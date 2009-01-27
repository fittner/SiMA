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

// File clsMutableDouble.java
// May 16, 2006
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
public class clsMutableDouble extends clsCloneable {
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
    
    public void div(double prValue) {
    	m_value /= prValue;
    }
    public void mult(double prValue) {
    	m_value *= prValue;
    }    
    public void set(double prValue) {
      m_value = prValue;
    }

    public String toString() {
      return Double.toString(m_value);
    }
}
