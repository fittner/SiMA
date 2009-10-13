/**
 * clsPair.java: DecisionUnits - pa.tools
 * 
 * @author langr
 * 07.10.2009, 12:51:03
 */
package pa.tools;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 07.10.2009, 12:51:03
 * 
 */
public class clsPair<L, R> {
 
	public final L left;
	public final R right;
 
    public clsPair(final L left, final R right) {
        this.left = left;
        this.right = right;
    }
    
    public static <L, R> clsPair<L, R> create(L left, R right) {
        return new clsPair<L, R>(left, right);
    }
 
    @SuppressWarnings("unchecked")
	@Override
	public final boolean equals(Object o) {
        if (!(o instanceof clsPair))
            return false;
 
        final clsPair<?, ?> other = (clsPair) o;
        return equal(left, other.left) && equal(right, other.right);
    }
    
    public static final boolean equal(Object o1, Object o2) {
        if (o1 == null) {
            return o2 == null;
        }
        return o1.equals(o2);
    }
 
    @Override
	public int hashCode() {
        int hLeft = left == null ? 0 : left.hashCode();
        int hRight = right == null ? 0 : right.hashCode();
 
        return hLeft + (37 * hRight);
    }
}