/**
 * itfInspectorDescription.java: DecisionUnits - pa.interfaces._v38
 * 
 * @author deutsch
 * 19.04.2011, 10:04:51
 */
package pa._v38.interfaces;

/**
 * If this interface is implemented by a module, an inspector tab that displays the internal state of the module is added.
 * 
 * @author deutsch
 * 19.04.2011, 10:04:51
 * 
 */
public interface itfInspectorInternalState {
	/**
	 * A text-based view on the values of all internal member variables of the module is expected as return value of this method.
	 *
	 * @since 12.07.2011 11:32:43
	 *
	 * @return
	 */
	public String stateToTEXT();
}
