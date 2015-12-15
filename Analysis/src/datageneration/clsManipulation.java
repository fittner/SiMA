package datageneration;

import java.net.URI;

import logger.clsLogger;

import org.slf4j.Logger;

import datageneration.manipulators.clsFramesManipulation;

public abstract class clsManipulation {
	protected static final Logger log = clsLogger.getLog("analysis.manipulator.manipulation");
	private URI moTarget = null;
	private String moValue = null;
	
	public clsManipulation(URI oTarget) {
		setTarget(oTarget);
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
