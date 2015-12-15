package datageneration;

import logger.clsLogger;

import org.slf4j.Logger;

import interfaces.itfAnalysisLogger;
import interfaces.itfDataManipulation;
import interfaces.itfLogDataTransfer;

public class clsExperimenter implements itfLogDataTransfer {
	protected static final Logger log = clsLogger.getLog("analysis.experimenter");
	private itfDataManipulation moManipulator = null;
	private itfAnalysisLogger moLogger = null;
	
	public clsExperimenter(itfDataManipulation oManipulator, itfAnalysisLogger oLogger) {
		setManipulator(oManipulator);
		setLogger(oLogger);
	}
	
	public void run() {
		log.info("Preparing to run simulation experiment");
	}

	/**
	 * @return the moManipulator
	 */
	public itfDataManipulation getManipulator() {
		return moManipulator;
	}

	protected <T> T notNull(T value, String message) {
		if(value == null) {
			throw new IllegalArgumentException(message);
		}
		
		return value;
	}
	
	/**
	 * @param moManipulator the moManipulator to set
	 */
	public void setManipulator(itfDataManipulation moManipulator) {
		this.moManipulator = notNull(moManipulator, "itfDataManipulation implementation provided to clsExperimenter must not be null");
	}

	/**
	 * @return the moLogger
	 */
	public itfAnalysisLogger getLogger() {
		return moLogger;
	}

	/**
	 * @param moLogger the moLogger to set
	 */
	public void setLogger(itfAnalysisLogger moLogger) {
		this.moLogger = notNull(moLogger, "itfAnalysisLogger implementation provided to clsExperimenter must not be null");
	}
}
