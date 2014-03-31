/**
 * 
 */
package du.itf.sensors;

/**
 * @author langr
 *
 * Base class for all internal, body-realated sensors like the fast messenger system 
 * (nervous system) for pain-reception.
 * 
 * This does NOT include sensors, directly related to homeostasis --> clsSensorHomeostasis
 */
abstract public class clsSensorIntern extends clsDataBase implements Cloneable {
	@Override
	public Object clone() throws CloneNotSupportedException {
        try {
        	clsSensorIntern oClone = (clsSensorIntern)super.clone();

        	return oClone;
        } catch (CloneNotSupportedException e) {
           return e;
        }
	}	
}
