/**
 * itfInspectorTimeChartBase.java: DecisionUnits - pa._v38.interfaces
 * 
 * @author deutsch
 * 23.04.2011, 11:13:54
 */
package pa._v38.interfaces;

import java.util.ArrayList;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 23.04.2011, 11:13:54
 * 
 */
public abstract interface itfInspectorTimeChartBase {
	public String getTimeChartAxis();
	public String getTimeChartTitle();	
	public ArrayList<Double> getTimeChartData();
	public ArrayList<String> getTimeChartCaptions();
}
