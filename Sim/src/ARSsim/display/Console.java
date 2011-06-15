/**
 * 2011/06/15 TD - added javadoc comments and sanitized class.
 * 2011/06/14: CM+TD - added "pressPause()" to constructor. now the simulation loads everything upon start and is in paused mode afterwards if the corresponding booelan param of the constructor is set to true.
 */
package ARSsim.display;

import sim.display.GUIState;

/**
 * Wrapper for the MASON class sim.display.Console. It differs by providing a new constructor that starts the simulation in paused mode.
 *
 * @author langr
 */
public class Console extends sim.display.Console {
	private static final long serialVersionUID = 7217845510506158250L;

	/**
	 * Constructor that start the simulation in paused mode. Depends on the param pnAutoPause.
	 * 
	 * @author deutsch
	 * 15.06.2011, 15:46:49
	 *
	 * @param simulation GUIState 
	 * @param pnAutoPause Boolean value to determine if the simulation should be autostarted in paused mode.
	 */
	public Console(GUIState simulation, boolean pnAutoPause) {
		super(simulation);
		if (pnAutoPause) {
			this.pressPause(); // TD+CM: autostart as paused
		}
	}
}
