/**
 * clsAssociation.java: DecisionUnits - pa.memory
 * 
 * @author langr
 * 11.08.2009, 15:03:08
 */
package pa.datatypes;

import java.lang.reflect.Method;

/**
 *
 *    moElementA  <---------------------> moElementB
 * 
 * @author langr
 * 11.08.2009, 15:03:08
 * 
 */
public class clsAssociation<TYPE>  implements Cloneable {
	static final long mrMaxStackDepth = 5000;

	public TYPE moElementA;
	public TYPE moElementB;
	
	private void checkStackDepth() {
		long depth = Thread.currentThread().getStackTrace().length;
		
		if (depth > mrMaxStackDepth) {
			throw new StackOverflowError("max. call stack for clsAssociation<TYPE>.clone reached. either increase threshold constant or check code for an infinite clone loop. ("+depth+">"+mrMaxStackDepth+")");
		}
	}
	
	/* (non-Javadoc)
	 *
	 * IMPORTANT: be aware of infinite loops while cloning e.g. clsPrimaryInformationMesh. use other clone method instead
	 *
	 * @author deutsch
	 * 19.10.2009, 16:10:49
	 * 
	 * @see java.lang.Object#clone()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object clone() throws CloneNotSupportedException {
		checkStackDepth();
		
		clsAssociation<TYPE> clon = null;
	     try { 
	       clon = (clsAssociation<TYPE>) super.clone(); // unchecked warning
	     } catch (CloneNotSupportedException e) { 
	       throw e; 
	     }
	     try { 
	       Class<?> clzz = this.moElementA.getClass();
	       Method   meth = clzz.getMethod("clone", new Class[0]);
	       Object   dupl = meth.invoke(this.moElementA, new Object[0]);
	       clon.moElementA = (TYPE) dupl; // unchecked warning
	     } catch (Exception e) {
	       //...
	     }
	     try {
	       Class<?> clzz = this.moElementB.getClass();
	       Method   meth = clzz.getMethod("clone", new Class[0]);
	       Object   dupl = meth.invoke(this.moElementB, new Object[0]);
	       clon.moElementB = (TYPE) dupl; // unchecked warning
	     } catch (Exception e) {
	       //...
	     } 
		
	     return clon;
	}
	
	/**
	 * special clone method. prevents infinite loops while cloning object A which has association X with object B.
	 * A.clone() calls X.clone() which - consecutively - creates a new clone from A ... Thus, references to A and its clone A' 
	 * are passed to the clone method from X. If X.moA refers to A, it is redirected to A'; if X.moB refers to B, it is 
	 * redirected to A'.
	 *
	 * @author deutsch
	 * 19.10.2009, 16:17:12
	 *
	 * @param obj_orig
	 * @param obj_clon
	 * @return clone
	 * @throws CloneNotSupportedException
	 */
	@SuppressWarnings("unchecked")
	public Object clone(Object obj_orig, Object obj_clon) throws CloneNotSupportedException {
		checkStackDepth();
		
		clsAssociation<TYPE> clon = null;
	    try { 
	    	clon = (clsAssociation<TYPE>) super.clone(); // unchecked warning
	    } catch (CloneNotSupportedException e) { 
	    	throw e; 
	    }
	    try {
	    	if (this.moElementA.equals(obj_orig)) {
	    		clon.moElementA = (TYPE) obj_clon;
	    	} else {
	    		Class<?> clzz = this.moElementA.getClass();
	    		Method   meth = clzz.getMethod("clone", new Class[0]);
				Object   dupl = meth.invoke(this.moElementA, new Object[0]);
				clon.moElementA = (TYPE) dupl; // unchecked warning
	    	}
	    } catch (Exception e) {
	    	 //...
	    }
	    try {
	    	if (this.moElementB.equals(obj_orig)) {
	    		clon.moElementB = (TYPE) obj_clon;
	    	} else {	    	
				Class<?> clzz = this.moElementB.getClass();
				Method   meth = clzz.getMethod("clone", new Class[0]);
				Object   dupl = meth.invoke(this.moElementB, new Object[0]);
				clon.moElementB = (TYPE) dupl; // unchecked warning
	    	}
	    } catch (Exception e) {
	    	//...
	    } 
		
	    return clon;
	}	
}
