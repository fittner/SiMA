/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package decisionunit.itf.actions;

import bfg.tools.shapes.clsAngle;
import decisionunit.itf.actions.eActionCommandMotion;


/**
 * message class to send a motion action from the brains thinking-cycle
 * to the body's execution-cycle and therefore (in case of physical 
 * changes) further to the clsMobileObject2D and the physics...
 * 
 * @author langr
 * 
 */
public class clsMotionAction extends clsActionCommands{

	public eActionCommandMotion meMotionType;
	public double mnSpeed = 4; //e.g. normal speed is 4.0
	public clsAngle moRelativeRotation;
	
	/**
	 * @param meType
	 * @param meMotionType
	 */
	public clsMotionAction(eActionCommandMotion meMotionType) {
		super(eActionCommandType.MOTION);
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
		return mnSpeed;
	}

	/**
	 * @param mnSpeed the mnSpeed to set
	 */
	public void setSpeed(double mnSpeed) {
		this.mnSpeed = mnSpeed;
	}

	/**
	 * @return the moRelativeRotation
	 */
	public clsAngle getRelativeRotation() {
		return moRelativeRotation;
	}

	/**
	 * @param moRelativeRotation the moRelativeRotation to set
	 */
	public void setRelativeRotation(clsAngle moRelativeRotation) {
		this.moRelativeRotation = moRelativeRotation;
	}
	
	/**
	 * @param meType
	 * @param meMotionType
	 * @param mnSpeed
	 * @param moDirection
	 */
	public clsMotionAction(eActionCommandType peType,
			eActionCommandMotion peMotionType, int pnSpeed, clsAngle poRelativeRotation) {
		super(peType);
		this.meMotionType = peMotionType;
		this.mnSpeed = pnSpeed;
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
		return new clsMotionAction(motionCommand);
	}
	
}
