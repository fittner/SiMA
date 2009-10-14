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

	public TYPE moElementA;
	public TYPE moElementB;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Object clone() throws CloneNotSupportedException {
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
	
}
