/**
 * itfInterfaceTimeChartHistory.java: DecisionUnits - pa._v30.interfaces
 * 
 * @author deutsch
 * 23.04.2011, 15:35:10
 */
package pa._v30.interfaces;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 23.04.2011, 15:35:10
 * 
 */
public interface itfInterfaceTimeChartHistory {
	public TreeMap<Long, ArrayList<Double>> getTimeChartHistory();
}
