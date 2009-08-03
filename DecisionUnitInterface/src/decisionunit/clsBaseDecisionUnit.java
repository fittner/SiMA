package decisionunit;

import decisionunit.itf.actions.itfActionProcessor;
import decisionunit.itf.sensors.clsSensorData;

public abstract class clsBaseDecisionUnit {
	private clsSensorData moSensorData;

	public void update(clsSensorData poSensorData) {
		setSensorData(poSensorData);		
	}
	
	public abstract void process(itfActionProcessor poActionProcessor);

	/**
	 * @param moSensorData the moSensorData to set
	 */
	protected void setSensorData(clsSensorData moSensorData) {
		this.moSensorData = moSensorData;
	}

	/**
	 * @return the moSensorData
	 */
	public clsSensorData getSensorData() {
		return moSensorData;
	}
}
