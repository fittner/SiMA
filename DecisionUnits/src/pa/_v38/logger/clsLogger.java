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
		Logger.getLogger(pa._v38.modules.F46_MemoryTracesForPerception.class).setLevel(Level.OFF);
		Logger.getLogger(pa._v38.modules.F37_PrimalRepressionForPerception.class).setLevel(Level.OFF);
		Logger.getLogger(pa._v38.modules.F35_EmersionOfBlockedContent.class).setLevel(Level.OFF);
		Logger.getLogger(pa._v38.modules.F19_DefenseMechanismsForPerception.class).setLevel(Level.OFF);
		Logger.getLogger(pa._v38.modules.F08_ConversionToSecondaryProcessForDriveWishes.class).setLevel(Level.OFF);
		Logger.getLogger(pa._v38.modules.F21_ConversionToSecondaryProcessForPerception.class).setLevel(Level.OFF);
		Logger.getLogger(pa._v38.modules.F23_ExternalPerception_focused.class).setLevel(Level.OFF);
		Logger.getLogger(pa._v38.modules.F51_RealityCheckWishFulfillment.class).setLevel(Level.OFF);
		Logger.getLogger(pa._v38.modules.F26_DecisionMaking.class).setLevel(Level.DEBUG);
		Logger.getLogger(pa._v38.modules.F52_GenerationOfImaginaryActions.class).setLevel(Level.OFF);
		Logger.getLogger(pa._v38.modules.F29_EvaluationOfImaginaryActions.class).setLevel(Level.OFF);
		Logger.getLogger("pa._v38.decisionpreparation").setLevel(Level.OFF);
		Logger.getLogger("pa._v38.memorymgmt.psychicspreadactivation").setLevel(Level.OFF);
		Logger.getLogger("pa._v38.memorymgmt").setLevel(Level.OFF);
	}
	
}
