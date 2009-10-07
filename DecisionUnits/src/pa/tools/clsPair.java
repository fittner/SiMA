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
 
    private final L left;
    private final R right;
 
    public R getRight() {
        return right;
    }
 
    public L getLeft() {
        return left;
    }
 
    public clsPair(final L left, final R right) {
        this.left = left;
        this.right = right;
    }
    
    public static <A, B> clsPair<A, B> create(A left, B right) {
        return new clsPair<A, B>(left, right);
    }
 
    @Override
	public final boolean equals(Object o) {
        if (!(o instanceof clsPair))
            return false;
 
        final clsPair<?, ?> other = (clsPair) o;
        return equal(getLeft(), other.getLeft()) && equal(getRight(), other.getRight());
    }
    
    public static final boolean equal(Object o1, Object o2) {
        if (o1 == null) {
            return o2 == null;
        }
        return o1.equals(o2);
    }
 
    @Override
	public int hashCode() {
        int hLeft = getLeft() == null ? 0 : getLeft().hashCode();
        int hRight = getRight() == null ? 0 : getRight().hashCode();
 
        return hLeft + (57 * hRight);
    }
}