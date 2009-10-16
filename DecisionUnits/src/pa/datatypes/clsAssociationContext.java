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
	
	@Override
	public String toString() {
		return moAssociationContext.toString();
	}
		
}
