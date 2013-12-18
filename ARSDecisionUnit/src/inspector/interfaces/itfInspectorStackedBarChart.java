/**
 * CHANGELOG
 *
 * Sep 27, 2012 herret - File created
 *
 */
package inspector.interfaces;

import java.util.ArrayList;

/**
 * Implement this interface to display a stacked bar chart within an inspector tab.
 * 
 * @author herret
 * Sep 27, 2012, 9:47:31 AM
 * 
 */
public interface itfInspectorStackedBarChart {

	/**
	 * Returns the title of the  stacked bar chart
	 *
	 * @since 30.08.2012 10:00
	 *
	 * @return
	 */
	public String getStackedBarChartTitle();
	
	/**
	 * Returns an array that contains the current set of data. The main Array List has to have the same number of entries as the array returned by getStackedBarChartCategoryCaptions. And the sub Array Lists has to have the same number of entries a the array returned by getSteckedBarChartColumnCaptions
	 *
	 * @since 30.08.2012 10:00
	 *
	 * @return
	 */
	public ArrayList<ArrayList<Double>> getStackedBarChartData();
	
	/**
	 * Returns an array that contains the labels of all Categories. Has to have the same number of entries as the main Array List return by getStackedBarChartData.
	 *
	 * @since 30.08.2012 10:00
	 *
	 * @return
	 */
	public ArrayList<String> getStackedBarChartCategoryCaptions();
	
	/**
	 * Returns an array that contains the labels for all Columns. Has to have the same number of entries as the sub ArrayList retruned by getStackedBarChartData.
	 *
	 * @since 30.08.2012 10:00
	 *
	 * @return
	 */
	public ArrayList<String> getStackedBarChartColumnCaptions();
}
