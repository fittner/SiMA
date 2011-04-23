/**
 * clsDataLoggerEntry.java: DecisionUnits - pa._v30.datalogger
 * 
 * @author deutsch
 * 23.04.2011, 15:02:01
 */
package pa._v30.datalogger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
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
	
	protected TreeMap<Long, ArrayList<Double>> values;
	protected ArrayList<String> captions;
	public long first = -1;
	public long last = -1;
	private String name = "";
	
	public clsDLEntry_Abstract(clsModuleBase poModule) {
		moModule = (itfInspectorTimeChartBase)poModule;
		values = new TreeMap<Long, ArrayList<Double>>();
		captions = new ArrayList<String>();
		
		name = moModule.getClass().getSimpleName();
		if (name.startsWith("mo")) {
			name = name.substring(2);
		}
		
		captions = null;
	}

	protected void updateCaptions() {
		if (captions == null) {
			captions = new ArrayList<String>();
		}
		
		captions.clear();
		for (Iterator<String> it = moModule.getTimeChartCaptions().iterator(); it.hasNext();) {
			String oS = it.next();
			captions.add(oS);
		}
	}
	
	protected boolean captionsChanged() {
		boolean changed = false;
		
		if (captions == null) {
			return false;
		}
		
		if (captions.size() != moModule.getTimeChartCaptions().size()) {
			changed = true;
		} else {
			for (int i=0; i<captions.size(); i++) {
				if (!captions.get(i).equals(moModule.getTimeChartCaptions().get(i))) {
					changed = true;
					break;
				}
			}
		}
		
		return changed;
	}
	
	public String getName() {
		return name;
	}
	
	public void step() {
		long act = clsSimState.getSteps();
		if (first==-1) {first = act;}
		last = act;
		put(act);
		enforceMaxSize();
	}
	
	protected void put(long step) {
		values.put(step, moModule.getTimeChartData());
	}
	
	@SuppressWarnings("unused") // if clsDataLogger.maxentries is set to 0 a warning occurs ...
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
		if (captions == null) {
			updateCaptions();
		}
		return captions;
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
		
		for (Iterator<String> it = moModule.getTimeChartCaptions().iterator(); it.hasNext();) {
			String oC = it.next();		
			o += oC+clsDataLogger.csvseperator;
		}
		
		o = o.substring(0, o.length() - clsDataLogger.csvseperator.length());
		
		return o;
	}
	
	public String valuesToCSV(long step) {
		String o = "";
		
		try {
			ArrayList<Double> v = values.get(step);
			for (Iterator<Double> it =v.iterator();it.hasNext();) {
				Double r = it.next();
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

	public String toCSV() {
		String csv = "";
		
		csv += columnsToCSV() + clsDataLogger.newline;
		
		for (long i=first; i<=last; i++) {
			csv += valuesToCSV(i) + clsDataLogger.newline;
		}
		
		return csv;
	}
	
	public String toHTML_CSV() {
		String html = "";
		
		html += "<html><head></head><body>";
		
		html += "<h1>"+name+" - History</h1>";
		
		html += "<pre>";
		html += "Step"+clsDataLogger.csvseperator;
		for (Iterator<String> it = getTimeChartCaptions().iterator(); it.hasNext();) {
			String oCaption = it.next();
			html += oCaption+clsDataLogger.csvseperator;
		}
		html = html.substring(0, html.length() - clsDataLogger.csvseperator.length());
		html += clsDataLogger.newline;
		
		try {
			for (Iterator< Map.Entry<Long, ArrayList<Double>> > it = values.entrySet().iterator(); it.hasNext();) {
				Map.Entry<Long, ArrayList<Double>> oEntry = it.next();
				html += oEntry.getKey()+clsDataLogger.csvseperator;			
				for (Double rValue:oEntry.getValue()) {
					html += rValue+clsDataLogger.csvseperator;
				}
				html = html.substring(0, html.length() - clsDataLogger.csvseperator.length());
				html += clsDataLogger.newline;
			}
		} catch (java.util.ConcurrentModificationException e) {
			//FIXME (Deutsch): very bad! caused by line Map.Entry<Long, ArrayList<Double>> oEntry = it.next();
			System.out.println("clsDLEntry_Abstract.toHTML_CSV: "+e);
		}
	
		html += "</pre>";
		
		
		html += "</body></html>";
		
		return html;		
	}
	
	public String toHTML_TABLE() {
		String html = "";
		
//		html += "<html><head></head><body>";
		
//		html += "<h1>"+name+" - History</h1>";
		
		html += "<table>";
		html += "<tr><th>Step</th>";
		for (Iterator<String> it = getTimeChartCaptions().iterator(); it.hasNext();) {
			String oCaption = it.next();		
			html += "<th>"+oCaption+"</th>";
		}
		html += "</tr>";
		
		for (Iterator< Map.Entry<Long, ArrayList<Double>> > it = values.entrySet().iterator(); it.hasNext();) {
			try {
				Map.Entry<Long, ArrayList<Double>> oEntry = it.next();		
	
				html += "<tr><td>"+oEntry.getKey()+"</td>";
				for (Iterator<Double> it2=oEntry.getValue().iterator();it2.hasNext();){
					Double rValue = it2.next();
					html += "<td>"+rValue+"</td>";
				}
				html += "</tr>";
			} catch (java.util.ConcurrentModificationException e) {
				System.out.println("clsDLEntry_Abstract.toHTML_TABLE: "+e);
				break;
			}
		}
		
		html += "</table>";
		
		
//		html += "</body></html>";
		
		return html;
	}
}
