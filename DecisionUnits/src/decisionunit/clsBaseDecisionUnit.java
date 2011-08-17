/**
 * CHANGELOG
 * 
 * 2011/07/06 TD - added javadoc comments. code sanitation.
 */
package decisionunit;

import pa._v30.logger.clsActionLogger;
import config.clsProperties;
import du.enums.eDecisionType;
import du.itf.itfDecisionUnit;
import du.itf.actions.itfActionProcessor;
import du.itf.sensors.clsSensorData;

/**
 * The base class for all control architectures/decision units to be used with the brain socket an the projekt DecisionUnitInterface.
 * 
 * @author deutsch
 * 06.07.2011, 12:43:22
 * 
 */
public abstract class clsBaseDecisionUnit implements itfDecisionUnit {
	/** stores the incoming sensor data as provided by the brain socket; @since 06.07.2011 12:45:42 */
	private clsSensorData moSensorData;
	/** the action processer of the BW project. stores the selected actions.; @since 06.07.2011 12:46:08 */
	private itfActionProcessor moActionProcessor;
	/** which decision unit type. @see eDecisionType; @since 06.07.2011 12:46:45 */
	protected eDecisionType meDecisionType;
	/** history of selected and executed actions.; @since 06.07.2011 12:47:05 */
	public clsActionLogger moActionLogger;

	/**
	 * DOCUMENT (deutsch) - insert description 
	 *
	 * @since 06.07.2011 12:47:59
	 *
	 * @param poPrefix prefix for all relevant entries in the property object.
	 * @param poProp the property object.
	 * @param uid unique identifier. the body and the brain get the same. eases debugging and logging.
	 */
	public clsBaseDecisionUnit(String poPrefix, clsProperties poProp, int uid) {
		setDecisionUnitType();
		applyProperties(poPrefix, poProp);
		moActionLogger = new clsActionLogger(uid);
	}
	
	/**
	 * Updates the stored sensor data to the incoming values.
	 *
	 * @since 06.07.2011 12:50:12
	 * 
	 * @see du.itf.itfDecisionUnit#update(du.itf.sensors.clsSensorData)
	 */
	@Override
	public void update(clsSensorData poSensorData) {
		setSensorData(poSensorData);		
	}
	
	/**
	 * Processes the incoming data and produces the action commands. Also known as "thinking".
	 * 
	 * @since 06.07.2011 12:55:30
	 * 
	 * @see du.itf.itfDecisionUnit#process()
	 */
	@Override
	public abstract void process();


	/**
	 * Sets the value of the sensor data. 
	 *
	 * @since 06.07.2011 12:59:50
	 *
	 * @param poSensorData
	 */
	protected void setSensorData(clsSensorData poSensorData) {
		this.moSensorData = poSensorData;
	}
	
	/**
	 * Set the reference to the action processes. Designed using the command processor design pattern.
	 *
	 * @since 06.07.2011 13:00:20
	 * 
	 * @see du.itf.itfDecisionUnit#setActionProcessor(du.itf.actions.itfActionProcessor)
	 */
	@Override
	public void setActionProcessor(itfActionProcessor poActionProcessor) {
		this.moActionProcessor = poActionProcessor;
	}
	
	/**
	 * Returns the action processor.
	 *
	 * @since 06.07.2011 13:00:38
	 *
	 * @return
	 */
	public itfActionProcessor getActionProcessor() {
		return moActionProcessor;
	}

	/**
	 * Returns the sensor data.
	 *
	 * @since 06.07.2011 13:00:55
	 *
	 * @return
	 */
	public clsSensorData getSensorData() {
		return moSensorData;
	}
	
	/**
	 * Update the action logger. Add the currently selected actions to the log.
	 *
	 * @since 06.07.2011 13:01:06
	 * 
	 * @see du.itf.itfDecisionUnit#updateActionLogger()
	 */
	@Override
	public void updateActionLogger() {
		moActionLogger.add(moActionProcessor.logText());
	}
	
	/**
	 * Provides the default entries for this class. See config.clsProperties in project DecisionUnitInterface.
	 *
	 * @since 06.07.2011 13:02:15
	 *
	 * @param poPrefix
	 * @return
	 */
	public static clsProperties getDefaultProperties(String poPrefix) {
//		String pre = clsProperties.addDot(poPrefix);

		clsProperties oProp = new clsProperties();
		
		
		return oProp;
	}	

	/**
	 * Applies the provided properties and creates all instances of various member variables.
	 *
	 * @since 06.07.2011 13:01:45
	 *
	 * @param poPrefix
	 * @param poProp
	 */
	private void applyProperties(String poPrefix, clsProperties poProp) {
//		String pre = clsProperties.addDot(poPrefix);

	}
	
	/**
	 * Returns the type of the decision unit. 
	 * 
	 * @since 06.07.2011 12:56:32
	 * 
	 * @see du.itf.itfDecisionUnit#getDecisionUnitType()
	 */
	@Override
	public eDecisionType getDecisionUnitType() {
		return meDecisionType;
	}
	
	/**
	 * Set the type of the decision unit. To be called by the siblings of this class in their constructor.
	 *
	 * @since 06.07.2011 12:56:43
	 *
	 */
	protected abstract void setDecisionUnitType();
}
