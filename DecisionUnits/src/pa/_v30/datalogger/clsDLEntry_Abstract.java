/**
 * clsDataLoggerEntry.java: DecisionUnits - pa._v30.datalogger
 * 
 * @author deutsch
 * 23.04.2011, 15:02:01
 */
package pa._v30.datalogger;

import java.util.ArrayList;
import java.util.TreeMap;
import pa._v30.interfaces.itfInspectorTimeChartBase;
import pa._v30.interfaces.itfInterfaceTimeChartHistory;
import pa._v30.modules.clsModuleBase;
import statictools.clsSimState;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 23.04.2011, 15:02:01
 * 
 */
public abstract class clsDLEntry_Abstract implements itfInspectorTimeChartBase, itfInterfaceTimeChartHistory {
	protected itfInspectorTimeChartBase moModule;
	
	private TreeMap<Long, ArrayList<Double>> values;
	public long first = -1;
	public long last = -1;
	private boolean mnColumnsChanged = false;
	
	public clsDLEntry_Abstract(clsModuleBase poModule) {
		moModule = (itfInspectorTimeChartBase)poModule;
		values = new TreeMap<Long, ArrayList<Double>>();
	}

	public void step() {
		long act = clsSimState.getSteps();
		if (first==-1) {first = act;}
		last = act;
		values.put(act, moModule.getTimeChartData());
		enforceMaxSize();
	}
	
	private void enforceMaxSize() {
		if (clsDataLogger.maxentries > 0) {
			while (values.size() > clsDataLogger.maxentries) {
				Long oKey = values.firstKey();
				values.remove(oKey);
			}
			first = values.firstKey();
			last = values.lastKey();
		}
	}
	
	@Override
	public String getTimeChartAxis() {
		return moModule.getTimeChartAxis();
	}

	@Override
	public String getTimeChartTitle() {
		return moModule.getTimeChartTitle();
	}

	@Override
	public ArrayList<String> getTimeChartCaptions() {
		return moModule.getTimeChartCaptions();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 23.04.2011, 15:33:27
	 * 
	 * @see pa._v30.interfaces.itfInspectorTimeChartBase#getTimeChartData()
	 */
	@Override
	public ArrayList<Double> getTimeChartData() {
		return values.lastEntry().getValue();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 23.04.2011, 15:35:53
	 * 
	 * @see pa._v30.interfaces.itfInterfaceTimeChartHistory#getTimeChartHistory()
	 */
	@Override
	public TreeMap<Long, ArrayList<Double>> getTimeChartHistory() {
		return values;
	}
	
	public String columnsToCSV() {
		String o = "";
		
		for (String oC:moModule.getTimeChartCaptions()) {
			o += oC+clsDataLogger.csvseperator;
		}
		
		o = o.substring(0, o.length() - clsDataLogger.csvseperator.length());
		
		return o;
	}
	
	public String valuesToCSV(long step) {
		String o = "";
		
		try {
			ArrayList<Double> v = values.get(step);
			for (Double r:v) {
				o += r+clsDataLogger.csvseperator;
			}
		} catch (java.lang.Exception e) {
			for (int i=0;i<moModule.getTimeChartCaptions().size(); i++) {
				o += clsDataLogger.csvseperator;
			}
		}
		
		o = o.substring(0, o.length() - clsDataLogger.csvseperator.length());
		
		return o;
	}	
	
	public boolean columnsChanged() {
		return mnColumnsChanged;
	}
}
