/**
 * clsTripple.java: DecisionUnits - pa.tools
 * 
 * @author deutsch
 * 07.10.2009, 18:45:06
 */
package pa._v38.tools;

import java.lang.reflect.Method;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 07.10.2009, 18:45:06
 * 
 */
public class clsTriple<A, B, C> implements Cloneable {
	public A a;
	public B b;
	public C c;	
 
    public clsTriple(final A a, final B b, final C c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
    
    public static <A, B, C> clsTriple<A, B, C> create(A a, B b, C c) {
        return new clsTriple<A, B, C>(a, b, c);
    }
 
	@Override
	public final boolean equals(Object o) {
        if (!(o instanceof clsTriple))
            return false;
 
        final clsTriple<?, ?, ?> other = (clsTriple<?, ?, ?>) o;
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
		clsTriple<A, B, C> clon = null;
	     try { 
	       clon = (clsTriple<A, B, C>) super.clone(); // unchecked warning
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