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
public class clsMutableBoolean extends clsCloneable {
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
