/**
 * itfAPSleep.java: BW - bw.body.io.actuators.actionProxies
 * 
 * @author Benny D�nz
 * 15.09.2009, 08:23:32
 */
package bw.body.io.actuators.actionProxies;

/**
 * Proxy-Interface for action Sleep. => Internal organs and other body-parts 
 * that must be informed carry this interface and must be registered at the executor
 * Method sleep: inform about the sleep intensity 
 * 
 * @author Benny D�nz
 * 20.06.2009, 15:38:56
 * 
 */
public interface itfAPSleep{

	/*
	 * Inform the entity about the sleep-intensity 
	 */
	void sleep();
		
}