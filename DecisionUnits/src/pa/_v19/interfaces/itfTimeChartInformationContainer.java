/**
 * itfTimeChartInformationContainer.java: DecisionUnitMasonInspectors - inspectors.mind.pa
 * 
 * @author langr
 * 04.11.2009, 19:09:18
 */
package pa._v19.interfaces;

import java.util.ArrayList;

import pa._v19.tools.clsPair;

/**
 * 
 * @author langr
 * 04.11.2009, 19:09:18
 * 
 */
@Deprecated
public interface itfTimeChartInformationContainer {
	public ArrayList<clsPair<String, Double>> getTimeChartData();
}
