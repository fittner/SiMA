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

import java.io.Serializable;

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
public class clsMutableInteger implements Cloneable, Serializable {
    /**
	 * TODO (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 13.07.2009, 19:01:14
	 */
	private static final long serialVersionUID = 1294667044504111789L;
	private int m_value;
    
	@Override
	public clsMutableInteger clone() {
	    try {
	      return (clsMutableInteger) super.clone();
	    } catch (CloneNotSupportedException e) {
	      return null;
	    }
	}    
    
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
    
    public void div(int pnValue) {
    	m_value /= pnValue;
    }
    public void mult(int pnValue) {
    	m_value *= pnValue;
    }
    
    public int set(int pnValue) {
      return m_value = pnValue;
    }

    @Override
    public String toString() {
      return Integer.toString(m_value);
    }
}
