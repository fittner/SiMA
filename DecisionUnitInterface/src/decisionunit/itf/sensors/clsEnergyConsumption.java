/**
 * 
 */
package decisionunit.itf.sensors;

/**
 * @author langr
 *
 * Actual energy consumption - the sum of all consumers
 *
 */
public class clsEnergyConsumption extends clsSensorHomeostasis {

	private float mrEnergy;
	
	/**
	 * 
	 */
	public clsEnergyConsumption() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param mrEnergy the mrEnergy to set
	 */
	public void setEnergy(float mrEnergy) {
		this.mrEnergy = mrEnergy;
	}

	/**
	 * @return the mrEnergy
	 */
	public float getEnergy() {
		return mrEnergy;
	}

}
