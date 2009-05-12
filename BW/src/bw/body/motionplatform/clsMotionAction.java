/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.motionplatform;

import sim.physics2D.util.Angle;
import enums.eActionCommandMotion;
import enums.eActionCommandType;

/**
 * message class to send a motion action from the brains thinking-cycle
 * to the body's execution-cycle and therefore (in case of physical 
 * changes) further to the clsMobileObject2D and the physics...
 * 
 * @author langr
 * 
 */
public class clsMotionAction extends clsBrainAction{

	public eActionCommandMotion meMotionType;
	public double mrSpeed = 4; //e.g. normal speed is 4.0
	public Angle moRelativeRotation;
	
	/**
	 * @param meType
	 * @param meMotionType
	 */
	public clsMotionAction(eActionCommandType meType,
			eActionCommandMotion meMotionType) {
		super(meType);
		this.meMotionType = meMotionType;
	}

	// region: getter/setter
	
	/**
	 * @return the meMotionType
	 */
	public eActionCommandMotion getMotionType() {
		return meMotionType;
	}

	/**
	 * @param meMotionType the meMotionType to set
	 */
	public void setMotionType(eActionCommandMotion meMotionType) {
		this.meMotionType = meMotionType;
	}

	/**
	 * @return the mnSpeed
	 */
	public double getSpeed() {
		return mrSpeed;
	}

	/**
	 * @param mnSpeed the mnSpeed to set
	 */
	public void setSpeed(double prSpeed) {
		this.mrSpeed = prSpeed;
	}

	/**
	 * @return the moRelativeRotation
	 */
	public Angle getRelativeRotation() {
		return moRelativeRotation;
	}

	/**
	 * @param moRelativeRotation the moRelativeRotation to set
	 */
	public void setRelativeRotation(Angle moRelativeRotation) {
		this.moRelativeRotation = moRelativeRotation;
	}
	
	/**
	 * @param meType
	 * @param meMotionType
	 * @param mnSpeed
	 * @param moDirection
	 */
	public clsMotionAction(eActionCommandType peType,
			eActionCommandMotion peMotionType, double prSpeed, Angle poRelativeRotation) {
		super(peType);
		this.meMotionType = peMotionType;
		this.mrSpeed = prSpeed;
		this.moRelativeRotation = poRelativeRotation;
	}
	
	/**
	 * helper function to create a motion action
	 *
	 * @author langr
	 * 18.03.2009, 11:13:49
	 *
	 * @param motionCommand
	 * @return
	 */
	public static clsMotionAction creatAction(eActionCommandMotion motionCommand)
	{
		return new clsMotionAction(eActionCommandType.MOTION, motionCommand);
	}
	
}
