/**
 * clsDataLogger.java: DecisionUnits - pa._v38.datalogger
 * 
 * @author deutsch
 * 23.04.2011, 14:52:17
 */
package pa._v38.logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import pa._v38.interfaces.itfInspectorGenericActivityTimeChart;
import pa._v38.interfaces.itfInspectorGenericDynamicTimeChart;
import pa._v38.interfaces.itfInspectorGenericTimeChart;
import pa._v38.modules.clsModuleBase;
import statictools.clsGetARSPath;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 23.04.2011, 14:52:17
 * 
 */
public class clsDataLogger {
	public ArrayList<clsDLEntry_Abstract> moDataStorage;
	
	/** Drops old entries if the size of moDataStorage is above this value. If maxentries=0, size of history is set to infinity. The current value is set to 0.; @since 20.07.2011 16:18:04 */
	public static long maxentries = 0;
	/** Seperator for the CSV values. Default is ";".; @since 20.07.2011 16:41:48 */	
	protected static final String csvseperator = ";";
	/** Cache that stores the content of System.getProperty("line.separator").; @since 20.07.2011 16:18:38 */	
	protected static final String newline = System.getProperty("line.separator");
	
	/** Stores the first timestamp. The action logger can be started whenever appropriate.; @since 20.07.2011 16:19:31 */
	private long first;
	/** Stores the highest provided timestamp.; @since 20.07.2011 16:20:09 */
	private long last;
	
	/** The filename for the file to which the log is written regularly.; @since 20.07.2011 16:20:44 */
	private String moLogFilename;
    /** Turns writing the action log to a file on and off. Default value is true.; @since 20.07.2011 16:21:07 */
    private boolean writeToFile = true;
    
    /** Flag that stores if the columns have already been rewritten after the columns of the provided data have changed.; @since 20.07.2011 16:44:25 */
    private boolean columnsWritten;

    /** Unique identifier provided by the decision unit.; @since 20.07.2011 16:45:09 */
    private int uid;
    
