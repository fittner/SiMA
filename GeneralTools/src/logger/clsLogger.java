/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logger;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.clsGetARSPath;

/**
 *
 * @author gunther
 */
public class clsLogger {
	private static boolean ansiSet = false;
    
    private clsLogger() {
    	
    }
    
    private static void setSystemPropertyForANSI() {
        //Set this property, in order to activate JAnsi correctly
        System.setProperty("jansi.passthrough", "true");
    }
    
    /**
	 * DOCUMENT (muchitsch) - insert description
	 *
	 * @since 03.09.2012 15:05:46
	 *
	 */
	private static String getCurrentUser() {
		
		String userName="";
		
		try{
			userName = System.getProperty("user.name");
		}
		catch(Exception e){
			System.out.printf(e.toString());						
		}
		
		return userName;
		

		
	}
    
    public static Logger getLog(String loggerName) {
        if (ansiSet==false) {
            setSystemPropertyForANSI();
            PropertyConfigurator.configure(clsGetARSPath.getArsPath() + clsGetARSPath.getSeperator() + "Simulation" + clsGetARSPath.getSeperator() + "log4j.properties");
            ansiSet=true;
        }
        
        return LoggerFactory.getLogger(loggerName);
        
        //return mlog; //LoggerFactory.getLogger(loggerName);
        
//        if (log==null) {
//            setSystemPropertyForANSI();
//            log = LoggerFactory.getLogger("Profileclient");
//        }
//        
//        return mlog;
    }
    
//    public static Logger getLogJRPC() {
//        if (mlogJRPC==null) {
//            setSystemPropertyForANSI();
//            mlogJRPC = LoggerFactory.getLogger("JRPC");
//        }
//        
//        return mlogJRPC;
//    }
    

    
    
    
    
    
    /**
	 * 
	 */
	//public static final org.apache.log4j.Logger jlog = org.apache.log4j.Logger.getRootLogger();
	
	
//	public static void initLogger(Level poLevel) {
//		// --- Set logger properties --- //
//		clsLogger.jlog.removeAllAppenders();
//		clsLogger.jlog.setLevel(poLevel);
//		//Layout layout = new PatternLayout("%p [%t] %c (%F:%L) - %m%n");
//		Layout layout = new PatternLayout("%t: (%F:%L) - %m%n");
//		clsLogger.jlog.addAppender(new ConsoleAppender(layout, ConsoleAppender.SYSTEM_OUT));
//		
//		//moCodeletLogger.setLevel(Level.TRACE);
//		specifyLoggers();
//	}
	
//	private static void specifyLoggers() {
//		//Set the levels of specific loggers
//		Logger.getLogger(pa._v38.modules.F46_MemoryTracesForPerception.class).setLevel(Level.OFF);
//		Logger.getLogger(pa._v38.modules.F37_PrimalRepressionForPerception.class).setLevel(Level.OFF);
//		Logger.getLogger(pa._v38.modules.F35_EmersionOfBlockedContent.class).setLevel(Level.OFF);
//		Logger.getLogger(pa._v38.modules.F19_DefenseMechanismsForPerception.class).setLevel(Level.OFF);
//		Logger.getLogger(pa._v38.modules.F08_ConversionToSecondaryProcessForDriveWishes.class).setLevel(Level.OFF);
//		Logger.getLogger(pa._v38.modules.F21_ConversionToSecondaryProcessForPerception.class).setLevel(Level.OFF);
//		Logger.getLogger(pa._v38.modules.F23_ExternalPerception_focused.class).setLevel(Level.OFF);
//		Logger.getLogger(pa._v38.modules.F51_RealityCheckWishFulfillment.class).setLevel(Level.OFF);
//		Logger.getLogger(pa._v38.modules.F26_DecisionMaking.class).setLevel(Level.INFO);
//		Logger.getLogger(pa._v38.modules.F52_GenerationOfImaginaryActions.class).setLevel(Level.INFO);
//		Logger.getLogger(pa._v38.modules.F29_EvaluationOfImaginaryActions.class).setLevel(Level.OFF);
//		Logger.getLogger(pa._v38.modules.F14_ExternalPerception.class).setLevel(Level.OFF);
//		
//		//package loggers
//		Logger.getLogger("pa._v38.decisionpreparation").setLevel(Level.OFF);
//		Logger.getLogger("pa._v38.tools.clsGoalTools").setLevel(Level.OFF);
//		Logger.getLogger("pa._v38.memorymgmt.psychicspreadactivation").setLevel(Level.OFF);
//		Logger.getLogger("pa._v38.memorymgmt").setLevel(Level.OFF);
//		
//		//customized loggers
//		Logger.getLogger("act_handling").setLevel(Level.TRACE);
//	}
	
}
