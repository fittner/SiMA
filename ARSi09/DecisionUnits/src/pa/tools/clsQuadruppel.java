/**
 * clsQuadruppel.java: DecisionUnits - pa.tools
 * 
 * @author deutsch
 * 07.10.2009, 18:52:03
 */
package pa.tools;

import java.lang.reflect.Method;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 07.10.2009, 18:52:03
 * 
 */
public class clsQuadruppel<A, B, C, D> implements Cloneable  {
	public A a;
	public B b;
	public C c;	
	public D d;
 
    public clsQuadruppel(final A a, final B b, final C c, final D d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }
    
    public static <A, B, C, D> clsQuadruppel<A, B, C, D> create(A a, B b, C c, D d) {
        return new clsQuadruppel<A, B, C, D>(a, b, c, d);
    }
 
    @SuppressWarnings("unchecked")
	@Override
	public final boolean equals(Object o) {
        if (!(o instanceof clsPair))
            return false;
 
        final clsQuadruppel<?, ?, ?, ?> other = (clsQuadruppel) o;
        return equal(a, other.a) && equal(b, other.b) && equal(c, other.c) && equal(d, other.d);
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
        int hD = d == null ? 0 : d.hashCode();
 
        return hA + (37 * hB) + (183 * hC) + (969 * hD);
    }

	@SuppressWarnings("unchecked")
	@Override
	public Object clone() throws CloneNotSupportedException {
		clsQuadruppel<A, B, C, D> clon = null;
	     try { 
	       clon = (clsQuadruppel<A, B, C, D>) super.clone(); // unchecked warning
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
	     try {
	       Class<?> clzz = this.d.getClass();
	       Method   meth = clzz.getMethod("clone", new Class[0]);
	       Object   dupl = meth.invoke(this.d, new Object[0]);
	       clon.d = (D) dupl; // unchecked warning
	     } catch (Exception e) {
	       //...
	     }	     
					
	     return clon;
	}    
}