/**
 * clsDLEntry_DynamicTimeChart.java: DecisionUnits - pa._v30.datalogger
 * 
 * @author deutsch
 * 23.04.2011, 16:09:48
 */
package pa._v30.datalogger;

import java.util.ArrayList;
import pa._v30.interfaces.itfInspectorGenericDynamicTimeChart;
import pa._v30.modules.clsModuleBase;
import pa._v30.tools.clsPair;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 23.04.2011, 16:09:48
 * 
 */
public class clsDLEntry_DynamicTimeChart extends clsDLEntry_Abstract implements itfInspectorGenericDynamicTimeChart {

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 23.04.2011, 16:12:42
	 *
	 * @param poModule
	 */
	public clsDLEntry_DynamicTimeChart(clsModuleBase poModule) {
		super(poModule);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 23.04.2011, 16:12:58
	 * 
	 * @see pa._v30.interfaces.itfInspectorGenericTimeChart#getTimeChartUpperLimit()
	 */
	@Override
	public double getTimeChartUpperLimit() {
		return ((itfInspectorGenericDynamicTimeChart)moModule).getTimeChartUpperLimit();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 23.04.2011, 16:12:58
	 * 
	 * @see pa._v30.interfaces.itfInspectorGenericTimeChart#getTimeChartLowerLimit()
	 */
	@Override
	public double getTimeChartLowerLimit() {
		return ((itfInspectorGenericDynamicTimeChart)moModule).getTimeChartLowerLimit();
	}

	private void updateHistory() {
		ArrayList<clsPair <Long, ArrayList<Double>> > newValues = new ArrayList<clsPair <Long, ArrayList<Double>> >();
		ArrayList<String> newCaptions = moModule.getTimeChartCaptions();
		
		for (int i=0; i<values.size(); i++) {
			clsPair <Long, ArrayList<Double>> oLine = values.get(i);
			long step = oLine.a;
			ArrayList<Double> oldset = oLine.b;
			ArrayList<Double> newset = new ArrayList<Double>();
			
			int oldPos=0;
			for (int newPos=0;newPos<newCaptions.size();newPos++) {
				double rValue = 0;
				
				String newCap = newCaptions.get(newPos);
				
				try {
					String oldCap = captions.get(oldPos);
					if (oldCap.equals(newCap)) {
						rValue = oldset.get(oldPos);
						oldPos++;
					}
				} catch (java.lang.Exception e) {
					// do nothing
				}
				
				newset.add(rValue);
			}
			
			newValues.add( new clsPair<Long, ArrayList<Double>>(step, newset) );
		}


		values = newValues;
		updateCaptions();
	}
	
	@Override
	protected void put(long step) {
		if (captionsChanged()) {
			updateHistory();
		}
		super.put(step);
	}	
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 23.04.2011, 16:12:58
	 * 
	 * @see pa._v30.interfaces.itfInspectorGenericDynamicTimeChart#chartRowsChanged()
	 */
	@Override
	public boolean chartColumnsChanged() {
		boolean mnCC = ((itfInspectorGenericDynamicTimeChart)moModule).chartColumnsChanged();
		
		if (mnCC && captionsChanged()) {
			updateHistory();
		}
		
		return mnCC;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 23.04.2011, 19:42:33
	 * 
	 * @see pa._v30.interfaces.itfInspectorGenericDynamicTimeChart#chartRowsUpdated()
	 */
	@Override
	public void chartColumnsUpdated() {
		((itfInspectorGenericDynamicTimeChart)moModule).chartColumnsUpdated();
	}

}
