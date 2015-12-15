package datageneration;

import java.io.File;
import java.net.URI;

import logger.clsLogger;

import org.slf4j.Logger;

import utils.clsGetARSPath;

public class clsTextManipulation extends clsManipulation {
	protected static final Logger log = clsLogger.getLog("analysis.manipulator.manipulation.text");

	public clsTextManipulation(URI oTarget) {
		super(oTarget);
	}

	protected File openFile() {
		String authority = getTarget().getAuthority();
		File newFile = new File(clsGetARSPath.getAnalysisRunConfigPath() + clsGetARSPath.getSeperator() + authority);
		if(!newFile.exists()) {
			throw new IllegalArgumentException("Filename provided in the authority part of the URI has to point to an existing file.\nFile " + newFile.toString() + " does not exist.");
		}
		
		return newFile;
	}
	
	@Override
	public void run() {
		log.info("Performing value manipulation for target " + getTarget() + " with value " + getValue());
		
		File oTargetFile = openFile();
	}
}
