package decisionunit;

import decisionunit.itf.actions.itfActionProcessor;
import decisionunit.itf.sensors.clsSensorData;

public abstract class clsBaseDecisionUnit {
	private clsSensorData moSensorData;
	private itfActionProcessor moActionProcessor;

	public void update(clsSensorData poSensorData) {
		setSensorData(poSensorData);		
	}
	
	public abstract void process();

	/**
	 * @param moSensorData the moSensorData to set
	 */
	protected void setSensorData(clsSensorData poSensorData) {
		this.moSensorData = poSensorData;
	}
	
	public void setActionProcessor(itfActionProcessor poActionProcessor) {
		this.moActionProcessor = poActionProcessor;
	}
	
	public itfActionProcessor getActionProcessor() {
		return moActionProcessor;
	}

	/**
	 * @return the moSensorData
	 */
	public clsSensorData getSensorData() {
		return moSensorData;
	}
}
