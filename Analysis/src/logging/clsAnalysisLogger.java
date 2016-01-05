package logging;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import logger.clsLogger;

import org.slf4j.Logger;

import interfaces.itfAnalysisLogger;

public class clsAnalysisLogger implements itfAnalysisLogger {
	protected static final Logger log = clsLogger.getLog("analysis.logger");
	private final String moRegExString = "\\$\\((?<name>.*?)\\)";
	private Pattern moPattern = null;
	private String moFormat = "";
	private Matcher moMatcher = null;
	private File moFile = null;

	public clsAnalysisLogger() {
		moPattern = Pattern.compile(moRegExString);
	}

	protected void writeHeader() throws IOException {
		StringBuffer oStringBuffer = new StringBuffer();
		String match = "";
		
		if(moMatcher != null && moFile != null) {
			log.info("Writing header");
			while (moMatcher.find()) {
				log.debug("Match found");
				match = moMatcher.group("name");
				log.debug("Matching group " + moMatcher.group());
				log.info("Writing column " + match);
				moMatcher.appendReplacement(oStringBuffer, match);
				log.debug("Replacement successfully appended");
			}	
			log.debug("No more matches");
			moMatcher.reset(moFormat);
			oStringBuffer.append("\n");
			log.debug("Newline appended to entry");
			try {
				log.info("Writing new header:\n" + oStringBuffer);
				if(moFile.exists()) {
					moFile.delete();
				}
				Files.write(moFile.toPath(), oStringBuffer.toString().getBytes(), StandardOpenOption.CREATE_NEW);
				log.debug("Header entry written successfully");
			} catch (IOException e) {
				log.error("Could not write header " + oStringBuffer + " to file " + moFile);
				throw e;
			}
		}
	}
	
	@Override
	public void setFormat(String oFormat) {
		moFormat = notNull(oFormat, "Format string provided to analysis logger must not be null");
		moMatcher = moPattern.matcher(moFormat);
	}

	@Override
	public void setTarget(URI oTarget) throws IOException {
		if(moMatcher == null) {
			throw new RuntimeException("Set file format before setting the target");
		}
		moFile = new File(oTarget);
		writeHeader();
	}
	
	@Override
	public void write(Map<String, String> oValues) throws IOException {
		StringBuffer oStringBuffer = new StringBuffer();
		String match = "";
		String value = "";
		
		log.debug("Values map: " + oValues);
		log.debug("Stringbuffer initialized");
		while (moMatcher.find()) {
			log.debug("Match found");
			match = moMatcher.group("name");
			log.debug("Matching group " + moMatcher.group());
			value = oValues.get(match);
			if(value != null) {
				log.info("Setting value of " + match + " to " + value);
				moMatcher.appendReplacement(oStringBuffer, value);
				log.debug("Replacement successfully appended");
			} else {
				throw new RuntimeException("No value provided for " + match + " in clsAnalysisLogger");
			}
		}
		log.debug("No more matches");
		moMatcher.reset(moFormat);
		oStringBuffer.append("\n");
		log.debug("Newline appended to entry");
		try {
			log.info("Writing new log entry:\n" + oStringBuffer);
			Files.write(moFile.toPath(), oStringBuffer.toString().getBytes(), StandardOpenOption.APPEND);
			log.debug("Log entry written successfully");
		} catch (IOException e) {
			log.error("Could not write values " + oStringBuffer + " to file " + moFile);
			throw e;
		}
	}
	
	protected <T> T notNull(T value, String message) {
		if(value == null) {
			throw new IllegalArgumentException(message);
		}
		
		return value;
	}
}
