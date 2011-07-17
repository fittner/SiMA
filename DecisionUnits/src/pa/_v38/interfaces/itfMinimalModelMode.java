/**
 * itfMinimalModelMode.java: DecisionUnits - pa._v38.interfaces
 * 
 * @author deutsch
 * 21.04.2011, 19:00:42
 */
package pa._v38.interfaces;

/**
 * applied to modules which are candidates to be turned of when model is used in minimal mode. see t.deutsch phd chapter 3.5 for more details. 
 * 
 * @author deutsch
 * 21.04.2011, 19:00:42
 * 
 */
public interface itfMinimalModelMode {
	/**
	 * Setter for the minimal mode.
	 *
	 * @since 12.07.2011 11:27:58
	 *
	 * @param pnMinial
	 */
	public void setMinimalModelMode(boolean pnMinial);
	
	/**
	 * Getter for the minimal mode.
	 *
	 * @since 12.07.2011 11:28:00
	 *
	 * @return
	 */
	public boolean getMinimalModelMode();
}
