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

// File clsMutableInteger.java
// December 02, 2005
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
public class clsMutableInteger extends clsCloneable {
    private int m_value;
    
    /** Constructor */
    public clsMutableInteger(int value) {
        m_value = value;
    }
    public clsMutableInteger(clsMutableInteger oValue) {
        m_value = oValue.intValue();
    }
    
    /** Return int value. */
    public int intValue() {
        return m_value;
    }

    public Integer IntegerValue() {
      return new Integer(m_value);
    }
    
    /** Increment value */
    public int inc() {
        return m_value++;
    }

    public int add(int pnValue) {
      return m_value+=pnValue;
    }
    public int set(int pnValue) {
      return m_value = pnValue;
    }

    public String toString() {
      return Integer.toString(m_value);
    }
}
