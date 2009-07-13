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
public class clsMutableBoolean extends clsCloneable implements Serializable {
    private boolean m_value;
    
    /** Constructor */
    public clsMutableBoolean(boolean value) {
        m_value = value;
    }
    public clsMutableBoolean(clsMutableBoolean oValue) {
        m_value = oValue.boolValue();
    }
    
    /** Return int value. */
    public boolean boolValue() {
        return m_value;
    }

    public Boolean BooleanValue() {
      return new Boolean(m_value);
    }
    
    public boolean set(boolean pnValue) {
      return m_value = pnValue;
    }

    @Override
    public String toString() {
      return Boolean.toString(m_value);
    }

    public boolean isTrue() {
      return m_value;
    }
}
