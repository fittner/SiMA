/**
 * clsMutableBoolean.java: BW - bw.utils.datatypes
 * 
 * @author deutsch
 * Jul 29, 2009, 6:50:03 PM
 */

package pa._v19.bfg.tools;

import java.io.Serializable;

/**
 * The Boolean class wraps a value of the primitive type boolean in an object. An object of 
 * type Boolean contains a single field whose type is boolean. The boolean value is mutable. 
 * 
 * @author deutsch
 * Jul 29, 2009, 6:49:44 PM
 *  @deprecated
 */
public class clsMutableBoolean implements Serializable {
	private static final long serialVersionUID = 729967101603040909L;
	private boolean m_value;

    /**
     * Allocates a Boolean object representing the value argument. 
     * 
     * @author deutsch
     * Jul 29, 2009, 6:51:47 PM
     *
     * @param value - the value of the Boolean.
     */
    public clsMutableBoolean(boolean value) {
        m_value = value;
    }
    /**
     * Copy constructor. Allocates a Boolean object representing the boolean value of the copied object oValue argument. 
     * 
     * @author deutsch
     * Jul 29, 2009, 6:51:50 PM
     *
     * @param oValue - the object to be copied.
     */
    public clsMutableBoolean(clsMutableBoolean oValue) {
        m_value = oValue.booleanValue();
    }
    

    /**
     * Returns the value of this Boolean object as a boolean primitive. 
     *
     * @author deutsch
     * Jul 29, 2009, 6:55:48 PM
     *
     * @return the primitive boolean value of this object.
     */
    public boolean booleanValue() {
        return m_value;
    }

    /**
     * Returns an immutable object of type java.lang.Boolean representing the same boolean primitive value as the current object.
     *
     * @author deutsch
     * Jul 29, 2009, 6:55:56 PM
     *
     * @return immutable object of type java.lang.Boolean
     */
    public Boolean BooleanValue() {
      return new Boolean(m_value);
    }
    
    /**
     * Sets the the boolean value of the object to the new value.
     *
     * @author deutsch
     * Jul 29, 2009, 6:55:54 PM
     *
     * @param pnValue - the new value 
     * @return the new value in the boolean primitive type
     */
    public boolean set(boolean pnValue) {
      return m_value = pnValue;
    }

    /* (non-Javadoc)
     *
     * @author deutsch
     * Jul 29, 2009, 6:55:52 PM
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
      return Boolean.toString(m_value);
    }

    /**
     * Returns true if the internal boolean value equals true. false otherwise.
     *
     * @author deutsch
     * Jul 29, 2009, 6:55:51 PM
     *
     * @return boolean primitive value 
     */
    public boolean isTrue() {
      return m_value;
    }
    
    /**
     * Returns true if and only if the argument is not null and is a Boolean or an 
     * clsMutableBoolean object that represents the same boolean value as this object. 
     *
     * @author deutsch
     * Jul 29, 2009, 7:02:03 PM
     *
     * @param obj - the object to compare with. 
     * @return true if the Boolean objects represent the same value; false otherwise.
     */
    @Override
	public boolean equals(Object obj) {
    	if (obj != null && 
   			obj instanceof clsMutableBoolean && 
   			m_value == ((clsMutableBoolean)obj).booleanValue()) {
    		return true;
    	} else if (obj != null && 
   			obj instanceof Boolean && 
  			m_value == ((Boolean)obj).booleanValue()) {
      		return true;
       	}
    	
    	return false;
    }
}
