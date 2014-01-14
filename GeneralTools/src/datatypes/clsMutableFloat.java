/**
 * clsMutableFloat.java: BW - bw.utils.datatypes
 * 
 * @author deutsch
 * Jul 29, 2009, 6:50:03 PM
 */
package datatypes;

import java.io.Serializable;

/**
 * The Float class wraps a value of primitive type float in an object. An object of type Float 
 * contains a single field whose type is float. The float value is mutable.
 * 
 * @author deutsch
 * Jul 29, 2009, 7:58:45 PM
 * 
 */
public class clsMutableFloat implements Serializable, Comparable<clsMutableFloat> {
	private static final long serialVersionUID = 6368289736751136777L;
	private float m_value;
    
    /**
     * Constructs a newly clsMutableFloat object that represents the argument converted to type float. 
     * 
     * @author deutsch
     * Jul 29, 2009, 8:05:28 PM
     *
     * @param the value to be represented by the clsMutableFloat.
     */
    public clsMutableFloat(float value) {m_value = value;}
    
    /**
     * Constructs a newly clsMutableFloat object that represents the argument converted to type float. 
     * 
     * @author deutsch
     * Jul 29, 2009, 8:05:28 PM
     *
     * @param the value to be represented by the clsMutableFloat.
     */
    public clsMutableFloat(Float poValue) {m_value = poValue.floatValue();}  
    
    /**
     * Copy constructor. Constructs a newly clsMutableFloat object that represents the argument converted to type float. 
     * 
     * @author deutsch
     * Jul 29, 2009, 8:05:28 PM
     *
     * @param the value to be represented by the clsMutableFloat.
     */
    public clsMutableFloat(clsMutableFloat poValue) {m_value = poValue.m_value;}
    
    /**
     * Returns the float value of this clsMutableFloat  object. 
     *
     * @author deutsch
     * Jul 29, 2009, 8:05:35 PM
     *
     * @return the float value represented by this object
     */
    public float floatValue() {return m_value;}
    
    /**
     * Returns a new instance of a Float object containing the float value of this object.
     *
     * @author deutsch
     * Jul 29, 2009, 7:56:35 PM
     *
     * @return a Float object representing the same float value as this object
     */
    public Float FloatValue() {return new Float(m_value);} 

    /**
     * return m_value += prValue;
     *
     * @author deutsch
     * Jul 29, 2009, 8:05:53 PM
     *
     * @param prValue
     * @return m_value += prValue;
     */
    public float add(float prValue) {return m_value += prValue;}
    
    /**
     * return m_value -= prValue;
     *
     * @author deutsch
     * Jul 29, 2009, 8:05:51 PM
     *
     * @param prValue
     * @return m_value -= prValue;
     */
    public float sub(float prValue) {return m_value -= prValue;}
    
    /**
     * return m_value /= prValue;
     *
     * @author deutsch
     * Jul 29, 2009, 8:05:49 PM
     *
     * @param prValue
     * @return m_value /= prValue;
     */
    public float div(float prValue) {return m_value /= prValue;}
    
    /**
     * return m_value *= prValue;
     *
     * @author deutsch
     * Jul 29, 2009, 8:05:45 PM
     *
     * @param prValue
     * @return m_value *= prValue;
     */
    public float mul(float prValue) {return m_value *= prValue;}
    
    /**
     * return m_value =  prValue;
     *
     * @author deutsch
     * Jul 29, 2009, 8:05:43 PM
     *
     * @param prValue
     * @return m_value =  prValue;
     */
    public float set(float prValue) {return m_value =  prValue;}
     
    public float add(clsMutableFloat poValue) {return add(poValue.floatValue());}
    public float sub(clsMutableFloat poValue) {return sub(poValue.floatValue());}
    public float div(clsMutableFloat poValue) {return div(poValue.floatValue());}
    public float mul(clsMutableFloat poValue) {return mul(poValue.floatValue());}
    public float set(clsMutableFloat poValue) {return set(poValue.floatValue());}    
    
    public float add(Float poValue) {return add(poValue.floatValue());}
    public float sub(Float poValue) {return sub(poValue.floatValue());}
    public float div(Float poValue) {return div(poValue.floatValue());}
    public float mul(Float poValue) {return mul(poValue.floatValue());}
    public float set(Float poValue) {return set(poValue.floatValue());}

    /* (non-Javadoc)
     *
     * Returns a string representation of this Float object. The primitive float value represented 
     * by this object is converted to a String exactly as if by the method toString of one argument. 
     * 
     * @author deutsch
     * Jul 29, 2009, 8:04:48 PM
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {return Float.toString(m_value);}
    
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * Jul 29, 2009, 7:40:56 PM
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * @see java.lang.Float#compare(float, float)
	 */
	@Override
	public int compareTo(clsMutableFloat o) {
		return Float.compare(this.m_value, o.floatValue());
	}
	
	/* (non-Javadoc)
	 *
	 * Compares this object against the specified object. The result is true if and only if the
     * argument is not null and is a Float object that represents a float with the same value as 
     * the float represented by this object. For this purpose, two float values are considered to
     * be the same if and only if the method floatToIntBits(float)  returns the identical int value 
     * when applied to each. 
     * 
	 * @author deutsch
	 * Jul 29, 2009, 7:59:37 PM
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 * @see java.lang.Float#equals(java.lang.Object)	 
	 */
	@Override
	public boolean equals(Object obj) {
    	if (obj != null && 
   			obj instanceof clsMutableInteger && 
   			Float.floatToIntBits(m_value) == Float.floatToIntBits(((clsMutableFloat)obj).floatValue())) {
      		return true;
       	} else if (obj != null && 
   			obj instanceof Integer && 
   			Float.floatToIntBits(m_value) == Float.floatToIntBits(((Float)obj).floatValue())) {
       		return true;
       	}
      	return false;		
	}
};
