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
	    /** Constructor */
	    public clsMutableDouble(Double poValue) {
	        m_value = poValue.floatValue();
	    }    
	    /** Copy Constructor */
	    public clsMutableDouble(clsMutableDouble poValue) {
	        m_value = poValue.m_value;
	    }
	    
	    public double doubleValue() {return m_value;}
	    public Double DoubleValue() {return new Double(m_value);} 

	    public void add(Float poValue) {add(poValue.floatValue());}
	    public void div(Float poValue) {div(poValue.floatValue());}
	    public void mult(Float poValue) {mult(poValue.floatValue());}
	    public void set(Float poValue) {set(poValue.floatValue());}
	    
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
