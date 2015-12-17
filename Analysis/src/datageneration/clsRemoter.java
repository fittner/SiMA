package datageneration;

import interfaces.itfRemoteControl;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
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
	
	@Override
	public void runSiMA(File oScenarioFile) {
		log.info("Preparing to run SiMA");
		String[] args = new String[4];
    	
		args[0] = "-config";
		args[1] = oScenarioFile.getName();
		args[2] = "-autostart";
		args[3] = "true";

		clsAnalyzer.getInstance().registerRemote(this);
		
		clsSingletonAnalysisAccessor.setAnalyzer(clsAnalyzer.getInstance());
		
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
		log.info("Simulation run finished - mason stoped - shutting down simulation run");
		shutdownSiMA();
		log.info("Simulation run shut down - returning control to experiment engine");
    }
}
