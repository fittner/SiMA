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
    /** Constructor */
    public clsMutableFloat(Float poValue) {
        m_value = poValue.floatValue();
    }    
    /** Copy Constructor */
    public clsMutableFloat(clsMutableFloat poValue) {
        m_value = poValue.m_value;
    }
    
    public float floatValue() {return m_value;}
    public Float FloatValue() {return new Float(m_value);} 

    public void add(Float poValue) {add(poValue.floatValue());}
    public void div(Float poValue) {div(poValue.floatValue());}
    public void mult(Float poValue) {mult(poValue.floatValue());}
    public void set(Float poValue) {set(poValue.floatValue());}
    
    public void add(clsMutableFloat poValue) {add(poValue.m_value);}
    public void div(clsMutableFloat poValue) {div(poValue.m_value);}
    public void mult(clsMutableFloat poValue) {mult(poValue.m_value);}
    public void set(clsMutableFloat poValue) {set(poValue.m_value);}    
    
    public void add(float prValue) {m_value+=prValue;}
    public void div(float prValue) {m_value /= prValue;}
    public void mult(float prValue) {m_value *= prValue;}
    public void set(float prValue) {m_value = prValue;}

    @Override
    public String toString() {
      return Float.toString(m_value);
    }
};
