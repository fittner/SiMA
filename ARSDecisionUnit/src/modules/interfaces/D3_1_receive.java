/**
 * CHANGELOG
 *
 * 12.10.2011 zottl		- Changed parameter of receive_D3_1 from int to double
 * 										- Documented receive_D3_1
 * 14.07.2011 deutsch - File created
 */
package modules.interfaces;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 14.07.2011, 15:58:23
 * 
 */
public interface D3_1_receive {
	
	
	/**
	 * Accepts freed psychic energy for buffering in DT3 and later distribution to
	 * other modules.
	 * 
	 * @author Marcus Zottl (e0226304)
	 * @since 12.10.2011 15:07:23
	 * 
	 * @param prFreePsychicEnergy - the amount of freed psychic energy that should
	 * be buffered in DT3.
	 */
	public void receive_D3_1 (double prFreePsychicEnergy);
}
