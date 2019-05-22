/**
 * clsPair.java: DecisionUnits - pa.tools
 * 
 * @author langr
 * 07.10.2009, 12:51:03
 */
package base.datatypes.helpstructures;

import java.lang.reflect.Method;


/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 07.10.2009, 12:51:03
 * 
 * doucmentation by AP:
 * Class Pair is a generic class that can be used to store two datatypes which are connected to each other by the context
 * for example a word and a thing presentation can be stored as a unique data-set. constructor checks that left and right 
 * are not null. this is not checked afterwards. thus, if b is set to null for an already created object, it will not be 
 * checked. this is a design descision - the class should be as simple as possible without the use of getter and setter
 * methods.
 * 
 */
public class clsPair<A, B> implements Cloneable {
 
	public A a;
	public B b;
 
    public clsPair(final A left, final B right) {
    	this.a = left;
        this.b = right;
    }
    


    public static <L, R> clsPair<L, R> create(L left, R right) {
        return new clsPair<L, R>(left, right);
    }
    
   	@Override
	public final boolean equals(Object o) {
        if (!(o instanceof clsPair))
            return false;
 
        final clsPair<?, ?> other = (clsPair<?, ?>) o;
        return equal(a, other.a) && equal(b, other.b);
    }
    
    public static final boolean equal(Object o1, Object o2) {
        if (o1 == null) {
            return o2 == null;
        }
        return o1.equals(o2);
    }
 
    @Override
	public int hashCode() {
        int hLeft = a == null ? 0 : a.hashCode();
        int hRight = b == null ? 0 : b.hashCode();
 
        return hLeft + (37 * hRight);
    }
    
	
	@SuppressWarnings("unchecked")
	@Override
	public Object clone() throws CloneNotSupportedException {
		 clsPair<A, B> clon = null;
	     try { 
	       clon = (clsPair<A, B>) super.clone(); // unchecked warning
	     } catch (CloneNotSupportedException e) { 
	       throw e; 
	     }
	     try { 
	       if(a != null) {
    	     Class<?> clzz = this.a.getClass();
    	     Method   meth = clzz.getMethod("clone", new Class[0]);
    	     Object   dupl = meth.invoke(this.a, new Object[0]);
    	     clon.a = (A) dupl; // unchecked warning
	       }
	     } catch (Exception e) {
	       //...
	     }
	     try {
	       if(b != null) {
	         Class<?> clzz = this.b.getClass();
	         Method   meth = clzz.getMethod("clone", new Class[0]);
	         Object   dupl = meth.invoke(this.b, new Object[0]);
	         clon.b = (B) dupl; // unchecked warning
	       }
	     } catch (Exception e) {
	       //...
	     } 
		
	     return clon;
	}
	    
	@Override
	public String toString() {
		return "["+a+", "+b+"]";
	}
}