	/**
	 * DOCUMENT (deutsch) - insert description 
	 *
	 * @since 20.07.2011 16:48:08
	 *
	 * @param poModules
	 * @param uid
	 */
	public clsDataLogger(HashMap<Integer, clsModuleBase> poModules, int uid) {
		moLogFilename = clsGetARSPath.getLogFilename("data_"+uid);
		this.uid = uid;
		
		moDataStorage = new ArrayList<clsDLEntry_Abstract>();
		
		for (clsModuleBase oMod:poModules.values()) {
			if (oMod instanceof itfInspectorGenericActivityTimeChart) {
				moDataStorage.add(new clsDLEntry_ActivityTimeChart( oMod ));
			} else if (oMod instanceof itfInspectorGenericDynamicTimeChart) {
				moDataStorage.add(new clsDLEntry_DynamicTimeChart( oMod ));
			} else if (oMod instanceof itfInspectorGenericTimeChart) { //important: itfInspectorGenericTimeChart has to be AFTER itfInspectorGenericDynamicTimeChart 
				moDataStorage.add(new clsDLEntry_TimeChart( oMod ));
			}
		}
		
		columnsWritten = false;

	}
	
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @since 20.07.2011 16:48:06
	 *
	 * @param name
	 * @return
	 */
	public clsDLEntry_Abstract getDL(String name) {
		clsDLEntry_Abstract oResult = null;
		
		for (clsDLEntry_Abstract oDL:moDataStorage) {
			if (oDL.getName().equals(name)) {
				oResult = oDL;
				break;
			}
		}
		
		return oResult;
	}
	
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @since 20.07.2011 16:48:03
	 *
	 */
	public void step() {
		long min = Integer.MAX_VALUE;
		long max = -1;
		boolean columnsDirty = false;
		
		for (clsDLEntry_Abstract oDLE:moDataStorage) {
			oDLE.step();
			
			if (oDLE.getCaptionsUpdated()) {
				columnsDirty = true;
				oDLE.resetCaptionsUpdated();
			}
			
			if (oDLE.first < min) {
				min = oDLE.first;
			}
			if (oDLE.last > max) {
				max = oDLE.last;
			}			
		}
		
		first = min;
		last = max;
		
		if (writeToFile) {
			if (columnsDirty) {
				rewriteLogFile();
			} else {
				if (!columnsWritten) {
					columnsWritten = true;
					writeLineToFile(getColumnsCSV(), moLogFilename);
					writeDescription();
				}			
				writeLineToFile(getValuesCSV(last), moLogFilename);
			}
		}
	}
	
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @since 20.07.2011 16:48:01
	 *
	 * @return
	 */
	public String toCSV() {
		String o = "";
		o = getColumnsCSV();
		for (long i=first; i<=last; i++) {
			o += getValuesCSV(i);
		}
		
		return o;
	}
	
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @since 20.07.2011 16:47:59
	 *
	 * @return
	 */
	public String getColumnsCSV() {
		String o = "#Step"+csvseperator;
		
		for (int i=0; i<moDataStorage.size(); i++) {
			clsDLEntry_Abstract oDS = moDataStorage.get(i);		
			o += oDS.columnsToCSV()+csvseperator;
		}
		o = o.substring(0, o.length() - clsDataLogger.csvseperator.length());
		o += newline;	
		
		return o;
	}
	
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @since 20.07.2011 16:47:57
	 *
	 * @param step
	 * @return
	 */
	public String getValuesCSV(long step) {
		String o = step+csvseperator;
		
		for (int i=0; i<moDataStorage.size(); i++) {
			clsDLEntry_Abstract oDS = moDataStorage.get(i);		
			o += oDS.valuesToCSV(step) + csvseperator;
		}
		o = o.substring(0, o.length() - clsDataLogger.csvseperator.length());
		o += newline;
		
		return o;
	}
	
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @since 20.07.2011 16:47:54
	 *
	 * @return
	 */
	@Deprecated
	public String toHTML() {
		String html = "<html><head></head><body>";
		
		html += "<h1>Data Logger - History</h1>";
		
		html += "<table><tr>";
		String[] temp = getColumnsCSV().split(csvseperator);
		for (String t:temp) {
			html += "<th>"+t+"</th>";
		}
		html +="</tr>";
		
		for (long i=first; i<=last; i++) {
			temp = getValuesCSV(i).split(csvseperator);
			html += "<tr>";
			for (String t:temp) {
				html += "<td>"+t+"</td>";
			}
			html += "</tr>";
		}		
		
		html += "</table>";
		
		return html+"</body></html>";
	}
	
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @since 20.07.2011 16:47:52
	 *
	 */
	private void writeDescription() {
		String text = getDescription();
		String filename = clsGetARSPath.getLogFilename("data_"+uid+"_desc");
		
		writeLineToFile(text, filename);
	}
	
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @since 20.07.2011 16:47:50
	 *
	 * @return
	 */
	public String getDescription() {		
		String text = "";
		
		text += "** Data Logger Content Description **"+newline;
		text += "Step = "+last+newline;
		text += newline;
		
		
		for (int i=0; i<moDataStorage.size(); i++) {
			clsDLEntry_Abstract oDL = moDataStorage.get(i);		
			text += " - ["+oDL.getName()+"]: ";
			for (String oS:oDL.getTimeChartCaptions()) {
				text += oS+clsDataLogger.csvseperator;
			}
			text += newline;
		}
		
		return text+newline;
	}
	
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @since 20.07.2011 16:47:48
	 *
	 * @param poFilename
	 */
	private void removeFile(String poFilename) {
		File oF = new File(poFilename);
		oF.delete();
	}
	
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @since 20.07.2011 16:47:46
	 *
	 */
	private void rewriteLogFile() {
		removeFile(moLogFilename);
		writeDescription();
		writeLineToFile(getColumnsCSV(), moLogFilename);
		
		for (long i=first; i<=last;i++) {
			String oL = getValuesCSV(i);
			writeLineToFile(oL, moLogFilename);
		}
	}
	
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @since 20.07.2011 16:47:43
	 *
	 * @param poLine
	 * @param poFilename
	 */
	private void writeLineToFile(String poLine, String poFilename) {
	    try{
	   	    // Create file 
	   	    FileWriter fstream = new FileWriter(poFilename,true);
	        BufferedWriter out = new BufferedWriter(fstream);
	        out.write(poLine);
	        out.flush();
	   	    //Close the output stream
	   	    out.close();
	     }catch (Exception e){//Catch exception if any
	   	      System.err.println("Error: " + e.getMessage());
	    }  
	}	
		
}
