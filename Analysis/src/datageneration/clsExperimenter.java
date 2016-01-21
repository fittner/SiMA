package datageneration;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
		moLogger.setFormat(
				"$(Agent_0.GOAL0_ID)" +
				", $(Agent_0.GOAL0_IMPORTANCE)" +
				", $(Agent_0.GOAL0_IMPORTANCE_DRIVE)" +
				", $(Agent_0.GOAL0_IMPORTANCE_FEELINGMATCH)" +
				", $(Agent_0.GOAL0_IMPORTANCE_FEELINGEXPECTATION)" +
				", $(Agent_0.GOAL0_IMPORTANCE_EFFORT)" +
				", $(Agent_0.GOAL1_ID)" +
				", $(Agent_0.GOAL1_IMPORTANCE)" +
				", $(Agent_0.GOAL1_IMPORTANCE_DRIVE)" +
				", $(Agent_0.GOAL1_IMPORTANCE_FEELINGMATCH)" +
				", $(Agent_0.GOAL1_IMPORTANCE_FEELINGEXPECTATION)" +
				", $(Agent_0.GOAL1_IMPORTANCE_EFFORT)" +
				", $(Agent_0.GOAL2_ID)" +
				", $(Agent_0.GOAL2_IMPORTANCE)" +
				", $(Agent_0.GOAL2_IMPORTANCE_DRIVE)" +
				", $(Agent_0.GOAL2_IMPORTANCE_FEELINGMATCH)" +
				", $(Agent_0.GOAL2_IMPORTANCE_FEELINGEXPECTATION)" +
				", $(Agent_0.GOAL2_IMPORTANCE_EFFORT)" +
				", $(Agent_0.GOAL3_ID)" +
				", $(Agent_0.GOAL3_IMPORTANCE)" +
				", $(Agent_0.GOAL3_IMPORTANCE_DRIVE)" +
				", $(Agent_0.GOAL3_IMPORTANCE_FEELINGMATCH)" +
				", $(Agent_0.GOAL3_IMPORTANCE_FEELINGEXPECTATION)" +
				", $(Agent_0.GOAL3_IMPORTANCE_EFFORT)" +
				", $(Agent_0.GOAL4_ID)" +
				", $(Agent_0.GOAL4_IMPORTANCE)" +
				", $(Agent_0.GOAL4_IMPORTANCE_DRIVE)" +
				", $(Agent_0.GOAL4_IMPORTANCE_FEELINGMATCH)" +
				", $(Agent_0.GOAL4_IMPORTANCE_FEELINGEXPECTATION)" +
				", $(Agent_0.GOAL4_IMPORTANCE_EFFORT)" +
				", $(Agent_0.GOAL5_ID)" +
				", $(Agent_0.GOAL5_IMPORTANCE)" +
				", $(Agent_0.GOAL5_IMPORTANCE_DRIVE)" +
				", $(Agent_0.GOAL5_IMPORTANCE_FEELINGMATCH)" +
				", $(Agent_0.GOAL5_IMPORTANCE_FEELINGEXPECTATION)" +
				", $(Agent_0.GOAL5_IMPORTANCE_EFFORT)" +
				", $(Agent_0.GOAL6_ID)" +
				", $(Agent_0.GOAL6_IMPORTANCE)" +
				", $(Agent_0.GOAL6_IMPORTANCE_DRIVE)" +
				", $(Agent_0.GOAL6_IMPORTANCE_FEELINGMATCH)" +
				", $(Agent_0.GOAL6_IMPORTANCE_FEELINGEXPECTATION)" +
				", $(Agent_0.GOAL6_IMPORTANCE_EFFORT)" +
				", $(Agent_0.GOAL7_ID)" +
				", $(Agent_0.GOAL7_IMPORTANCE)" +
				", $(Agent_0.GOAL7_IMPORTANCE_DRIVE)" +
				", $(Agent_0.GOAL7_IMPORTANCE_FEELINGMATCH)" +
				", $(Agent_0.GOAL7_IMPORTANCE_FEELINGEXPECTATION)" +
				", $(Agent_0.GOAL7_IMPORTANCE_EFFORT)" +
				", $(Agent_0.GOAL8_ID)" +
				", $(Agent_0.GOAL8_IMPORTANCE)" +
				", $(Agent_0.GOAL8_IMPORTANCE_DRIVE)" +
				", $(Agent_0.GOAL8_IMPORTANCE_FEELINGMATCH)" +
				", $(Agent_0.GOAL8_IMPORTANCE_FEELINGEXPECTATION)" +
				", $(Agent_0.GOAL8_IMPORTANCE_EFFORT)" +
				", $(Agent_0.GOAL9_ID)" +
				", $(Agent_0.GOAL9_IMPORTANCE)" +
				", $(Agent_0.GOAL9_IMPORTANCE_DRIVE)" +
				", $(Agent_0.GOAL9_IMPORTANCE_FEELINGMATCH)" +
				", $(Agent_0.GOAL9_IMPORTANCE_FEELINGEXPECTATION)" +
				", $(Agent_0.GOAL9_IMPORTANCE_EFFORT)" +
				", $(Agent_0.Outcome)");
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

		//this will later reset the config before specific values are manipulated
		try {
			moManipulator.prepareConfig("sim_run");
		} catch (IOException e1) {
			log.error("Exception while preparing config");
			e1.printStackTrace();
		}
		
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss"); //e.g 20140806_160022
		Calendar cal = Calendar.getInstance();
		File oLogFile = new File("sim_run" + dateFormat.format(cal.getTime()) + ".csv");
		try {
			moLogger.setTarget(oLogFile.toURI());
		} catch (IOException e1) {
			log.error("Exception while setting logging target");
			e1.printStackTrace();
		}
		
		//Replace with proper loop condition and variable development
		for(double i = 0; i <= 1.0; i += 0.1) {
			//Example of a single simulation run
			try {
				moManipulator.put("text://personality/EC2_default.properties/F65.PERSONALITY_SPLIT_STOMACH.value", Double.toString(i));
				moManipulator.put("text://personality/EC2_default.properties/F65.PERSONALITY_SPLIT_RECTUM.value", Double.toString(i));
				moManipulator.put("text://personality/EC2_default.properties/F65.PERSONALITY_SPLIT_STAMINA.value", Double.toString(i));
				moManipulator.put("text://personality/EC2_default.properties/F64.PERSONALITY_SPLIT_ORAL.value", Double.toString(i));
				moManipulator.put("text://personality/EC2_default.properties/F64.PERSONALITY_SPLIT_ANAL.value", Double.toString(i));
				moManipulator.put("text://personality/EC2_default.properties/F64.PERSONALITY_SPLIT_PHALLIC.value", Double.toString(i));
				moManipulator.put("text://personality/EC2_default.properties/F64.PERSONALITY_SPLIT_GENITAL.value", Double.toString(i));
				
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
