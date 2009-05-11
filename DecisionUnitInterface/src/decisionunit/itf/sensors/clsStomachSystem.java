/**
 * 
 */
package decisionunit.itf.sensors;

/**
 * @author langr
 * 
 * Holds the information about the actual energy level within the stomach
 *
 */
public class clsStomachSystem extends clsSensorHomeostasis {

	private float mrEnergy;
	
	/**
	 * 
	 */
	public clsStomachSystem() {
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
