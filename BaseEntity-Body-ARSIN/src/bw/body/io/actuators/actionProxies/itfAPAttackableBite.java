/**
 * itfAttackableBite.java: BW - bw.body.io.actuators.actionProxies
 * 
 * @author Benny Doenz
 * 15.09.2009, 08:00:44
 */
package bw.body.io.actuators.actionProxies;

/**
 * Proxy-Interface for action Attack/Bite
 * Method tryBite: will be called before biting 
 * Method bite: perform the bite
 *  
 * @author Benny Doenz
 * 20.06.2009, 15:38:56
 * 
 */
public interface itfAPAttackableBite {

	/*
	 * tries to kill the entity using a given force (default=4)
	 * returns 0 if OK, else damage-value as double
	 */
	double tryBite(double pfForce);
	
	/*
	 * Inform the entity it has been killed 
	 * Will only be executed if tryKill returns 0 as result
	 */
	void bite(double pfForce);

}
