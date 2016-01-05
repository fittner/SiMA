package datageneration;

import interfaces.itfLogDataTransfer;
import interfaces.itfRemoteControl;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import logger.clsLogger;

import org.slf4j.Logger;

import base.tools.clsSingletonAnalysisAccessor;
import properties.clsProperties;
import sim.SimulatorMain;
import sim.clsMain;
import sim.display.Console;
import sim.engine.Schedule;
import singeltons.clsSingletonMasonGetter;
import singeltons.clsSingletonProperties;
import utils.clsGetARSPath;

public class clsRemoter implements itfRemoteControl {
	protected static final Logger log = clsLogger.getLog("analysis.remoter");
	private boolean moStop = false;
	private final Lock moLock = new ReentrantLock();
	private itfLogDataTransfer moLogDataHandler = null;
	
	@Override
	public void stopSiMA() {
		synchronized (moLock) {
			moStop = true;
			moLock.notifyAll();
		}
	}
	
	private class ConsoleCloser extends TimerTask {
		private Console moConsole = null;
		
		public ConsoleCloser(Console poConsole) {
			if(poConsole == null) {
				throw new IllegalArgumentException("Console provided to console closer must not be null");
			}
			moConsole = poConsole;
		}
		
		@Override
		public void run() {
			moConsole.doClose();
		}
	}
	
	protected void shutdownSiMA() {
		Timer x = new Timer("CloseLastWindow",true);
		ConsoleCloser task = new ConsoleCloser(clsSingletonMasonGetter.getConsole());
		
		x.schedule(task, 3000);

		log.debug("Shutdown procedure complete");
	}
	
	public void registerLogDataHandler(itfLogDataTransfer poLogDataHandler) {
		moLogDataHandler = notNull(poLogDataHandler, "itfLogDataTransfer implementation provided to clsRemoter must not be null");
	}
	
	@Override
	public void runSiMA(File oScenarioFile) {
		log.info("Preparing to run SiMA");
		String[] args = new String[4];
    	
		args[0] = "-config";
		args[1] = oScenarioFile.getName();
		args[2] = "-autostart";
		args[3] = "true";

		clsAnalyzer oCurrentAnalyzer = new clsAnalyzer(this);
		
		clsSingletonAnalysisAccessor.setAnalyzer(oCurrentAnalyzer);
		
		//load the scenario
		SimulatorMain.main(args);
		
		synchronized (moLock) {
			try {
				while(true) {
					moLock.wait();
					if(moStop) {
						log.info("Stop signal received and stop condition set - ending simulation");
						clsSingletonMasonGetter.getConsole().pressStop();
						break;
					} else {
						log.warn("SiMA stop signal notified but stop condition is not set - we keep waiting");
					}
				}
			} catch (InterruptedException e) {
				log.warn("Waiting for SiMA stop signal interrupted: " + e);
				e.printStackTrace();
			}
		}
		log.info("Simulation run finished - mason stoped - reading factor and outcome logs");
		for(clsDuAnalyzer oDu : oCurrentAnalyzer.getAllDuAnalyzers()) {
			//check if data is consistent (currently: the factors and actions collections have same size)
			if(oDu.getFactors().size() != oDu.getActions().size()) {
				log.error("Data for simulation run might be inconsistent - the number of stored factors and stored actions differ");
			} else {
				//loop through steps
				for(int i = 0; i < oDu.getFactors().size(); ++i) {
					//handle factors
					Map<String, String> oFactors = oDu.getFactors().get(i);
					Map<String, String> oLogData = new HashMap<>(); 
					
					//log post processing - introducing the agent id into the key strings
					for(String oKey : oFactors.keySet()) {
						oLogData.put(oDu.getId() + "." + oKey, oFactors.get(oKey));
					}
					try {
						//handle actions
						oLogData.put(oDu.getId() + ".Outcome", oDu.getActions().get(i));
						moLogDataHandler.put(oLogData);
					} catch (IOException e) {
						log.error("Could not write data for step " + Integer.toString(i) + "IO Exception:");
						e.printStackTrace();
					}
				}
			}
		}
		shutdownSiMA();
		log.info("Simulation run shut down - returning control to experiment engine");
    }
	
	protected <T> T notNull(T value, String message) {
		if(value == null) {
			throw new IllegalArgumentException(message);
		}
		
		return value;
	}
}
