/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.physicalObject.mobile;

import java.awt.Color;

import com.sun.xml.internal.ws.message.RelatesToHeader;

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
	
    protected void faceTowards(Angle relativeAngle)
    {
    	moRobotBase.faceTowards(relativeAngle.add( moRobotBase.getOrientation() ) );
    }
	
	 protected void moveForward(double speed)
	 {
		 moRobotBase.moveForward(speed);
	 }
	 
	 protected void stop()
	 {
		 moRobotBase.stop();
	 }
	 
	 protected void backup()
	 {
		moRobotBase.backup(); 
	 }
}
