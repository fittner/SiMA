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
	
	public clsSensorActuatorBaseExt() {
		super();
		moBaseIO = null;
	}

	
	/*
	 * If these two methods of an object which does not have body (e.g. Base) are called, moBaseIO is null and therefore an exception is thrown
	 * 
	 * @author horvath
	 * 
	 */
	protected void registerEnergyConsumption(double prValue) {
		if(!moBaseIO.equals(null)){
			moBaseIO.registerEnergyConsumption(getUniqueId(), prValue);
		}else{
			throw new NullPointerException();			
		}
	}
	protected void registerEnergyConsumptionOnce(double prValue) {
		if(!moBaseIO.equals(null)){
			moBaseIO.registerEnergyConsumptionOnce(getUniqueId(), prValue);
		}else{
			throw new NullPointerException();			
		}
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
