/**
 * clsQuadruppel.java: DecisionUnits - pa.tools
 * 
 * @author deutsch
 * 07.10.2009, 18:52:03
 */
package pa.tools;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 07.10.2009, 18:52:03
 * 
 */
public class clsQuadruppel<A, B, C, D> {
	public final A a;
	public final B b;
	public final C c;	
	public final D d;
 
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
}