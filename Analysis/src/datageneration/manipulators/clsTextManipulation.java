package datageneration.manipulators;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import logger.clsLogger;

import org.slf4j.Logger;

import datageneration.clsManipulation;
import utils.clsGetARSPath;

public class clsTextManipulation extends clsManipulation {
	protected static final Logger log = clsLogger.getLog("analysis.manipulator.manipulation.text");
	protected final String moValueMatcherRegEx = ")=(?<value>.*)";

	public clsTextManipulation(URI oTarget) {
		super(oTarget);
	}

	protected void parseFile(File oFile, String oItemId, String oValue) {
		StringBuffer oStringBuffer = new StringBuffer();
		Pattern oPattern = Pattern.compile("(?<name>" + oItemId + moValueMatcherRegEx);
		try {
			Matcher oResult = oPattern.matcher(new String(Files.readAllBytes(oFile.toPath())));
			if(oResult.find()) {
				oResult.appendReplacement(oStringBuffer, oResult.group("name") + "=" + oValue);
				if(oResult.find()) {
					throw new RuntimeException("Item " + oItemId + " not unique in file " + oFile);
				}
				oResult.appendTail(oStringBuffer);
			} else {
				throw new RuntimeException("Item " + oItemId + " not found in file " + oFile);
			}
			Files.write(oFile.toPath(), oStringBuffer.toString().getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		log.info("Performing value manipulation for target " + getTarget() + " with value " + getValue());
		
		File oTargetFile = openFile();
		
		parseFile(oTargetFile, getItemId(getTarget()), getValue()); 
	}
}
