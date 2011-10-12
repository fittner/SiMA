/**
 * CHANGELOG
 *
 * 12.10.2011 zottl		- Properly defined send_D3_1 (return double instead of void)
 * 										- Changed wrong int parameter do be used as an index instead
 * 										-	Documented send_D3_1 
 * 14.07.2011 deutsch - File created
 */
package pa._v38.interfaces.modules;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 14.07.2011, 15:58:23
 * 
 */
public interface D3_1_send {
	
	/**
	 * Allows modules to access the buffer (DT3) of free psychic energy. The calling
	 * module is required to provide its module number as an argument to this method.
	 * The module number is used as an index to look up the buffer for this specific
	 * module. If there is no buffer for this number, the return value will always
	 * be zero.
	 * 
	 * @author Marcus Zottl (e0226304)
	 * @since 12.10.2011 15:18:07
	 *
	 * @param pnModuleNr - The module number of the calling module.
	 * @return the amount of psychic energy available to this module or zero
	 * in case the <i>pnModuleNr</i> is not found
	 */
	public double send_D3_1(int pnModuleNr);
}
