/**
 * cls_GenericDynamicTimeChartInspector.java: DecisionUnitMasonInspectors - inspectors.mind.pa._v38.autocreated
 * 
 * @author deutsch
 * 23.04.2011, 13:02:24
 */
package mind.autocreated;

import inspector.interfaces.itfInspectorGenericDynamicTimeChart;
import inspector.interfaces.itfInterfaceTimeChartHistory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYDataItem;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 23.04.2011, 13:02:24
 * 
 */
public class cls_GenericDynamicTimeChartInspector extends cls_GenericTimeChartInspector {

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 23.04.2011, 13:02:32
	 */
	private static final long serialVersionUID = -7046239914602916696L;

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * 23.04.2011, 13:02:27
	 *
	 * @param poObject
	 */
	public cls_GenericDynamicTimeChartInspector(itfInspectorGenericDynamicTimeChart poObject) {
		super(poObject);
		((itfInspectorGenericDynamicTimeChart)moTimeingContainer).chartColumnsUpdated();
	}

	public cls_GenericDynamicTimeChartInspector(itfInspectorGenericDynamicTimeChart poObject, int pnHistoryLength, int pnWidth, int pnHeight) {
		super(poObject, pnHistoryLength, pnWidth, pnHeight);
		((itfInspectorGenericDynamicTimeChart)moTimeingContainer).chartColumnsUpdated();
	}	

	@Override
	protected void customizePlot(XYPlot plot) {
		// TODO (Heinrich Kemmler) - Auto-generated method stub
		XYSplineRenderer renderer = new XYSplineRenderer();
		renderer.setBaseItemLabelsVisible(false);
		plot.setRenderer(renderer);
		
		super.customizePlot(plot);
			

	}

	@Override
    protected XYSeriesCollection createDataset() {
    	XYSeriesCollection poDataset = super.createDataset();
		return poDataset;
    }    
    
    private HashMap<String, HashMap<Long, Double>> backupHistory() {
    	HashMap<String, HashMap<Long, Double>> oBackup = new HashMap<String, HashMap<Long, Double>>();

    	for (int i=0; i<moValueHistory.size(); i++) {
    		XYSeries oSeries = moValueHistory.get(i);
   		String oCaption = (String) oSeries.getKey();
    		HashMap<Long, Double> oValues = new HashMap<Long, Double>();
    		
    		oBackup.put(oCaption, oValues);
    		
    		@SuppressWarnings("unchecked")
			List<XYDataItem> oItems = oSeries.getItems();
    		for (int j=0; j<oItems.size();j++) {
    			XYDataItem oItem = oItems.get(j);
    			long timestamp = oItem.getX().longValue();
    			double rValue = oItem.getYValue();
    			    			
    			oValues.put(timestamp, rValue);
    		}
    	}
    	
    	return oBackup;
    }
    
    private void refillHistory(HashMap<String, HashMap<Long, Double>> poBackup, ArrayList<String> poCaptions) {
    	long maxTimestamp = -1;
    	long minTimestamp = Integer.MAX_VALUE;
    	ArrayList<Integer> oNewRows = new ArrayList<Integer>();
    	int i = 0;
    	
    	for (int pos=0; pos<poCaptions.size(); pos++) {
    		String oCaption = poCaptions.get(pos);
    		XYSeries oSeries = moValueHistory.get(pos);
  
    		try {
    			HashMap<Long, Double> oValues = poBackup.get(oCaption);	
    			ArrayList<Long> oKeys = new ArrayList<Long>(oValues.keySet());
    			
    			for (int j=0; j<oKeys.size(); j++) {
    				Long key = oKeys.get(j);
    				Double value = oValues.get(key); 

    				oSeries.add(key, value);
    				if (key < minTimestamp) {
    					minTimestamp = key;
    				}
    				if (key > maxTimestamp) {
    					maxTimestamp = key;
    				}
    			}
    		} catch (java.lang.Exception e)  {
    			oNewRows.add(i);
    		}
    		
    		i++;
    	}
    	
    	for (int pos=0; pos<oNewRows.size(); pos++) {
    		Integer oKey = oNewRows.get(pos);
    		XYSeries oSeries = moValueHistory.get(oKey);
    		for (long t = minTimestamp; t<=maxTimestamp; t++) {
    			oSeries.add(t, 0);
    		}
    	}
    }
    
    private void reCreateChart() {
    	if (moTimeingContainer instanceof itfInterfaceTimeChartHistory) {
    		removeAll();
	    	createPanel();
	    	//fetchDataFromHistory();
    	} else {
	    	HashMap<String, HashMap<Long, Double>> oBackup = backupHistory();
	    	removeAll();
	    	createPanel();
	    	refillHistory(oBackup, moTimeingContainer.getTimeChartCaptions());
	    	//FIXME (DEUTSCH): after recreation of chart panel, the displayed inspector is grey. A back-and-forth switch between tabs is necessary to enforce display. a function call like repaint() at this position should be able to solve this problem.
    	}
    }
    
    @Override
	protected void updateDataset() {
    	if (((itfInspectorGenericDynamicTimeChart)moTimeingContainer).chartColumnsChanged()) {
    		reCreateChart();
    		((itfInspectorGenericDynamicTimeChart)moTimeingContainer).chartColumnsUpdated();
    	}
    	super.updateDataset();
		updateLimitLines();
    }	
}
