package decisionunit;

import decisionunit.itf.actions.clsActionCommandContainer;
import decisionunit.itf.sensors.clsSensorData;

public abstract class clsBaseDecisionUnit {
	private clsSensorData moSensorData;

	public void update(clsSensorData poSensorData) {
		setSensorData(poSensorData);		
	}
	
	public abstract clsActionCommandContainer process();

	/**
	 * @param moSensorData the moSensorData to set
	 */
	protected void setSensorData(clsSensorData moSensorData) {
		this.moSensorData = moSensorData;
	}

	/**
	 * @return the moSensorData
	 */
	protected clsSensorData getSensorData() {
		return moSensorData;
	}
}
