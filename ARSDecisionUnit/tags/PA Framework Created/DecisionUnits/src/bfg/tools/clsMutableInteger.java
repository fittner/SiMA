// File clsMutableInteger.java
// December 02, 2005
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
public class clsMutableInteger extends clsCloneable implements Serializable {
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

    @Override
    public String toString() {
      return Integer.toString(m_value);
    }
}
