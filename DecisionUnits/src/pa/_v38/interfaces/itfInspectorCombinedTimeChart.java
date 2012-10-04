/**
 * CHANGELOG
 *
 * Sep 27, 2012 herret - File created
 *
 */
package pa._v38.interfaces;

import java.util.ArrayList;

/**
 * DOCUMENT (herret) - insert description 
 * 
 * @author herret
 * Sep 27, 2012, 11:21:03 AM
 * 
 */
public interface itfInspectorCombinedTimeChart {
	/**
	 * Returns the label for the y-axis.
	 *
	 * @since 12.07.2011 11:39:24
	 *
	 * @return
	 */
	public String getCombinedTimeChartAxis();
	
	
	/**
	 * Returns an array that contains the current set of data. For each sub chart a ArrayList<Double> containing the Values should be contained
	 *
	 * @since 12.07.2011 11:41:25
	 *
	 * @return
	 */
	public ArrayList<ArrayList<Double>> getCombinedTimeChartData();
	
	/**
	 * Returns an array that contains the labels for all sub charts. Has to have the same number of entries as the array returned by getTimeChartData.
	 *
	 * @since 12.07.2011 11:42:16
	 *
	 * @return
	 */
	public ArrayList<String> getChartTitles();
	
	/**
	 * Returns an array that contains the labels of the values of all sub charts
	 *
	 * @since 12.07.2011 11:42:16
	 *
	 * @return
	 */
	public ArrayList<ArrayList<String>> getValueCaptions();
}
