/**
 * clsDataLoggerEntry.java: DecisionUnits - pa._v38.datalogger
 * 
 * @author deutsch
 * 23.04.2011, 15:02:01
 */
package pa._v38.logger;

import java.util.ArrayList;
import pa._v38.interfaces.itfInspectorTimeChartBase;
import pa._v38.interfaces.itfInterfaceTimeChartHistory;
import pa._v38.modules.clsModuleBase;
import pa._v38.tools.clsPair;
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
	
	protected ArrayList<clsPair <Long, ArrayList<Double>> > values;
	protected ArrayList<String> captions;
	public long first = -1;
	public long last = -1;
	private String name = "";
	private boolean captionsUpdated;
	
	public clsDLEntry_Abstract(clsModuleBase poModule) {
		moModule = (itfInspectorTimeChartBase)poModule;
		values = new ArrayList<clsPair<Long,ArrayList<Double>>>();
		captions = new ArrayList<String>();
		
		name = moModule.getClass().getSimpleName();
		if (name.startsWith("mo")) {
			name = name.substring(2);
		}
		
		captions = null;
		captionsUpdated = false;
	}

	protected void updateCaptions() {
		if (captions == null) {
			captions = new ArrayList<String>();
		}
		
		captions.clear();
		
		for (int i=0; i<moModule.getTimeChartCaptions().size(); i++) {
			String oS = moModule.getTimeChartCaptions().get(i);
			captions.add(oS);
		}
		
		captionsUpdated = true;
	}
	
	public boolean getCaptionsUpdated() {
		return captionsUpdated;
	}
	
	public void resetCaptionsUpdated() {
		captionsUpdated = false;
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
		values.add( new clsPair<Long, ArrayList<Double>>(step, moModule.getTimeChartData()) );
	}
	
	@SuppressWarnings("unused") // if clsDataLogger.maxentries is set to 0 a warning occurs ...
	private void enforceMaxSize() {
		if (clsDataLogger.maxentries > 0) {
			while (values.size() > clsDataLogger.maxentries) {
				values.remove(0);
			}
			first = values.get(0).a;
			last = values.get(values.size()-1).a;
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
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartData()
	 */
	@Override
	public ArrayList<Double> getTimeChartData() {
		return values.get(values.size()-1).b;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 23.04.2011, 15:35:53
	 * 
	 * @see pa._v38.interfaces.itfInterfaceTimeChartHistory#getTimeChartHistory()
	 */
	@Override
	public ArrayList<clsPair <Long, ArrayList<Double>> > getTimeChartHistory() {
		return values;
	}
	
	public String columnsToCSV() {
		String o = "";
		
		for (int i=0; i<moModule.getTimeChartCaptions().size(); i++) {
			String oC = moModule.getTimeChartCaptions().get(i);	
			o += oC+clsDataLogger.csvseperator;
		}
		
		o = o.substring(0, o.length() - clsDataLogger.csvseperator.length());
		
		return o;
	}
	
	public String valuesToCSV(long step) {
		String o = "";
		
		for (int i=0; i<values.size(); i++) {
			clsPair <Long, ArrayList<Double>> oLine = values.get(i);
			if (oLine.a == step) {
				o = valuesToCSV(oLine);
				break;
			}
		}
		
		if (o.length() == 0) {
			for (int i=0; i<captions.size(); i++) {
				o += clsDataLogger.csvseperator;
			}
		}
		
		return o;
	}
	
	private String valuesToCSV(clsPair <Long, ArrayList<Double>> oValues) {
		String o = "";
		
		//o += oValues.a + clsDataLogger.csvseperator;
		
		for (int i=0; i<oValues.b.size(); i++) {
			double r = oValues.b.get(i);
			o += r+clsDataLogger.csvseperator;
		}
		
		o = o.substring(0, o.length() - clsDataLogger.csvseperator.length());
		
		return o;
	}	

	public String toCSV() {
		String csv = "";
		
		csv += "#Step" + clsDataLogger.csvseperator + columnsToCSV() + clsDataLogger.newline;
		
		for (int i=0; i<values.size(); i++) {
			clsPair <Long, ArrayList<Double>> oLine = values.get(i);
			csv += oLine.a + clsDataLogger.csvseperator + valuesToCSV(oLine) + clsDataLogger.newline;
		}
		
		return csv;
	}
	
	@Deprecated
	public String toHTML_TABLE() {
		String html = "";
		
//		html += "<html><head></head><body>";
		
//		html += "<h1>"+name+" - History</h1>";
		
		html += "<table>";
		html += "<tr><th>Step</th>";
		for (int i=0; i<getTimeChartCaptions().size(); i++) {
			String oCaption = getTimeChartCaptions().get(i);
			html += "<th>"+oCaption+"</th>";
		}
		html += "</tr>";
		
		for (int i=0; i<values.size(); i++) {
			clsPair <Long, ArrayList<Double>> oLine = values.get(i);		
			
			html += "<tr><td>"+oLine.a+"</td>";
			for (int j=0; j<oLine.b.size(); j++) {
				double r = oLine.b.get(i);
				html += "<td>"+r+"</td>";
			}
			html += "</tr>";
		}
		
		html += "</table>";
		
		
//		html += "</body></html>";
		
		return html;
	}
}
