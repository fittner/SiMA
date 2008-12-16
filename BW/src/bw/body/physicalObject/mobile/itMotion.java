/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.physicalObject.mobile;

import sim.physics2D.util.Angle;

/**
 * Interface to direct the agent: accelerate, decelerate, rotate, jump, stop, ... 
 * 
 * @author langr
 * 
 */
public interface itMotion {

     void faceTowards(Angle angularForce);
	 void moveForward(double speed);
	 void stop();
	 void backup();
}
