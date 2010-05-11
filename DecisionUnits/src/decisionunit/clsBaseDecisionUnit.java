package decisionunit;

import config.clsBWProperties;
import du.itf.itfDecisionUnit;
import du.itf.actions.itfActionProcessor;
import du.itf.sensors.clsSensorData;

public abstract class clsBaseDecisionUnit implements itfDecisionUnit {
	private clsSensorData moSensorData;
	private itfActionProcessor moActionProcessor;
	private String moActionProcessorToHTML;

	public clsBaseDecisionUnit(String poPrefix, clsBWProperties poProp) {
		applyProperties(poPrefix, poProp);
	}
	
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
	
	public String getActionProcessorToHTML() {
		return moActionProcessorToHTML;
	}
	
	public void updateActionProcessorToHTML() {
		moActionProcessorToHTML = moActionProcessor.logXML();
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
//		String pre = clsBWProperties.addDot(poPrefix);

		clsBWProperties oProp = new clsBWProperties();
		
		
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
//		String pre = clsBWProperties.addDot(poPrefix);

	}	
}
