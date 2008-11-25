// File clsMutableFloat.java
// August 24, 2006
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
public class clsMutableFloat  extends clsCloneable {
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

    public String toString() {
      return Float.toString(m_value);
    }
};
