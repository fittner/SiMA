/**
 * itfInterfaceTimeChartHistory.java: DecisionUnits - pa._v38.interfaces
 * 
 * @author deutsch
 * 23.04.2011, 15:35:10
 */
package pa._v38.interfaces;

import java.util.ArrayList;

import pa._v38.tools.clsPair;

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
