/**
 * clsAssociationContext.java: DecisionUnits - pa.memory
 * 
 * @author langr
 * 11.08.2009, 15:04:24
 */
package pa.datatypes;

import java.lang.reflect.Method;


/**
 *                  moAssociationContext
 *                          |
 *                          |
 *               moWeight   |
 *    moElementA  <---------------------> moElementB
 * 
 * The TYPE of this association can be either 
 * clsPrimaryInformation (incl. Mesh) or clsSecondaryInformation (incl. Mesh)
 * 
 * @author langr
 * 11.08.2009, 15:04:24
 * 
 */
public class clsAssociationContext<TYPE> extends clsAssociationWeighted<TYPE> implements Cloneable {

	public TYPE moAssociationContext;
	
	@SuppressWarnings("unchecked")
	@Override
	public Object clone() throws CloneNotSupportedException {
		clsAssociationContext<TYPE> clon = null;
	     try { 
	       clon = (clsAssociationContext<TYPE>) super.clone(); // unchecked warning
	     } catch (CloneNotSupportedException e) { 
	       throw e; 
	     }
	     try { 
	       Class<?> clzz = this.moAssociationContext.getClass();
	       Method   meth = clzz.getMethod("clone", new Class[0]);
	       Object   dupl = meth.invoke(this.moAssociationContext, new Object[0]);
	       clon.moAssociationContext = (TYPE) dupl; // unchecked warning
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
	@Override
	@SuppressWarnings("unchecked")
	public Object clone(Object obj_orig, Object obj_clon) throws CloneNotSupportedException {		
		clsAssociationContext<TYPE> clon = null;
	     try { 
	       clon = (clsAssociationContext<TYPE>) super.clone(obj_orig, obj_clon); // unchecked warning
	     } catch (CloneNotSupportedException e) { 
	       throw e; 
	     }
	     try { 
	       Class<?> clzz = this.moAssociationContext.getClass();
	       Method   meth = clzz.getMethod("clone", new Class[0]);
	       Object   dupl = meth.invoke(this.moAssociationContext, new Object[0]);
	       clon.moAssociationContext = (TYPE) dupl; // unchecked warning
	     } catch (Exception e) {
	       //...
	     }
		
	     return clon;
	}
	
	@Override
	public String toString() {
		return moAssociationContext.toString();
	}
		
}
