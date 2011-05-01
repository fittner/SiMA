/**
 * clsDataLogger.java: DecisionUnits - pa._v30.datalogger
 * 
 * @author deutsch
 * 23.04.2011, 14:52:17
 */
package pa._v30.logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import pa._v30.interfaces.itfInspectorGenericActivityTimeChart;
import pa._v30.interfaces.itfInspectorGenericDynamicTimeChart;
import pa._v30.interfaces.itfInspectorGenericTimeChart;
import pa._v30.modules.clsModuleBase;
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
	public static final long maxentries = 0; // all history sizes of moDataStorage entries drop old entries if their history exceeds this value. if maxentries=0, size of history is set to infinity.
	public static final String csvseperator = ";";
	public static final String newline = System.getProperty("line.separator");
	
	private long first;
	private long last;
	
	private String moLogFilename;
    private boolean writeToFile = true;
    
    private boolean columnsWritten;

    private int uid;
    
	public clsDataLogger(HashMap<Integer, clsModuleBase> poModules, int uid) {
		moLogFilename = clsGetARSPath.getLogFilename("data_"+uid);
		this.uid = uid;
		
		moDataStorage = new ArrayList<clsDLEntry_Abstract>();
		
		for (clsModuleBase oMod:poModules.values()) {
			if (oMod instanceof itfInspectorGenericActivityTimeChart) {
				moDataStorage.add(new clsDLEntry_ActivityTimeChart( oMod ));
			} else if (oMod instanceof itfInspectorGenericDynamicTimeChart) {
				moDataStorage.add(new clsDLEntry_DynamicTimeChart( oMod ));
			} else if (oMod instanceof itfInspectorGenericTimeChart) { //important: itfInspectorGenericTimeChart has to AFTER itfInspectorGenericDynamicTimeChart 
				moDataStorage.add(new clsDLEntry_TimeChart( oMod ));
			}
		}
		
		columnsWritten = false;

	}
	
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
	
	public String toCSV() {
		String o = "";
		o = getColumnsCSV();
		for (long i=first; i<=last; i++) {
			o += getValuesCSV(i);
		}
		
		return o;
	}
	
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
	
	private void writeDescription() {
		String text = getDescription();
		String filename = clsGetARSPath.getLogFilename("data_"+uid+"_desc");
		
		writeLineToFile(text, filename);
	}
	
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
	
	private void removeFile(String poFilename) {
		File oF = new File(poFilename);
		oF.delete();
	}
	
	private void rewriteLogFile() {
		removeFile(moLogFilename);
		writeDescription();
		writeLineToFile(getColumnsCSV(), moLogFilename);
		
		for (long i=first; i<=last;i++) {
			String oL = getValuesCSV(i);
			writeLineToFile(oL, moLogFilename);
		}
	}
	
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
