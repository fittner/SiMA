/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.motionplatform;

import sim.physics2D.util.Angle;
import bw.utils.enums.eActionCommandMotion;
import bw.utils.enums.eActionCommandType;

/**
 * TODO (langr) - insert description 
 * 
 * @author langr
 * 
 */
public class clsMotionAction extends clsBrainAction{

	public eActionCommandMotion meMotionType;
	public double mnSpeed; //e.g. normal speed is 4.0
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
		return mnSpeed;
	}

	/**
	 * @param mnSpeed the mnSpeed to set
	 */
	public void setSpeed(int mnSpeed) {
		this.mnSpeed = mnSpeed;
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
			eActionCommandMotion peMotionType, int pnSpeed, Angle poRelativeRotation) {
		super(peType);
		this.meMotionType = peMotionType;
		this.mnSpeed = pnSpeed;
		this.moRelativeRotation = poRelativeRotation;
	}
	
}
