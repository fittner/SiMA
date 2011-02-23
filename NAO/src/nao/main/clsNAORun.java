package nao.main;

import nao.body.clsNAOBody;
import nao.body.io.actuators.clsActionProcessor;
import statictools.clsGetARSPath;
import config.clsBWProperties;
import du.itf.itfDecisionUnit;

public class clsNAORun implements Runnable{

	clsNAOBody nao;
	
	 public void run() {
	    	try{
	    		
	    		String oFilename = "";
	    		oFilename = "testsetup.main.properties"; // no parameters given - used default config			
				String oPath = "";
				oPath = clsGetARSPath.getConfigPath();
				String URL = "128.131.80.227";
		    	int port = 9559;
		    	
		    	clsBWProperties oProp = pa.clsPsychoAnalysis.getDefaultProperties("");
		    	//clsBWProperties oProp = clsBWProperties.readProperties(oPath, oFilename);
		    	itfDecisionUnit oDU = new pa.clsPsychoAnalysis("", oProp);
		    	
		    	clsActionProcessor oActionProcessor = new clsActionProcessor();
		    	oDU.setActionProcessor(oActionProcessor);
		    	
				 nao = new clsNAOBody(URL, port);
				
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
				
	    	} catch(Exception e) {
	    		System.out.println(getCustomStackTrace(e));
	    	      //System.out.println("Error : " + e + " " +  e.getStackTrace());
	    	      System.exit(0);	    	      
	    	}
	    	finally{
	    		
	    		try {
	    			
					nao.close();
					
				} catch (Exception e) {
					System.out.println(getCustomStackTrace(e));
				}
	    	}
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
