/**
 * clsTripple.java: DecisionUnits - pa.tools
 * 
 * @author deutsch
 * 07.10.2009, 18:45:06
 */
package pa.tools;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 07.10.2009, 18:45:06
 * 
 */
public class clsTripple<A, B, C> {
	public final A a;
	public final B b;
	public final C c;	
 
    public clsTripple(final A a, final B b, final C c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
    
    public static <A, B, C> clsTripple<A, B, C> create(A a, B b, C c) {
        return new clsTripple<A, B, C>(a, b, c);
    }
 
    @SuppressWarnings("unchecked")
	@Override
	public final boolean equals(Object o) {
        if (!(o instanceof clsPair))
            return false;
 
        final clsTripple<?, ?, ?> other = (clsTripple) o;
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
}