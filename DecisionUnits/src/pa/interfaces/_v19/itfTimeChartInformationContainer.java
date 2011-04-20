/**
 * itfTimeChartInformationContainer.java: DecisionUnitMasonInspectors - inspectors.mind.pa
 * 
 * @author langr
 * 04.11.2009, 19:09:18
 */
package pa.interfaces._v19;

import java.util.ArrayList;

import pa.tools.clsPair;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 04.11.2009, 19:09:18
 * 
 */
public interface itfTimeChartInformationContainer {
	public ArrayList<clsPair<String, Double>> getTimeChartData();
}
