/**
 * @author deutsch
 * 
 * Copied from the old bubble family game
 * 
 * CHKME (deutsch) - keep this file?
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
public class clsMutableDouble implements Cloneable, Serializable {
	   /**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 13.07.2009, 19:00:32
	 */
	private static final long serialVersionUID = 2937615057721679113L;
	private double m_value;
	   
		@Override
		public clsMutableDouble clone() {
		    try {
		      return (clsMutableDouble) super.clone();
		    } catch (CloneNotSupportedException e) {
		      return null;
		    }
		}	   
	    
	    /** Constructor */
	    public clsMutableDouble(double value) {
	        m_value = value;
	    }
	    /** Constructor */
	    public clsMutableDouble(Double poValue) {
	        m_value = poValue.doubleValue();
	    }    
	    /** Copy Constructor */
	    public clsMutableDouble(clsMutableDouble poValue) {
	        m_value = poValue.m_value;
	    }
	    
	    public double doubleValue() {return m_value;}
	    public Double DoubleValue() {return new Double(m_value);} 

	    public void add(Double poValue) {add(poValue.doubleValue());}
	    public void div(Double poValue) {div(poValue.doubleValue());}
	    public void mult(Double poValue) {mult(poValue.doubleValue());}
	    public void set(Double poValue) {set(poValue.doubleValue());}
	    
	    public void add(clsMutableDouble poValue) {add(poValue.m_value);}
	    public void div(clsMutableDouble poValue) {div(poValue.m_value);}
	    public void mult(clsMutableDouble poValue) {mult(poValue.m_value);}
	    public void set(clsMutableDouble poValue) {set(poValue.m_value);}    
	    
	    public void add(double prValue) {m_value+=prValue;}
	    public void div(double prValue) {m_value /= prValue;}
	    public void mult(double prValue) {m_value *= prValue;}
	    public void set(double prValue) {m_value = prValue;}

	    @Override
	    public String toString() {
	      return Double.toString(m_value);
	    }
}
