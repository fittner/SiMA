package datageneration;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import logger.clsLogger;

import org.slf4j.Logger;

import properties.clsProperties;
import sim.SimulatorMain;
import singeltons.clsSingletonMasonGetter;

public class clsRemoter {
	protected static final Logger log = clsLogger.getLog("analysis.remoter");
	
	public void runSiMA(File oScenarioFile) throws IOException {
		log.info("Preparing to run SiMA");
		String[] args = new String[4];
    	
		args[0] = "-config";
		args[1] = oScenarioFile.getName();
		args[2] = "-autostart";
		args[3] = "true";
        	
    	//load the scenario
		SimulatorMain.main(args);
		
		Timer x = new Timer("test",true);
		TimerTask task = new TimerTask(){
			@Override
			public void run() {
				clsSingletonMasonGetter.getConsole().pressPause();
			}
		};
		
		x.schedule(task, 1);
    }
}
