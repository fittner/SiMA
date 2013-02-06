/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pa._v38.logger;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/**
 *
 * @author gunther
 */
public class clsLogger {
	/**
	 * 
	 */
	public static final org.apache.log4j.Logger jlog = org.apache.log4j.Logger.getRootLogger();
	
	
	public static void initLogger(Level poLevel) {
		// --- Set logger properties --- //
		clsLogger.jlog.removeAllAppenders();
		clsLogger.jlog.setLevel(poLevel);
		//Layout layout = new PatternLayout("%p [%t] %c (%F:%L) - %m%n");
		Layout layout = new PatternLayout("%t: (%F:%L) - %m%n");
		clsLogger.jlog.addAppender(new ConsoleAppender(layout, ConsoleAppender.SYSTEM_OUT));
		
		//moCodeletLogger.setLevel(Level.TRACE);
		specifyLoggers();
	}
	
	private static void specifyLoggers() {
		//Set the levels of specific loggers
		Logger.getLogger(pa._v38.modules.F46_MemoryTracesForPerception.class).setLevel(Level.TRACE);
	}
	
}
