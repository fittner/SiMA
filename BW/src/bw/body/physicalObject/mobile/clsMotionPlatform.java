/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.physicalObject.mobile;

import bw.physicalObject.itMotion;
import sim.physics2D.util.Angle;

/**
 * TODO (langr) - insert description 
 * 
 * @author langr
 * 
 */
public class clsMotionPlatform implements itMotion{

	public ARSsim.robot2D.clsRobot moRobotBase = null;
	
	public clsMotionPlatform(ARSsim.robot2D.clsRobot poRobotBase)
	{
		moRobotBase = poRobotBase;
		
		//moRobotBase.setVelocity(vel);
		moRobotBase.setCoefficientOfFriction(.2);
		moRobotBase.setCoefficientOfStaticFriction(0);
		moRobotBase.setCoefficientOfRestitution(1);
	}
	
    public void faceTowards(Angle relativeAngle)
    {
    	moRobotBase.faceTowards(relativeAngle.add( moRobotBase.getOrientation() ) );
    }
	
    public void moveForward(double speed)
	 {
		 moRobotBase.moveForward(speed);
	 }
	 
    public void stop()
	 {
		 moRobotBase.stop();
	 }
	 
    public void backup()
	 {
		moRobotBase.backup(); 
	 }
}
