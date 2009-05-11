/**
 * 
 */
package decisionunit.itf.sensors;

/**
 * @author langr
 *
 */
public class clsHealthSystem extends clsSensorHomeostasis {

	private float mrHealthValue;
	private float mrRecoveryRate;
	private float mrLowerBound;
	private float mrUpperBound;
	
	/**
	 * 
	 */
	public clsHealthSystem() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param mrHealthValue the mrHealthValue to set
	 */
	public void setHealthValue(float mrHealthValue) {
		this.mrHealthValue = mrHealthValue;
	}

	/**
	 * @return the mrHealthValue
	 */
	public float getHealthValue() {
		return mrHealthValue;
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
