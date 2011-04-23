/**
 * itfInterfaceTimeChartHistory.java: DecisionUnits - pa._v30.interfaces
 * 
 * @author deutsch
 * 23.04.2011, 15:35:10
 */
package pa._v30.interfaces;

import java.util.ArrayList;

import pa._v30.tools.clsPair;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 23.04.2011, 15:35:10
 * 
 */
public interface itfInterfaceTimeChartHistory {
	public ArrayList<clsPair <Long, ArrayList<Double>> > getTimeChartHistory();
}
