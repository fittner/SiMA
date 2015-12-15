import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import logger.clsLogger;
import logging.clsAnalysisLogger;

import org.slf4j.Logger;

import datageneration.clsExperimenter;
import datageneration.clsManipulator;
import datageneration.clsRemoter;

public class clsController extends Thread {
	protected static final Logger log = clsLogger.getLog("analysis.controller");
	private clsExperimenter moExperimentEngine = null;
	private clsManipulator moDataManipulator = null; 
	private clsAnalysisLogger moLogger = null;
	private clsRemoter moRemoteController = null;
	
	public clsController() {
		this(null);
	}
	
	public clsController(String oThreadName) {
		if(oThreadName != null) {
			log.info("Setting thread name to " + oThreadName);
			this.setName(oThreadName);
		}
		log.debug("new controller created");
	}
	
	public void initialize() {
		log.info("initializing controller");
		moDataManipulator = new clsManipulator();
		log.info("Manipulator instance created");
		log.debug(moDataManipulator.toString());
		moLogger = new clsAnalysisLogger();
		log.info("Logger instance created");
		log.debug(moLogger.toString());
		moExperimentEngine = new clsExperimenter(moDataManipulator, moLogger);
		log.info("Experimentation engine instance created");
		log.debug(moExperimentEngine.toString());
		moRemoteController = new clsRemoter();
		log.info("Remote controller instance created");
		log.debug(moRemoteController.toString());
		log.debug("controller initialized");
	}

	public void run() {
		log.info("starting controller execution");
		try {
			moDataManipulator.prepareConfig("sim_run_1");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
//		moExperimentEngine.run();
		
//		try {
//			moDataManipulator.put("text://personality.properties/F26.INITIAL_REQUEST_INTENSITY.value", "0.0");
//			moDataManipulator.put("frames://ADAM.pprj/EMOTION:A10_DIVIDE_CAKE_L01_I07:GUILT#SourceAggr", "1.0");
//		} catch (URISyntaxException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		try {
			moRemoteController.runSiMA(moDataManipulator.getScenarioFile());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void shutdown() {
		log.info("shuting down controller");
	}
	
	public static void main(String[] args) {
		clsController controller = new clsController("SiMA Automated Analysis");
		
		controller.initialize();
		
		controller.start();
		
		try {
			controller.join();
		} catch(InterruptedException e) {
			log.error("Thread " + controller.getName() + " interrupted!");
		}
		
		controller.shutdown();
	}

}
