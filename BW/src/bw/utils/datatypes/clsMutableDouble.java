/**
 * clsMutableDouble.java: BW - bw.utils.datatypes
 * 
 * @author tobias
 * Jul 29, 2009, 6:50:03 PM
 */
package bw.utils.datatypes;

import java.io.Serializable;

/**
 * The clsMutableDouble class wraps a value of the primitive type double in an object. An object of 
 * type clsMutableDouble contains a single field whose type is double. The value is mutable.
 * 
 * @author tobias
 * Jul 29, 2009, 7:38:20 PM
 * 
 */
public class clsMutableDouble implements Serializable, Comparable<clsMutableDouble> {
	private static final long serialVersionUID = 2937615057721679113L;
	private double m_value;
    
    /**
     * Constructs a newly allocated clsMutableDouble object that represents the primitive double argument. 
     * 
     * @author tobias
     * Jul 29, 2009, 7:53:36 PM
     *
     * @param value
     */
    public clsMutableDouble(double value) {m_value = value;}
    
    /**
     * Constructs a newly allocated clsMutableDouble object that represents the primitive double argument. 
     * 
     * @author tobias
     * Jul 29, 2009, 7:53:44 PM
     *
     * @param poValue
     */
    public clsMutableDouble(Double poValue) {m_value = poValue.doubleValue();}
    
    /**
     * Copy constructor. Constructs a newly allocated clsMutableDouble object that represents the primitive double argument. 
     * 
     * @author tobias
     * Jul 29, 2009, 7:53:46 PM
     *
     * @param poValue
     */
    public clsMutableDouble(clsMutableDouble poValue) {m_value = poValue.m_value;}
    
    /**
     * Returns the double value of this Double object. 
     *
     * @author tobias
     * Jul 29, 2009, 7:55:34 PM
     *
     * @return the double value represented by this object
     */
    public double doubleValue() {return m_value;}
    
    /**
     * Returns a new instance of a Double object containing the double value of this object.
     *
     * @author tobias
     * Jul 29, 2009, 7:56:35 PM
     *
     * @return a Double object representing the same double value as this object
     */
    public Double DoubleValue() {return new Double(m_value);} 

    /**
     * return m_value += prValue;
     *
     * @author tobias
     * Jul 29, 2009, 7:57:09 PM
     *
     * @param prValue
     * @return m_value += prValue;
     */
    public double add(double prValue) {return m_value += prValue;}
    
    /**
     * return m_value -= prValue;
     *
     * @author tobias
     * Jul 29, 2009, 7:57:18 PM
     *
     * @param prValue
     * @return m_value -= prValue;
     */
    public double sub(double prValue) {return m_value -= prValue;}
    
    /**
     * return m_value /= prValue;
     *
     * @author tobias
     * Jul 29, 2009, 7:57:16 PM
     *
     * @param prValue
     * @return m_value /= prValue;
     */
    public double div(double prValue) {return m_value /= prValue;}
    
    /**
     * return m_value *= prValue;
     *
     * @author tobias
     * Jul 29, 2009, 7:57:14 PM
     *
     * @param prValue
     * @return m_value *= prValue;
     */
    public double mul(double prValue) {return m_value *= prValue;}
    
    /**
     * return m_value =  prValue;
     *
     * @author tobias
     * Jul 29, 2009, 7:57:12 PM
     *
     * @param prValue
     * @return m_value =  prValue;
     */
    public double set(double prValue) {return m_value =  prValue;}

    public double add(clsMutableDouble poValue) {return add(poValue.doubleValue());}
    public double sub(clsMutableDouble poValue) {return sub(poValue.doubleValue());}
    public double div(clsMutableDouble poValue) {return div(poValue.doubleValue());}
    public double mul(clsMutableDouble poValue) {return mul(poValue.doubleValue());}
    public double set(clsMutableDouble poValue) {return set(poValue.doubleValue());}    
    
    public double add(Double poValue) {return add(poValue.doubleValue());}
    public double sub(Double poValue) {return sub(poValue.doubleValue());}
    public double div(Double poValue) {return div(poValue.doubleValue());}
    public double mul(Double poValue) {return mul(poValue.doubleValue());}
    public double set(Double poValue) {return set(poValue.doubleValue());}
    
    /* (non-Javadoc)
     * 
     * Returns a string representation of this Double object. The primitive double value 
     * represented by this object is converted to a string exactly as if by the method 
     * toString of one argument. 
     * 
     * @author tobias
     * Jul 29, 2009, 7:52:39 PM
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {return Double.toString(m_value);}
    
    /* (non-Javadoc)
     *
     * Compares this object against the specified object. The result is true if and only if 
     * the argument is not null and is a Double object that represents a double that has the 
     * same value as the double represented by this object. For this purpose, two double values 
     * are considered to be the same if and only if the method doubleToLongBits(double) returns 
     * the identical long value when applied to each. 
     * 
     * @author tobias
     * Jul 29, 2009, 7:51:20 PM
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     * @see java.lang.Double#equals(java.lang.Object)
     */
    @Override
	public boolean equals(Object obj) {
    	if (obj != null && 
  			obj instanceof clsMutableInteger && 
   			Double.doubleToLongBits(m_value) == Double.doubleToLongBits(((clsMutableDouble)obj).doubleValue())) {
      		return true;
      	} else if (obj != null && 
   			obj instanceof Integer && 
   			Double.doubleToLongBits(m_value) == Double.doubleToLongBits(((Double)obj).doubleValue())) {
       		return true;
       	}
    	return false;
    }
    
	/* (non-Javadoc)
	 *
	 * Compares the two specified double values. The sign of the integer value returned 
	 * is the same as that of the integer that would be returned by the call: 
	 * new Double(d1).compareTo(new Double(d2))
	 * 
	 * @author tobias
	 * Jul 29, 2009, 7:39:43 PM
	 * 
	 * @see java.lang.Double#compare(double, double)
	 */
	@Override
	public int compareTo(clsMutableDouble o) {
		return Double.compare(this.m_value, o.doubleValue());
	}
}
