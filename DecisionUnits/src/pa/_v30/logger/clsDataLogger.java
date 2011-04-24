/**
 * clsDataLogger.java: DecisionUnits - pa._v30.datalogger
 * 
 * @author deutsch
 * 23.04.2011, 14:52:17
 */
package pa._v30.logger;

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
	
	public clsDataLogger(HashMap<Integer, clsModuleBase> poModules, String uid) {
		moLogFilename = clsGetARSPath.getLogFilename("data_"+uid);
		
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
		for (clsDLEntry_Abstract oDLE:moDataStorage) {
			oDLE.step();
		}
	}

	private void updateFirstLast() {
		long min = Integer.MAX_VALUE;
		long max = -1;
		
		for (int i=0; i<moDataStorage.size(); i++) {
			clsDLEntry_Abstract oDS = moDataStorage.get(i);
			
			if (oDS.first < min) {
				min = oDS.first;
			}
			if (oDS.last > max) {
				max = oDS.last;
			}
		}
		
		first = min;
		last = max;
	}
	
	public String toCSV() {
		updateFirstLast();
		
		String o = "";
		o = getColumnsCSV();
		for (long i=first; i<=last; i++) {
			o += getValuesCSV(i);
		}
		
		return o;
	}
	
	public String getColumnsCSV() {
		String o = "Step"+csvseperator;
		
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
	
	public String toHTML() {
		updateFirstLast();
		
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
	
	public String getDescription() {
		updateFirstLast();
		
		String html = "<html><head></head><body>";
		
		html += "<h1>Data Logger Content Description</h1>";
		html += "<p>From: "+first+" to: "+last+"</p>";
		
		html += "<ul>";
		
		for (int i=0; i<moDataStorage.size(); i++) {
			clsDLEntry_Abstract oDL = moDataStorage.get(i);		
			html += "<li><b>"+oDL.getName()+":</b> ";
			for (String oS:oDL.getTimeChartCaptions()) {
				html += oS+clsDataLogger.csvseperator;
			}
			html += "</li>";
		}
		html += "</ul>";
		
		return html+"</body></html>";
	}
}
