/**
 * clsEventLogger.java: DecisionUnitInterface - statictools
 * 
 * @author deutsch
 * 22.04.2011, 21:32:18
 */
package statictools.eventlogger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import sim.engine.SimState;
import statictools.clsGetARSPath;
import statictools.clsSimState;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 22.04.2011, 21:32:18
 * 
 */
public class clsEventLogger {
    private static clsEventLogger instance = null;
    private SimState sim = null;
    private String moFilename;
    private boolean writeToFile = true;
    private boolean fillArray = true;
    private final int maxArrayLength = 200;
    private ArrayList<Event> moEvents;
    private boolean textdirty = true;
    private String text = "";
    private clsEventLoggerInspector moELI;
    private static final String newline = System.getProperty("line.separator");
        
	private clsEventLogger() {
   		moEvents = new ArrayList<Event>();
    	sim = clsSimState.getSimState();
    }
    
    public static void setELI(clsEventLoggerInspector poELI) {
    	clsEventLogger.getInstance().moELI = poELI;
    }
    
    private static clsEventLogger getInstance() {
       if (instance == null) {
            instance = new clsEventLogger();
       }
       return instance;
    }
    
	public static long getSteps() {
		long result = -1;
		if (clsEventLogger.getInstance().sim != null) {
			result = clsEventLogger.getInstance().sim.schedule.getSteps();
		}
		return result;
	}
	
    public static void add(Event poEvent) {
    	clsEventLogger.getInstance().addEvent(poEvent);
    }
    
    public static ArrayList<Event> getEventList() {
    	return clsEventLogger.getInstance().moEvents;
    }
    
    public static String toText() {
    	return clsEventLogger.getInstance().totext();
    }
    
    public static boolean isTextDirty() {
    	return clsEventLogger.getInstance().textdirty;
    }
    
    private String totext() {
        if (textdirty) {
	    	textdirty = false;
	    	
	    	text = "";
	    	
	    	text += "** Event Log **"+newline;
	    	text += "Last event occured at step: "+getSteps()+". Recorded number of events: "+moEvents.size()+"."+newline+newline;
	    	if (maxArrayLength > 0) {
	    		text += "Displaying last "+maxArrayLength+" events."+newline;
	    	}
	    	
	    	if (writeToFile) {
	    		text += "Writing log to file: "+getFilename()+"."+newline;
	    	}
	    	text += newline;
	    	
	    	text += "Step; Event"+newline;
	    	
	    	for (int i=moEvents.size()-1; i>=0; i--) {
	    		Event oE = moEvents.get(i);
	    		text += oE.toString().replace(";", "; ")+newline;
	    	}
	    	
	    	text += newline;
	    	

        }
    	return text;
    }
    
    private void addEvent(Event poEvent) {
    	textdirty = true;
    	poEvent.step = clsEventLogger.getSteps();
    	
    	if (writeToFile) {writeEntryToFile(poEvent);}    	
    	if (fillArray) {addEntryToArray(poEvent);}
    	
    	enforceMaxSize();
    	
    	if (moELI != null) {
    		moELI.updateInspector();
    	}
    }
    
    private void enforceMaxSize() {
    	if (maxArrayLength > 0) {
    		while (moEvents.size() > maxArrayLength) {
    			moEvents.remove(0);
    		}
    	}
    }

    private void addEntryToArray(Event poEvent) {
    	moEvents.add(poEvent);
    }

	
	private void writeEntryToFile(Event poEvent) {
		String oFilename = getFilename();
	    try{
	   	    // Create file 
	   	    FileWriter fstream = new FileWriter(oFilename,true);
	        BufferedWriter out = new BufferedWriter(fstream);
	        out.write(poEvent.toString()); out.newLine();
	        out.flush();
	   	    //Close the output stream
	   	    out.close();
	     }catch (Exception e){//Catch exception if any
	   	      System.err.println("Error: " + e.getMessage());
	    }  
	}
	
	private String getFilename() {
		if (moFilename==null || moFilename.length()==0) {
			moFilename = clsGetARSPath.getLogFilename("events");
		}
		return moFilename;
	}

}
