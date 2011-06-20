/**
 * clsMutableInteger.java: BW - bw.utils.datatypes
 * 
 * @author deutsch
 * Jul 29, 2009, 6:50:03 PM
 */
package bw.utils.datatypes;

import java.io.Serializable;

/**
 * Constructs a newly allocated clsMutableInteger object that represents the specified int value. The value
 * of this object is mutable.
 * 
 * @author deutsch
 * Jul 29, 2009, 7:21:29 PM
 * 
 */
public class clsMutableInteger implements Serializable, Comparable<clsMutableInteger> {
	private static final long serialVersionUID = 1294667044504111789L;
	private int m_value;
    
    /**
     * Constructs a newly allocated clsMutableInteger object that represents the specified int value. 
     * 
     * @author deutsch
     * Jul 29, 2009, 7:31:50 PM
     *
     * @param the value to be represented by the clsMutableInteger object.
     */
    public clsMutableInteger(int value) {m_value = value;}
    
    /**
     * Constructs a newly allocated clsMutableInteger object that represents the int value of the passed 
     * Integer object. 
     * 
     * @author deutsch
     * Jul 29, 2009, 7:33:28 PM
     *
     * @param the value to be represented by the clsMutableInteger object.
     */
    public clsMutableInteger(Integer oValue) {m_value = oValue.intValue();}
    
    /**
     * Copy constructor. Constructs a newly allocated clsMutableInteger object that represents the int 
     * value of the passed clsMutableInteger object. 
     * 
     * @author deutsch
     * Jul 29, 2009, 7:34:11 PM
     *
     * @param  the value to be represented by the clsMutableInteger object.
     */
    public clsMutableInteger(clsMutableInteger oValue) {m_value = oValue.intValue();}
    
    /**
     * Returns the value of this clsMutableInteger as an int. 
     *
     * @author deutsch
     * Jul 29, 2009, 7:29:36 PM
     *
     * @return the numeric value represented by this object after conversion to type int.
     */
    public int intValue() {return m_value;}
    
    /**
     * Returns a new instance of an Integer object containing the int value of this object.
     *
     * @author deutsch
     * Jul 29, 2009, 7:30:16 PM
     *
     * @return an Integer object representing the same int value as this object
     */
    public Integer IntegerValue() {return new Integer(m_value);}
    
    /**
     * return m_value++;
     *
     * @author deutsch
     * Jul 29, 2009, 7:34:59 PM
     *
     * @return m_value++; int value
     */
    public int inc() {return m_value++;}
    
    /**
     * return m_value--;
     *
     * @author deutsch
     * Jul 29, 2009, 7:35:14 PM
     *
     * @return m_value--; int value
     */
    public int dec() {return m_value--;}

    /**
     * return m_value += pnValue;
     *
     * @author deutsch
     * Jul 29, 2009, 7:35:35 PM
     *
     * @param pnValue
     * @return m_value += pnValue; int value
     */
    public int add(int pnValue) {return m_value += pnValue;}
    
    /**
     * return m_value -= pnValue;
     *
     * @author deutsch
     * Jul 29, 2009, 7:35:41 PM
     *
     * @param pnValue
     * @return m_value -= pnValue; int value
     */
    public int sub(int pnValue) {return m_value -= pnValue;}
    
    /**
     * return m_value /= pnValue;
     *
     * @author deutsch
     * Jul 29, 2009, 7:35:39 PM
     *
     * @param pnValue
     * @return m_value /= pnValue; int value
     */
    public int div(int pnValue) {return m_value /= pnValue;}
    
    /**
     * return m_value *= pnValue;
     *
     * @author deutsch
     * Jul 29, 2009, 7:36:06 PM
     *
     * @param pnValue
     * @return m_value *= pnValue; int value
     */
    public int mul(int pnValue) {return m_value *= pnValue;}
    
    /**
     * return m_value =  pnValue;
     *
     * @author deutsch
     * Jul 29, 2009, 7:35:37 PM
     *
     * @param pnValue
     * @return m_value =  pnValue; int value
     */
    public int set(int pnValue) {return m_value =  pnValue;}

    public int add(clsMutableInteger pnValue) {return add(pnValue.intValue());}
    public int sub(clsMutableInteger pnValue) {return sub(pnValue.intValue());}
    public int div(clsMutableInteger pnValue) {return div(pnValue.intValue());}
    public int mul(clsMutableInteger pnValue) {return mul(pnValue.intValue());}
    public int set(clsMutableInteger pnValue) {return set(pnValue.intValue());}
    
    public int add(Integer pnValue) {return add(pnValue.intValue());}
    public int sub(Integer pnValue) {return sub(pnValue.intValue());}
    public int div(Integer pnValue) {return div(pnValue.intValue());}
    public int mul(Integer pnValue) {return mul(pnValue.intValue());}
    public int set(Integer pnValue) {return set(pnValue.intValue());}
    
    /* (non-Javadoc)
     *
     * Returns a String object representing this Integer's value. The value is converted 
     * to signed decimal representation and returned as a string, exactly as if the integer 
     * value were given as an argument to the toString(int) method. 
     *
     * @author deutsch
     * Jul 29, 2009, 7:29:00 PM
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {return Integer.toString(m_value);}
    
    /**
     * Returns true if and only if the argument is not null and is an Integer or 
     * an clsMutableInteger object that represents the same int value as this object. 
     *
     * @author deutsch
     * Jul 29, 2009, 7:02:03 PM
     *
     * @param obj - the object to compare with. 
     * @return true if the Integer objects represent the same value; false otherwise.
     */    
    @Override
	public boolean equals(Object obj) {
    	if (obj != null && 
   			obj instanceof clsMutableInteger && 
   			m_value == ((clsMutableInteger)obj).intValue()) {
    		return true;
    	} else if (obj != null && 
   			obj instanceof Integer && 
  			m_value == ((Integer)obj).intValue()) {
      		return true;
       	}
    	
    	return false;
    }
    
	/* (non-Javadoc)
	 * 
	 * returns the value 0 if the argument is a Integer numerically equal to this Integer; a value 
	 * less than 0 if the argument is a Integer numerically greater than this Integer; and a value 
	 * greater than 0 if the argument is a Integer numerically less than this Integer.
	 *  
	 * @author deutsch
	 * Jul 29, 2009, 7:24:53 PM
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(clsMutableInteger arg0) {
		if (arg0.intValue() == m_value) {
			return 0;
		} else if (arg0.intValue() > m_value) {
			return -1;
		} else {
			return 1;
		}
	}
}
