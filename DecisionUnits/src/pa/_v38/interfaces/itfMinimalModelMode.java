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
	public void setMinimalModelMode(boolean pnMinial);
	public boolean getMinimalModelMode();
}
