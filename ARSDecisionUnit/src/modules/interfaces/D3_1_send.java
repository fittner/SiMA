/**
 * CHANGELOG
 *
 * 12.10.2011 zottl		- Properly defined send_D3_1 (return double instead of void)
 * 										- Changed wrong int parameter do be used as an index instead
 * 										-	Documented send_D3_1 
 * 14.07.2011 deutsch - File created
 */
package modules.interfaces;


public interface D3_1_send {
	
	/**
	 * Allows modules to access the buffer (DT3) of free psychic intensity. The calling
	 * module is required to provide its module number as an argument to this method.
	 * The module number is used as an index to look up the buffer for this specific
	 * module. If there is no buffer for this number, the requested buffer is created
	 * the returned intensity from the first request is always zero
	 * 
	 * @author Kogelnig Philipp (e0225185)
	 * @since 08.10.2012
	 * 
	 * @author Aldo Martinez Pinanez
	 * @since 13.02.2014
	 *
	 * @param pnModuleNr - The module number of the calling module.
	 * @return the amount of psychic intensity available to this module
	 */
	public double send_D3_1(int pnModuleNr);
}
