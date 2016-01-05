package datageneration;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import logger.clsLogger;

import org.slf4j.Logger;

import interfaces.itfAnalysisLogger;
import interfaces.itfDataManipulation;
import interfaces.itfFileManipulation;
import interfaces.itfLogDataTransfer;
import interfaces.itfRemoteControl;

public class clsExperimenter implements itfLogDataTransfer {
	protected static final Logger log = clsLogger.getLog("analysis.experimenter");
	private itfDataManipulation moManipulator = null;
	private itfAnalysisLogger moLogger = null;
	private itfRemoteControl moRemote = null;
	private itfFileManipulation moFileReader = null;
	private Map<String, String> moSimLog = new HashMap<>();
	
	public clsExperimenter(itfDataManipulation poManipulator, itfAnalysisLogger poLogger, itfRemoteControl poRemote, itfFileManipulation poFileReader) {
		setManipulator(poManipulator);
		setLogger(poLogger);
		setRemote(poRemote);
		
		//initialize logger
		moLogger.setFormat("$(Agent_0.GuiltValue), $(Agent_0.Outcome)");
		setFileManipulation(poFileReader);
	}

	protected void writeLogs() throws IOException {
		//write initial values
		
		//write sim factors and outcomes
		
	}
	
	public void run() {
		String fileName = "";
		int nSimRunCounter = 0;
		log.info("Preparing to run simulation experiments");

		//Replace with proper loop condition and variable development
		for(double i = 0; i <= 1.0; i += 0.1) {
			//Example of a single simulation run
			try {
				//this will later reset the config before specific values are manipulated
				moManipulator.prepareConfig("sim_run_" + Integer.toString(nSimRunCounter));
				
				File oLogFile = new File("sim_run_" + Integer.toString(nSimRunCounter) + ".csv");
				moLogger.setTarget(oLogFile.toURI());

				moManipulator.put("text://personality/analysis_personality_adam.properties/F26.INITIAL_REQUEST_INTENSITY.value", Double.toString(i));
				
				moRemote.runSiMA(moManipulator.getScenarioFile());
				
				writeLogs();
				
				nSimRunCounter++;
			} catch (IOException e) {
				log.error("Could not prepare config for simulation run: " + e);
			} catch (URISyntaxException e) {
				log.error("Problem when trying to manipulate value in simulation run sim_run_" + Double.toString(i) + ":\n" + e);
				e.printStackTrace();
			}
		}
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

	/**
	 * @return the moRemote
	 */
	public itfRemoteControl getRemote() {
		return moRemote;
	}

	/**
	 * @param moRemote the moRemote to set
	 */
	public void setRemote(itfRemoteControl moRemote) {
		this.moRemote = notNull(moRemote, "itfRemoteControl implementation provided to clsExperimenter must not be null");
	}
	
	
	private void setFileManipulation(itfFileManipulation moFileReader) {
		//this.moFileReader = notNull(moFileReader, "itfFileManipulation implementation provided to clsExperimenter must not be null");
	}
	
	public itfFileManipulation getFileReader() {
		return moFileReader;
	}



	@Override
	public void put(Map<String, String> poStepLogEntries) throws IOException {
		moLogger.write(poStepLogEntries);
	}
}
