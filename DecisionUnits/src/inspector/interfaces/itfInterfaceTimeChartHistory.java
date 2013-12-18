/**
 * itfInterfaceTimeChartHistory.java: DecisionUnits - pa._v38.interfaces
 * 
 * @author deutsch
 * 23.04.2011, 15:35:10
 */
package inspector.interfaces;

import java.util.ArrayList;

import base.datatypes.helpstructures.clsPair;

/**
 * For the modules that implement this interface, a history of the provided values is stored in the data logger. Special purpose inspector tabs show the stored 
 * values in form of charts and csv files.
 * 
 * @author deutsch
 * 23.04.2011, 15:35:10
 * 
 */
public interface itfInterfaceTimeChartHistory {
	/**
	 * Returns a two-dimensional set of data. The first dimension is the time (Long), the second dimension (ArrayList<Double>) contains the provided values
	 * for the given point in time. 
	 *
	 * @since 12.07.2011 11:56:22
	 *
	 * @return
	 */
	public ArrayList<clsPair <Long, ArrayList<Double>> > getTimeChartHistory();
}
