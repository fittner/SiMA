package datageneration;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import logger.clsLogger;
import logging.clsAnalysisLogger;
import base.clsAnalysisConstants;

import org.slf4j.Logger;

import utils.clsGetARSPath;
import datageneration.manipulators.clsFramesManipulation;
import datageneration.manipulators.clsTextManipulation;

public abstract class clsManipulation {
	protected static final Logger log = clsLogger.getLog("analysis.manipulator.manipulation");
	private URI moTarget = null;
	private String moValue = null;
	
	public clsManipulation(URI oTarget) {
		setTarget(oTarget);
	}
	
	protected String getDirectory(URI oTarget) {
		String oDirectory = "";
		
		switch(oTarget.getAuthority()) {
		case "personality":
			oDirectory = clsAnalysisConstants.config.directories.PERSONALITY;
			log.debug(oTarget + " lookup directoty " + oDirectory + " for personality value " + oTarget);
			break;
		case "body":
			oDirectory = clsAnalysisConstants.config.directories.BODY;
			log.debug(oTarget + " lookup directoty " + oDirectory + " for body value " + oTarget);
			break;
		case "memory":
			oDirectory = clsAnalysisConstants.config.directories.MEMORY;
			log.debug(oTarget + " lookup directoty " + oDirectory + " for memory value " + oTarget);
			break;
		case "scenario":
			oDirectory = clsAnalysisConstants.config.directories.SCENARIO;
			log.debug(oTarget + " lookup directoty " + oDirectory + " for scenario value " + oTarget);
			break;
		default:
			break;
		}
		
		return oDirectory;
	}
	
	protected String getFileName(URI oTarget) {
		String oFullPath = getTarget().getPath();
		
		List<String> oParts = new ArrayList<String>(Arrays.asList(oFullPath.substring(1, oFullPath.length()).split("/")));
		if(oParts.size() > 0) {
			return oParts.get(0);
		} else {
			throw new IllegalArgumentException("Cannot extract filename from URI " + oTarget + ". Path element of URI has not enough parts.");
		}
	}
	
	protected String getItemId(URI oTarget) {
		String oFullPath = getTarget().getPath();
		List<String> oParts = new ArrayList<String>(Arrays.asList(oFullPath.substring(1, oFullPath.length()).split("/")));
		if(oParts.size() > 1) {
			return oFullPath.substring(oParts.get(0).length() + 2, oFullPath.length());
		} else {
			throw new IllegalArgumentException("Cannot extract item id from URI " + oTarget + ". Path element of URI has not enough parts.");
		}
	}
	
	protected File openFile() {
		String oDirectory = getDirectory(getTarget());
		String oFileName = getFileName(getTarget());
		
		File newFile = new File(oDirectory + clsGetARSPath.getSeperator() + oFileName);
		if(!newFile.exists()) {
			throw new IllegalArgumentException("Filename provided in the authority part of the URI has to point to an existing file.\nFile " + newFile.toString() + " does not exist.");
		}
		
		return newFile;
	}
	
	public static clsManipulation create(URI oTarget) {
		clsManipulation oNewManipulation = null;
		
		switch(oTarget.getScheme()) {
		case "text":
			log.info("Creating new text manipulation object for target " + oTarget.toString());
			oNewManipulation = new clsTextManipulation(oTarget);
			break;
		case "frames":
			log.info("Creating new frame manipulation object for target " + oTarget.toString());
			oNewManipulation = new clsFramesManipulation(oTarget);
			break;
		default:
			throw new IllegalArgumentException("Can not manipulate object of scheme " + oTarget.getScheme());
		}
		
		return oNewManipulation;
	}
	
	public abstract void run();

	/**
	 * @return the moTarget
	 */
	public URI getTarget() {
		return moTarget;
	}

	/**
	 * @param moTarget the moTarget to set
	 */
	public void setTarget(URI moTarget) {
		this.moTarget = notNull(moTarget, "Target provided to manipulator instance must not be null");
	}

	/**
	 * @return the moValue
	 */
	public String getValue() {
		return moValue;
	}

	/**
	 * @param moValue the moValue to set
	 */
	public void setValue(String moValue) {
		if(moValue == null) {
			log.warn("Setting value of " + moTarget + " to null");
		}
		this.moValue = moValue;
	}
	
	protected <T> T notNull(T value, String message) {
		if(value == null) {
			throw new IllegalArgumentException(message);
		}
		
		return value;
	}
}
