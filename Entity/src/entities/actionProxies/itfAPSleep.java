/**
 * itfAPSleep.java: BW - bw.body.io.actuators.actionProxies
 * 
 * @author Benny Doenz
 * 15.09.2009, 08:23:32
 */
package entities.actionProxies;

/**
 * Proxy-Interface for action Sleep. => Internal organs and other body-parts 
 * that must be informed carry this interface and must be registered at the executor
 * Method sleep: inform about the sleep intensity 
 * 
 * @author Benny Doenz
 * 20.06.2009, 15:38:56
 * 
 */
public interface itfAPSleep{

	/*
	 * Inform the entity about the sleep-intensity 
	 */
	void sleep();
		
}