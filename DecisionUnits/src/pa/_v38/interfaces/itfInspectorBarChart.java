/**
 * CHANGELOG
 *
 * Sep 5, 2012 herret - File created
 *
 */
package pa._v38.interfaces;

import java.util.ArrayList;

/**
 * Implement this interface to display a bar chart within an inspector tab.
 * 
 * @author herret
 * Sep 5, 2012, 11:26:06 AM
 * 
 */
public interface itfInspectorBarChart {
	/**
	 * Returns the title of the bar chart
	 *
	 * @since 30.08.2012 10:00
	 *
	 * @return
	 */
	public String getBarChartTitle();
	/**
	 * Returns an array that contains the current set of data. The main Array List has to have the same number of entries as the array returned by getAreaChartAreaCaptions. And the sub Array Lists has to have the same number of entries a the array returned by getAreaChartColumnCaptions
	 *
	 * @since 30.08.2012 10:00
	 *
	 * @return
	 */
	public ArrayList<ArrayList<Double>> getBarChartData();
	
	/**
	 * Returns an array that contains the labels of all Categories. Has to have the same number of entries as the main Array List return by getAreaChartData.
	 *
	 * @since 30.08.2012 10:00
	 *
	 * @return
	 */
	public ArrayList<String> getBarChartCategoryCaptions();
	
	/**
	 * Returns an array that contains the labels for all Columns. Has to have the same number of entries as the sub ArrayList retruned by getAreaChartData.
	 *
	 * @since 30.08.2012 10:00
	 *
	 * @return
	 */
	public ArrayList<String> getBarChartColumnCaptions();
}
