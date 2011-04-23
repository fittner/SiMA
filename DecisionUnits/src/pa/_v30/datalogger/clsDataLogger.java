/**
 * clsDataLogger.java: DecisionUnits - pa._v30.datalogger
 * 
 * @author deutsch
 * 23.04.2011, 14:52:17
 */
package pa._v30.datalogger;

import java.util.ArrayList;
import java.util.HashMap;
import pa._v30.interfaces.itfInspectorGenericActivityTimeChart;
import pa._v30.interfaces.itfInspectorGenericDynamicTimeChart;
import pa._v30.interfaces.itfInspectorGenericTimeChart;
import pa._v30.modules.clsModuleBase;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 23.04.2011, 14:52:17
 * 
 */
public class clsDataLogger {
	ArrayList<clsDLEntry_Abstract> moDataStorage;
	public static final long maxentries = 0; // all history sizes of moDataStorage entries drop old entries if their history exceeds this value. if maxentries=0, size of history is set to infinity.
	public static final String csvseperator = ";";
	public static final String newline = System.getProperty("line.separator");
	
	public clsDataLogger(HashMap<Integer, clsModuleBase> poModules) {
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
	
	public void step() {
		for (clsDLEntry_Abstract oDLE:moDataStorage) {
			oDLE.step();
		}
	}

	public String toCSV() {
		String o = "";
		long min = Integer.MAX_VALUE;
		long max = -1;
		
		for (clsDLEntry_Abstract oDS:moDataStorage) {
			if (oDS.first < min) {
				min = oDS.first;
			}
			if (oDS.last > max) {
				max = oDS.last;
			}
		}

		o = getColumnsCSV();
		
		for (long i=min; i<=max; i++) {
			o += getValuesCSV(i);
		}
		
		return o;
	}
	
	public String getColumnsCSV() {
		String o = "Step"+csvseperator;
		
		for (clsDLEntry_Abstract oDS:moDataStorage) {
			o += oDS.columnsToCSV()+csvseperator;
		}
		o = o.substring(0, o.length() - clsDataLogger.csvseperator.length());
		o += newline;	
		
		return o;
	}
	
	public String getValuesCSV(long step) {
		String o = "";
		
		for (clsDLEntry_Abstract oDS:moDataStorage) {
			o += oDS.valuesToCSV(step) + csvseperator;
		}
		o = o.substring(0, o.length() - clsDataLogger.csvseperator.length());
		o += newline;
		
		return o;
	}
}
