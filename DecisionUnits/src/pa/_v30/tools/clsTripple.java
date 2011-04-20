/**
 * clsTripple.java: DecisionUnits - pa.tools
 * 
 * @author deutsch
 * 07.10.2009, 18:45:06
 */
package pa._v30.tools;

import java.lang.reflect.Method;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 07.10.2009, 18:45:06
 * 
 */
public class clsTripple<A, B, C> implements Cloneable {
	public A a;
	public B b;
	public C c;	
 
    public clsTripple(final A a, final B b, final C c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
    
    public static <A, B, C> clsTripple<A, B, C> create(A a, B b, C c) {
        return new clsTripple<A, B, C>(a, b, c);
    }
 
	@Override
	public final boolean equals(Object o) {
        if (!(o instanceof clsPair))
            return false;
 
        final clsTripple<?, ?, ?> other = (clsTripple<?, ?, ?>) o;
        return equal(a, other.a) && equal(b, other.b) && equal(c, other.c);
    }
    
    public static final boolean equal(Object o1, Object o2) {
        if (o1 == null) {
            return o2 == null;
        }
        return o1.equals(o2);
    }
 
    @Override
	public int hashCode() {
        int hA = a == null ? 0 : a.hashCode();
        int hB = b == null ? 0 : b.hashCode();
        int hC = c == null ? 0 : c.hashCode();
 
        return hA + (37 * hB) + (183 * hC);
    }
    
	@SuppressWarnings("unchecked")
	@Override
	public Object clone() throws CloneNotSupportedException {
		clsTripple<A, B, C> clon = null;
	     try { 
	       clon = (clsTripple<A, B, C>) super.clone(); // unchecked warning
	     } catch (CloneNotSupportedException e) { 
	       throw e; 
	     }
	     try { 
	       Class<?> clzz = this.a.getClass();
	       Method   meth = clzz.getMethod("clone", new Class[0]);
	       Object   dupl = meth.invoke(this.a, new Object[0]);
	       clon.a = (A) dupl; // unchecked warning
	     } catch (Exception e) {
	       //...
	     }
	     try {
	       Class<?> clzz = this.b.getClass();
	       Method   meth = clzz.getMethod("clone", new Class[0]);
	       Object   dupl = meth.invoke(this.b, new Object[0]);
	       clon.b = (B) dupl; // unchecked warning
	     } catch (Exception e) {
	       //...
	     } 
	     try {
	       Class<?> clzz = this.c.getClass();
	       Method   meth = clzz.getMethod("clone", new Class[0]);
	       Object   dupl = meth.invoke(this.c, new Object[0]);
	       clon.c = (C) dupl; // unchecked warning
	     } catch (Exception e) {
	       //...
	     }	     
		
	     return clon;
	}    
	
	@Override
	public String toString() {
		return "["+a+", "+b+", "+c+"]";
	}	
}