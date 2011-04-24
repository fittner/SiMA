package nao.main;

import java.io.IOException;

import statictools.clsUniqueIdGenerator;

import nao.body.clsNAOBody;
import nao.body.io.actuators.clsActionProcessor;
import config.clsBWProperties;
import du.itf.itfDecisionUnit;

/**
 * This is the main runtime for the nao execution. as we dont have a simstate that gets executed.. we have out own run()
 * @author muchitsch
 *
 */
public class clsNAORun implements Runnable{

	private String moNAOURL;
	private int moNAOPort;
	clsNAOBody nao;
	
	 public void run() {
//    		String oFilename = oFilename = "testsetup.main.properties"; // no parameters given - used default config			
//			String oPath = oPath = clsGetARSPath.getConfigPath();
	    	
	    	clsBWProperties oProp = pa.clsPsychoAnalysis.getDefaultProperties("");
	    	itfDecisionUnit oDU = new pa.clsPsychoAnalysis("", oProp, clsUniqueIdGenerator.getUniqueId()+"");
	    	
	    	clsActionProcessor oActionProcessor = new clsActionProcessor();
	    	oDU.setActionProcessor(oActionProcessor);
	    	
			try {
				nao = new clsNAOBody(moNAOURL, moNAOPort);
			} catch (IOException e) {
				e.printStackTrace();
	    		System.out.println(getCustomStackTrace(e));
	    		System.exit(0);	
			}
			
			nao.getBrain().setDecisionUnit(oDU);
			int oStep = 0;
			
			while(clsSingletonNAOState.getKeeprunning())
            {
			 	System.out.println("step: " + oStep);
			 	
			 	nao.stepSensing();
			 	nao.stepUpdateInternalState();
			 	nao.stepProcessing();
		 		nao.stepExecution();
			 	
			 	oStep++;
			 	
            }
			System.out.println("...stopping thread");
			
//	    		System.out.println(getCustomStackTrace(e));
//	    		System.exit(0);	    	      
  		
    		try {
    			
				nao.close();
				
			} catch (Exception e) {
				System.out.println(getCustomStackTrace(e));
			}
		}
	 
	 
		public String getMoNAOURL() {
				return moNAOURL;
			}
		public void setMoNAOURL(String moNAOURL) {
			this.moNAOURL = moNAOURL;
		 	}
		public int getMoNAOPort() {
			return moNAOPort;
			}
		public void setMoNAOPort(int moNAOPort) {
			this.moNAOPort = moNAOPort;
			}

		
	    public static String getCustomStackTrace(Throwable aThrowable) {
	        //add the class name and any message passed to constructor
	        final StringBuilder result = new StringBuilder( "ARS-EX: " );
	        result.append(aThrowable.toString());
	        final String NEW_LINE = System.getProperty("line.separator");
	        result.append(NEW_LINE);

	        //add each element of the stack trace
	        for (StackTraceElement element : aThrowable.getStackTrace() ){
	          result.append( element );
	          result.append( NEW_LINE );
	        }
	        return result.toString();
	      }
	    
}
