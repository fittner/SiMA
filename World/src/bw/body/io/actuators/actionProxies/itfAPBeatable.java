package bw.body.io.actuators.actionProxies;

/**
 * Proxy-Interface for action beat
 * Method beat: perform aa beat
 * 
 * @author herret
 * 16.07.2013, 15:38:56
 * 
 */
public interface itfAPBeatable {


	/*
	 * Inform the entity it has been beaten
    */
	void beat(double pfForce);

}
