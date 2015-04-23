
package inspector.interfaces;

import java.util.ArrayList;

/**
 * If an module implements this interface, a area chart inspector tab is added that displays the provided data.
 * 
 * @author herret
 * Aug 30, 2012, 3:41:59 PM
 * 
 */
public interface itfInspectorAreaChart {
	/**
	 * Returns the title of the area chart
	 *
	 * @since 30.08.2012 10:00
	 *
	 * @return
	 */
	public String getAreaChartTitle();
	
	/**
	 * Returns an array that contains the current set of data. The main Array List has to have the same number of entries as the array returned by getAreaChartAreaCaptions. And the sub Array Lists has to have the same number of entries a the array returned by getAreaChartColumnCaptions
	 *
	 * @since 30.08.2012 10:00
	 *
	 * @return
	 */
	public ArrayList<ArrayList<Double>> getAreaChartData();
	
	/**
	 * Returns an array that contains the labels of all Areas. Has to have the same number of entries as the main Array List return by getAreaChartData.
	 *
	 * @since 30.08.2012 10:00
	 *
	 * @return
	 */
	public ArrayList<String> getAreaChartAreaCaptions();
	
	/**
	 * Returns an array that contains the labels for all Columns. Has to have the same number of entries as the sub ArrayList retruned by getAreaChartData.
	 *
	 * @since 30.08.2012 10:00
	 *
	 * @return
	 */
	public ArrayList<String> getAreaChartColumnCaptions();
}
