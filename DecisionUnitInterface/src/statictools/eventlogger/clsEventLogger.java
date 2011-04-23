/**
 * clsEventLogger.java: DecisionUnitInterface - statictools
 * 
 * @author deutsch
 * 22.04.2011, 21:32:18
 */
package statictools.eventlogger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
    private boolean htmldirty = true;
    private String html = "";
    private clsEventLoggerInspector moELI;

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
    
    public static String toHtml() {
    	return clsEventLogger.getInstance().tohtml();
    }
    
    public static boolean isHtmlDirty() {
    	return clsEventLogger.getInstance().htmldirty;
    }
    
    private String tohtml() {
        if (htmldirty) {
	    	htmldirty = false;
	    	
	    	html = "<html><head></head><body>";
	    	
	    	html += "<h1>Event Log</h1>";
	    	html += "<p>Last event occured at step: "+getSteps()+". Recorded number of events: "+moEvents.size()+".</p>";
	    	html += "<table><tr><th>Step</th><th>Event</th></tr>";
	    	
	    	long step = Integer.MAX_VALUE;
	    	String temp = "";
	    	for (int i=moEvents.size()-1; i>=0; i--) {
	    		Event oE = moEvents.get(i);
	    		
	    		if (oE.step < step) {
	    			step = oE.step;
	    			if (temp.length()!=0) {
	    				temp = temp.substring(0, temp.length()-"<br/>".length());
	    				temp += "</td></tr>";
	    			}
	    			temp += "<tr><td>"+step+"</td><td>";
	    		}
	    		
	    		temp += oE.toHtml();
	    	}
	    	temp += "</td></tr>";
	    	
	    	html += temp+"</table>";
	    	
	    	if (maxArrayLength > 0) {
	    		html += "<p>Displaying last "+maxArrayLength+" events.</p>";
	    	}
	    	
	    	if (writeToFile) {
	    		html += "<p>Writing log to file: "+getFilename()+".</p>";
	    	}
	    	
	    	html  += "</body></html>";
        }
    	return html;
    }
    
    private void addEvent(Event poEvent) {
    	htmldirty = true;
    	poEvent.step = clsEventLogger.getSteps();
    	
    	if (writeToFile) {writeEntryToFile(poEvent);}    	
    	if (fillArray) {addEntryToArray(poEvent);}
    	
    	enforceMaxSize();
    	
    	moELI.updateInspector();
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
			moFilename = clsGetARSPath.getArsPath()+"/events_"+getDateTime()+".log";
		}
		return moFilename;
	}
	
    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Date date = new Date();
        return dateFormat.format(date);
    }	
}
