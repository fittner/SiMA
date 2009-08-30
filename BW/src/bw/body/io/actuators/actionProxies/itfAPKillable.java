package bw.body.io.actuators.actionProxies;

/**
 * Proxy-Interface for action Kill
 * Method tryKill: will be called before killing 
 * Method kill: perform the kill 
 * Method attack: perform an attack (opponent has no options in this ..
 * 
 * @author Benny Dönz
 * 20.06.2009, 15:38:56
 * 
 */
public interface itfAPKillable {

	/*
	 * tries to kill the entity using a given force (default=4)
	 * returns 0 if OK, else damage-value as double
	 */
	double tryKill(double pfForce);
	
	/*
	 * Inform the entity it has been killed 
	 * Will only be executed if tryKill returns 0 as result
	 */
	void kill(double pfForce);

	/*
	 * Inform the entity it has been attacked 
    */
	void attack(double pfForce);

}
