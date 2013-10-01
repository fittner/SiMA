/**
 * CHANGELOG
 *
 * 11.05.2013 hinterleitner - File created
 *
 */
package datatypes.helpstructures;
import java.lang.reflect.Method;

/**
 * DOCUMENT (hinterleitner) - insert description 
 * 
 * @author hinterleitner
 * 11.05.2013, 11:52:24
 * 
 */




public class clsPentagon<A, B, C, D, E> implements Cloneable  {
    public A a;
    public B b;
    public C c; 
    public D d;
    public E e;
 
    public clsPentagon(final A a, final B b, final C c, final D d, final E e) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
    }
    
    public static <A, B, C, D, E> clsPentagon<A, B, C, D, E> create(A a, B b, C c, D d, E e) {
        return new clsPentagon<A, B, C, D, E>(a, b, c, d, e);
    }
 
    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof clsPentagon))
            return false;
 
        final clsPentagon<?, ?, ?, ?, ?> other = (clsPentagon<?, ?, ?, ?, ?>) o;
        return equal(a, other.a) && equal(b, other.b) && equal(c, other.c) && equal(d, other.d) && equal(e, other.e);
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
        int hE = e == null ? 0 : e.hashCode();
 
        //*4,9, *5,2, *5,5
        return hA + (37 * hB) + (183 * hC) + (969 * hD) + (5329 * hE);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object clone() throws CloneNotSupportedException {
        clsPentagon<A, B, C, D, E> clon = null;
         try { 
           clon = (clsPentagon<A, B, C, D, E>) super.clone(); // unchecked warning
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
         try { 
             Class<?> clzz = this.e.getClass();
             Method   meth = clzz.getMethod("clone", new Class[0]);
             Object   dupl = meth.invoke(this.e, new Object[0]);
             clon.e = (E) dupl; // unchecked warning
           } catch (Exception e) {
             //...
           }
                    
         return clon;
    }    
    
    @Override
    public String toString() {
        return "["+a+", "+b+", "+c+", "+d+","+e+"]";
    }   
}