// File clsMutableFloat.java
// August 24, 2006
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
public class clsMutableFloat  extends clsCloneable implements Serializable {
    private float m_value;
    
    /** Constructor */
    public clsMutableFloat(float value) {
        m_value = value;
    }
    
    /** Return int value. */
    public float floatValue() {
        return m_value;
    }

    public Float FloatValue() {
      return new Float(m_value);
    }
    

    public void add(float prValue) {
      m_value+=prValue;
    }
    public void set(float prValue) {
      m_value = prValue;
    }

    @Override
    public String toString() {
      return Float.toString(m_value);
    }
};
