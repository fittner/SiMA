/**
 * cls_GenericDynamicTimeChartInspector.java: DecisionUnitMasonInspectors - inspectors.mind.pa._v30.autocreated
 * 
 * @author deutsch
 * 23.04.2011, 13:02:24
 */
package inspectors.mind.pa._v30.autocreated;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.data.xy.XYDataItem;

import pa._v30.interfaces.itfInspectorGenericDynamicTimeChart;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 23.04.2011, 13:02:24
 * 
 */
public class cls_GenericDynamicTimeChartInspector extends
		cls_GenericTimeChartInspector {

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
	 * @author deutsch
	 * 23.04.2011, 13:02:27
	 *
	 * @param poObject
	 */
	public cls_GenericDynamicTimeChartInspector(
			itfInspectorGenericDynamicTimeChart poObject) {
		super(poObject);
		// TODO (deutsch) - Auto-generated constructor stub
	}


    @Override
    protected XYSeriesCollection createDataset() {
    	XYSeriesCollection poDataset = super.createDataset();
		
		return poDataset;
    }    
    
    private HashMap<String, HashMap<Long, Double>> backupHistory() {
    	HashMap<String, HashMap<Long, Double>> oBackup = new HashMap<String, HashMap<Long, Double>>();
    	
    	for (XYSeries oSeries:moValueHistory) {
    		String oCaption = (String) oSeries.getKey();
    		HashMap<Long, Double> oValues = new HashMap<Long, Double>();
    		
    		oBackup.put(oCaption, oValues);
    		
    		@SuppressWarnings("unchecked")
			List<XYDataItem> oItems = oSeries.getItems();

    		for (XYDataItem oItem:oItems) {
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
    	
    	for (String oCaption:poCaptions) {
    		XYSeries oSeries = moValueHistory.get(i);
  
    		try {
    			HashMap<Long, Double> oValues = poBackup.get(oCaption);
    			for (Map.Entry<Long, Double> oValue:oValues.entrySet()) {
    				oSeries.add(oValue.getKey(), oValue.getValue());
    				if (oValue.getKey() < minTimestamp) {
    					minTimestamp = oValue.getKey();
    				}
    				if (oValue.getKey() > maxTimestamp) {
    					maxTimestamp = oValue.getKey();
    				}
    			}
    		} catch (java.lang.Exception e)  {
    			oNewRows.add(i);
    		}
    		
    		i++;
    	}
    	
    	for (Integer oKey:oNewRows) {
    		XYSeries oSeries = moValueHistory.get(oKey);
    		for (long t = minTimestamp; t<=maxTimestamp; t++) {
    			oSeries.add(t, 0);
    		}
    	}
    }
    
    private void reCreateChart() {
    	HashMap<String, HashMap<Long, Double>> oBackup = backupHistory();
    	removeAll();
    	createPanel();
    	refillHistory(oBackup, moTimeingContainer.getTimeChartCaptions());
    	//FIXME (DEUTSCH): after recreation of chart panel, the displayed inspector is grey. A back-and-forth switch between tabs is necessary to enforce display. a function call like repaint() at this position should be able to solve this problem. 
    }
    
    @Override
	protected void updateData() {
    	if (((itfInspectorGenericDynamicTimeChart)moTimeingContainer).chartRowsChanged()) {
    		reCreateChart();
    	}
    	super.updateData();
		updateLimitLines();
    }	
}
