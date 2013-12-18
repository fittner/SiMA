/**
 * CHANGELOG
 * 
 * 12.10.2011 zottl		- Changed parameter of receive_D3_2 from int to double
 * 										- Added second parameter used as index
 * 										- Documented receive_D3_2
 * 14.07.2011 deutsch - File created
 */
package modules.interfaces;

/**
 * DOCUMENT (deutsch) - interface for psychic energy
 * 
 * @author hinterleitner
 * 14.07.2011, 15:58:23
 * 
 */
public interface D3_2_receive {
	
	/**
	 * Allows modules to remove consumed psychic energy from the buffer (DT3).
	 * The calling module is required to provide its module number as an argument
	 * to this method. The module number is used as an index to look up the buffer
	 * for this specific module. If there is no buffer for this number, nothing
	 * happens.
	 * 
	 * @author Marcus Zottl (e0226304)
	 * @since 12.10.2011 15:07:23
	 * 
	 * @param prConsumedPsychicEnergy - the amount of consumed psychic energy that
	 * should be removed from the buffer.
	 * @param pnModuleNr - The module number of the calling module.
	 */
	public void receive_D3_2 (double prConsumedPsychicEnergy, int pnModuleNr);
}
