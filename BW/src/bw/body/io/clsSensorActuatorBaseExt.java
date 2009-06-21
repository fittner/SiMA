/**
 * @author zeilinger
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io;

/**
 * TODO (zeilinger) - insert description 
 * 
 * @author zeilinger
 * Changes:
 *   BD (21.6.) Moved moBaseIO and registerEnergyconsumption from clsSensorActuatorBase to here
 * 
 */
public abstract class clsSensorActuatorBaseExt extends clsSensorActuatorBase{

	private clsBaseIO moBaseIO;
	
	public clsSensorActuatorBaseExt(clsBaseIO poBaseIO) {
		super();
		moBaseIO=poBaseIO;
	}

	protected void registerEnergyConsumption(float prValue) {
		moBaseIO.registerEnergyConsumption(getUniqueId(), prValue);
	}
	protected void registerEnergyConsumptionOnce(float prValue) {
		moBaseIO.registerEnergyConsumptionOnce(getUniqueId(), prValue);
	}

	/**
	 * needed for access from actuator-classes to the PhysicalObject2D via the clsEntity
	 * 
	 * @author langr
	 * 25.02.2009, 17:45:01
	 * 
	 * @return the moBaseIO
	 */
	public clsBaseIO getBaseIO() {
		return moBaseIO;
	}
}
