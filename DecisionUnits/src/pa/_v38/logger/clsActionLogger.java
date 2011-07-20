/**
 * clsActionLogger.java: DecisionUnits - pa._v38.datalogger
 * 
 * @author deutsch
 * 24.04.2011, 01:18:19
 */
package pa._v38.logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import pa._v38.tools.clsPair;
import pa._v38.tools.clsTripple;
import statictools.clsGetARSPath;
import statictools.clsSimState;

/**
 * Collect and store the provided action commands. The content can be accesses either directly, written to a file, or a structured text output.
 *  
 * 
 * @author deutsch
 * 24.04.2011, 01:18:19
 * 
 */
public class clsActionLogger {
	/** A list containing an entry of type pair with the start time (long) and the action command (string); @since 20.07.2011 16:16:59 */
	public ArrayList<clsPair<Long, String>> actions;
	
	/** Drops old entries if the size of actions is above this value. If maxentries=0, size of history is set to infinity. The current value is set to 0.; @since 20.07.2011 16:18:04 */
	private long maxentries = 0;
	/** Cache that stores the content of System.getProperty("line.separator").; @since 20.07.2011 16:18:38 */
	private static final String newline = System.getProperty("line.separator");
	/** Seperator for the CSV values. Default is ";".; @since 20.07.2011 16:41:48 */
	private static final String csvseperator = ";";
	
	/** Stores the first timestamp. The action logger can be started whenever appropriate.; @since 20.07.2011 16:19:31 */
	private long first;
	/** Stores the highest provided timestamp.; @since 20.07.2011 16:20:09 */
	private long last;
	
	/** The filename for the file to which the log is written regularly.; @since 20.07.2011 16:20:44 */
	private String moLogFilename;
    /** Turns writing the action log to a file on and off. Default value is true.; @since 20.07.2011 16:21:07 */
    private boolean writeToFile = true;
    
	/**
	 * Constructor. Sets moLogFilename to "action_"+uid.
	 *
	 * @since 20.07.2011 16:24:35
	 *
	 * @param uid Unique identifier provided for the decision unit.
	 */
	public clsActionLogger(int uid) {
		moLogFilename = clsGetARSPath.getLogFilename("action_"+uid);
		
		actions = new ArrayList<clsPair<Long,String>>();
		first = Integer.MAX_VALUE;
		last = -1;
	}
	
	/**
	 * Add the provided action command to the log list. The current timestamp of the simulation is retrieved from clsSimState.getSteps().
	 *
	 * @since 20.07.2011 16:25:38
	 *
	 * @param poAction
	 */
	public void add(String poAction) {
		long act = clsSimState.getSteps();
		if (act < first) {first = act;}
		if (act > last) {last = act;}
		clsPair<Long, String> oEntry =new clsPair<Long, String>(act, poAction); 
		actions.add(oEntry);
		
		enforceMaxLength();
		
		if (writeToFile) {
			writeLineToFile(oEntry);
		}
	}
	
	/**
	 * Enforces that the arraylist actions is at maximum maxentries long. If maxentries is set 0, the array can have infinite number of entries.
	 *
	 * @since 20.07.2011 16:29:58
	 *
	 */
	private void enforceMaxLength() {
		if (maxentries > 0 && actions.size() > maxentries) {
			while (actions.size() > maxentries) {
				actions.remove(0);
			}
			first = actions.get(0).a;
		}
	}
	
	/**
	 * Creates an arraylist that compresses the list of tuples to a list of tripples. The list of tupples stored in actions contains the timestamp and the 
	 * action command. The list triples contains the start timestamp, the end timestamp, and the action. Thus, if an action is executed a lot of times 
	 * consecutively, it is replaced by a single entry. 
	 * 
	 * An example: when a new action command is provided in e.g. round 10, a new entry is added to the list. This entry stays unchanged until a different 
	 * action command is provided in e.g. round 15. Now the previous entry is provided with the information that this action command was active from 10 to 14.
	 *
	 * @since 20.07.2011 16:26:53
	 *
	 * @return
	 */
	public ArrayList<clsTripple<Long,Long,String>> compress() {
		ArrayList<clsTripple<Long,Long,String>> oResult = new ArrayList<clsTripple<Long,Long,String>>();
		
		long prev = -1;
		clsTripple<Long, Long, String> oEntry = null;
		
		for (int i=0; i<actions.size(); i++) {
			if (oEntry == null) {
				oEntry = new clsTripple<Long, Long, String>(actions.get(i).a, (long) -1, actions.get(i).b);
			}
			
			if (!oEntry.c.equals(actions.get(i).b)) {
				oEntry.b = prev;
				oResult.add( oEntry );
				oEntry = new clsTripple<Long, Long, String>(actions.get(i).a, (long) -1, actions.get(i).b);
			}
			
			prev = actions.get(i).a;
		}
		
		oEntry.b = prev;
		oResult.add( oEntry );
		
		return oResult;
	}
	
	/**
	 * Write the provided pair (timestamp+action command) to the file defined in moLogFilename. Adds the entry at the end and closes the file.
	 *
	 * @since 20.07.2011 16:35:40
	 *
	 * @param poLine
	 */
	private void writeLineToFile(clsPair<Long, String> poLine) {
	    try{
	   	    // Create file 
	   	    FileWriter fstream = new FileWriter(moLogFilename,true);
	        BufferedWriter out = new BufferedWriter(fstream);
	        out.write(poLine.a+csvseperator+poLine.b); out.newLine();
	        out.flush();
	   	    //Close the output stream
	   	    out.close();
	     }catch (Exception e){//Catch exception if any
	   	      System.err.println("Error: " + e.getMessage());
	    }  
	}	
	
	/**
	 * Uses the result of the method compress to display the past actions in a structured text form.
	 *
	 * @since 20.07.2011 16:36:25
	 *
	 * @return
	 */
	public String toText() {
		String o = "";
		
		o += "** Action Logger ("+first+" to "+last+") **"+newline+newline;
		
		ArrayList<clsTripple<Long,Long,String>> oLines = compress();
		
		for (int i=oLines.size()-1;i>=0;i--) {
			clsTripple<Long,Long,String> oL = oLines.get(i);
			
			o += " - ["+oL.a+":";
			if (i==oLines.size()-1) {
				o+="__";
			} else {
				o+=""+oL.b;
			}
			o += "]: "+oL.c+newline;
		}
		
		return o;
	}
	
	
}
