/**
 * itfAPCultivatable.java: BW - bw.body.io.actuators.actionProxies
 * 
 * @author Benny Doenz
 * 28.08.2009, 14:57:01
 */
package bw.body.io.actuators.actionProxies;

/**
 * Proxy-Interface for action Cultivate
 * Method cultivate: Perform the action 
 * 
 * @author Benny Doenz
 * 20.06.2009, 15:38:56
 * 
 */
public interface itfAPCultivatable {

	/*
	 * 	 Inform the entity that the action has been performed
	 */
	void cultivate(double prAmount);
		
}