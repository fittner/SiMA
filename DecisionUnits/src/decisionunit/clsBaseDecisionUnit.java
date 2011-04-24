package decisionunit;

import pa._v30.logger.clsActionLogger;
import config.clsBWProperties;
import du.enums.eDecisionType;
import du.itf.itfDecisionUnit;
import du.itf.actions.itfActionProcessor;
import du.itf.sensors.clsSensorData;

public abstract class clsBaseDecisionUnit implements itfDecisionUnit {
	private clsSensorData moSensorData;
	private itfActionProcessor moActionProcessor;
	protected eDecisionType meDecisionType;
	public clsActionLogger moActionLogger;

	public clsBaseDecisionUnit(String poPrefix, clsBWProperties poProp, String uid) {
		setDecisionUnitType();
		applyProperties(poPrefix, poProp);
		moActionLogger = new clsActionLogger(uid);
	}
	
	@Override
	public void update(clsSensorData poSensorData) {
		setSensorData(poSensorData);		
	}
	
	@Override
	public abstract void process();

	/**
	 * @param moSensorData the moSensorData to set
	 */
	protected void setSensorData(clsSensorData poSensorData) {
		this.moSensorData = poSensorData;
	}
	
	@Override
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
	
	@Override
	public void updateActionLogger() {
		moActionLogger.add(moActionProcessor.logText());
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
//		String pre = clsBWProperties.addDot(poPrefix);

		clsBWProperties oProp = new clsBWProperties();
		
		
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
//		String pre = clsBWProperties.addDot(poPrefix);

	}
	
	@Override
	public eDecisionType getDecisionUnitType() {
		return meDecisionType;
	}
	
	protected abstract void setDecisionUnitType();
}
