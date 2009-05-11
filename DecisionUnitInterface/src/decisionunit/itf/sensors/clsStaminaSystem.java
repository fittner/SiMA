/**
 * 
 */
package decisionunit.itf.sensors;

/**
 * @author langr
 *
 */
public class clsStaminaSystem extends clsSensorHomeostasis {

	private float mrStaminaValue;
	private float mrRecoveryRate;
	private float mrLowerBound;
	private float mrUpperBound;
	
	/**
	 * 
	 */
	public clsStaminaSystem() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param mrStaminaValue the mrStaminaValue to set
	 */
	public void setStaminaValue(float mrStaminaValue) {
		this.mrStaminaValue = mrStaminaValue;
	}

	/**
	 * @return the mrStaminaValue
	 */
	public float getStaminaValue() {
		return mrStaminaValue;
	}

	/**
	 * @param mrRecoveryRate the mrRecoveryRate to set
	 */
	public void setRecoveryRate(float mrRecoveryRate) {
		this.mrRecoveryRate = mrRecoveryRate;
	}

	/**
	 * @return the mrRecoveryRate
	 */
	public float getRecoveryRate() {
		return mrRecoveryRate;
	}

	/**
	 * @param mrLowerBound the mrLowerBound to set
	 */
	public void setLowerBound(float mrLowerBound) {
		this.mrLowerBound = mrLowerBound;
	}

	/**
	 * @return the mrLowerBound
	 */
	public float getLowerBound() {
		return mrLowerBound;
	}

	/**
	 * @param mrUpperBound the mrUpperBound to set
	 */
	public void setUpperBound(float mrUpperBound) {
		this.mrUpperBound = mrUpperBound;
	}

	/**
	 * @return the mrUpperBound
	 */
	public float getUpperBound() {
		return mrUpperBound;
	}
}
